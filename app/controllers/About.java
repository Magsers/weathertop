package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class About extends Controller {
  public static void index() {
    if (Accounts.memberLoggedIn()) {
      Member member = Accounts.getLoggedInMember();
      Logger.info("Logged In Member, Rendering About");
      render("about.html", member);
    } else {
      render("about.html");
    }
  }
}
