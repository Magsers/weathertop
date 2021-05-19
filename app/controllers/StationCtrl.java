package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller {

  public static void index(Long id) {
    Station station = Station.findById(id);
    Reading latestConditions = null;
    Dashboard.calculations(station);
    render("station.html", station, latestConditions);
  }

  public static void deletereading(Long id, Long readingid) {
    Station station = Station.findById(id);
    Reading reading = Reading.findById(readingid);
    Logger.info("Removing " + reading.id);
    station.readings.remove(reading);
    station.save();
    reading.delete();
    redirect("/stations/" + id);
  }

  public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, int pressure, Date dateTime) {
    Date timeStamp = Calendar.getInstance().getTime();
    Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure, timeStamp);
    Station station = Station.findById(id);
    Logger.info("Date / Time : " + timeStamp);
    station.readings.add(reading);
    station.save();
    redirect("/stations/" + id);
  }


}