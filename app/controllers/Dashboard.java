package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Member;
import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import utils.Analytics;
import utils.Conversions;

public class Dashboard extends Controller {

  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = Analytics.alphaStationList(member.stations);
    Reading latestReading = null;

    for (Station station : stations) {
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
    }
    render("dashboard.html", member, stations, latestReading);
  }

  public static void addStation(String name, float lat, float lng) {
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = Analytics.alphaStationList(member.stations);
    if (Analytics.uniqueStation(member.stations, name)) {
      Logger.info("Adding Station " + name);
      Station station = new Station(name, lat, lng);
      member.stations.add(station);
      member.save();
      redirect("/dashboard");
    } else {
      Logger.info("Duplicate Station " + name);
      String duplicateStation = "Station already exists! ";
      render("/dashboard.html", stations, member, duplicateStation);
    }
  }

  public static void deleteStation(Long id, Long stationid) {
    Member member = Member.findById(id);
    Station station = Station.findById(stationid);
    member.stations.remove(station);
    member.save();
    station.delete();
    Logger.info("Deleting " + station.name);
    redirect("/dashboard");
  }
}