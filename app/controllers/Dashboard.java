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
import utils.StationAnalytics;

public class Dashboard extends Controller {
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = StationAnalytics.alphaStationList(member.stations);
    Reading latestReading = null;

    for (Station station : stations) {
      if (station.readings.size() > 0) {
        latestReading = station.readings.get(station.readings.size() - 1);
        station.weathercode = StationAnalytics.codeToText(latestReading.code);
        station.iconClass = StationAnalytics.weatherIcon(latestReading.code);
        station.fahrenheit = StationAnalytics.fahrenheit(latestReading.temperature);
        station.beaufort = StationAnalytics.beaufort(latestReading.getWindSpeed());
        station.compassDirection = StationAnalytics.windCompass(latestReading.windDirection);
        station.windChill = StationAnalytics.windChillCalc(latestReading.temperature, latestReading.windSpeed);
        station.minTemp = StationAnalytics.getMinTemp(station.readings);
        station.maxTemp = StationAnalytics.getMaxTemp(station.readings);
        station.minWind = StationAnalytics.getMinWind(station.readings);
        station.maxWind = StationAnalytics.getMaxWind(station.readings);
        station.minPressure = StationAnalytics.getMinPressure(station.readings);
        station.maxPressure = StationAnalytics.getMaxPressure(station.readings);
        station.tempTrend = StationAnalytics.tempRising(station.readings);
        station.windTrend = StationAnalytics.windRising(station.readings);
        station.pressureTrend = StationAnalytics.pressureRising(station.readings);
        station.arrow = StationAnalytics.trendArrow(station.tempTrend);
        station.windarrow = StationAnalytics.trendArrow(station.windTrend);
        station.pressurearrow = StationAnalytics.trendArrow(station.pressureTrend);
      }
    }
    render("dashboard.html", member, stations, latestReading);
  }

  public static void addStation(String name, float lat, float lng) {
    Logger.info("Adding Station " + name);
    Member member = Accounts.getLoggedInMember();
    Station station = new Station(name, lat, lng);
    member.stations.add(station);
    member.save();
    redirect("/dashboard");
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