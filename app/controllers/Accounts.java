package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{
    public static void signup()
    {
        Logger.info("signing up");
        render("signup.html");
    }

    public static void login()
    {
        Logger.info("login login");
        render("login.html");
    }

    public static void profile()
    {
        Member member = Accounts.getLoggedInMember();
        Logger.info("profile");
        render("profile.html", member);
    }

    public static void register(String firstname, String lastname, String email, String password)
    {
        Logger.info("Registering new user " + email);
        Member member = new Member(firstname, lastname, email, password);
        member.save();
        session.put("logged_in_Memberid", member.id);
        render("profile.html", member);
    }

    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else {
            Logger.info("Authentication failed");
            String loginFail="Authentication failed, please try again or Register.";
            render("/login.html", loginFail);
        }
    }

    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
            Logger.info("getLoggedinMember ");
        } else {
            Logger.info("getLoggedinMember login");
            login();
        }
        return member;
    }

    public static void editMember(Long memberId, String firstname, String lastname, String email, String password) {
        Member member = Member.findById(memberId);
        member.setFirstName(firstname);
        member.setLastName(lastname);
        member.setEmail(email);
        member.setPassword(password);
        member.save();
        Logger.info("Editing Member" + member.firstname + member.lastname);
        redirect("/profile");
    }
}