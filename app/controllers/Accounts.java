package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller {

  public static void signup() {
    Logger.info("Rendering sign up");
    render("signup.html");
  }

  public static void login() {
    Logger.info("Rendering Login");
    render("login.html");
  }

  public static void profile() {
    Member member = Accounts.getLoggedInMember();
    Logger.info("Rendering profile");
    render("profile.html", member);
  }

  public static void register(String firstname, String lastname, String email, String password) {
    Member m = Member.findByEmail(email);
    if (m == null) {
      Logger.info("Registering new user " + email);
      Member member = new Member(firstname, lastname, email, password);
      member.save();
      session.put("logged_in_Memberid", member.id);
      render("profile.html", member);
    } else {
      Logger.info("Email address already in use");
      String invalidEmail = "Email address already in use. Please try again or Register.";
      render("/signup.html", invalidEmail);
    }
  }

  public static void authenticate(String email, String password) {
    Logger.info("Attempting to authenticate with " + email + ":" + password);
    Member member = Member.findByEmail(email);
    if ((member != null) && (member.checkPassword(password) == true)) {
      Logger.info("Authentication successful");
      session.put("logged_in_Memberid", member.id);
      redirect("/dashboard");
    } else {
      Logger.info("Authentication failed");
      String loginFail = "Authentication failed, please try again or Register.";
      render("/login.html", loginFail);
    }
  }

  public static void logout() {
    session.clear();
    redirect("/");
  }

  public static Member getLoggedInMember() {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
      Logger.info("Member : " + memberId);
    } else {
      member = null;
      Logger.info("Member = null ");
    }
    return member;
  }

  public static void editMember(Long memberId, String firstname, String lastname, String email, String password) {
    Member member = Member.findById(memberId);
    member.setFirstName(firstname);
    member.setLastName(lastname);
    Member m = Member.findByEmail(email);
    if (m == null) {
      Logger.info("Unique email");
      member.setEmail(email);
    } else {
      Logger.info("Email address already in use");
      String invalidEmail = "Email address already in use. Please use a unique email address.";
      render("/profile.html", member, invalidEmail);
    }
    member.setPassword(password);
    member.save();
    Logger.info("Editing Member" + member.firstname + member.lastname);
    redirect("/profile");
  }

  public static boolean memberLoggedIn() {
    boolean loggedIn = false;
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      Logger.info("Member logged in");
      loggedIn = true;
    } else {
      Logger.info("All members logged out");
      loggedIn = false;
    }
    return loggedIn;
  }

}