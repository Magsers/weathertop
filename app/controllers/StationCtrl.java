package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;
import utils.Analytics;
import utils.Conversions;

public class StationCtrl extends Controller {

  public static void index(Long id) {
    Station station = Station.findById(id);
    Reading latestReading = null;

    if (station.readings.size() > 0) {
      latestReading = station.readings.get(station.readings.size() - 1);
      station.weathercode = Conversions.codeToText(latestReading.code);
      station.iconClass = Conversions.weatherIcon(latestReading.code);
      station.fahrenheit = Conversions.fahrenheit(latestReading.temperature);
      station.beaufort = Conversions.beaufort(latestReading.getWindSpeed());
      station.compassDirection = Conversions.windCompass(latestReading.windDirection);
      station.windChill = Analytics.windChillCalc(latestReading.temperature, latestReading.windSpeed);
      station.minTemp = Analytics.getMinTemp(station.readings);
      station.maxTemp = Analytics.getMaxTemp(station.readings);
      station.minWind = Analytics.getMinWind(station.readings);
      station.maxWind = Analytics.getMaxWind(station.readings);
      station.minPressure = Analytics.getMinPressure(station.readings);
      station.maxPressure = Analytics.getMaxPressure(station.readings);
      station.tempTrend = Analytics.tempRising(station.readings);
      station.windTrend = Analytics.windRising(station.readings);
      station.pressureTrend = Analytics.pressureRising(station.readings);
      station.arrow = Analytics.trendArrow(station.tempTrend);
      station.windarrow = Analytics.trendArrow(station.windTrend);
      station.pressurearrow = Analytics.trendArrow(station.pressureTrend);
    }
    render("station.html", station, latestReading);
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