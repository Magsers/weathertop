package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class Start extends Controller {
  public static void index() {
    if (Accounts.memberLoggedIn()) {
      Member member = Accounts.getLoggedInMember();
      Logger.info("Logged In Member, Rendering Start");
      render("start.html", member);
    } else {
      render("start.html");
    }
  }
}
