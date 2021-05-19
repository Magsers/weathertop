package controllers;

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
    Reading latestConditions = null;

    for (Station station : stations) {
      calculations(station);
    }
    render("dashboard.html", member, stations, latestConditions);
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

  public static Reading calculations(Station station) {
    Reading latestConditions = null;

    if (station.readings.size() > 0) {
      latestConditions = station.readings.get(station.readings.size() - 1);
      station.weathercode = Conversions.codeToText(latestConditions.code);
      station.iconClass = Conversions.weatherIcon(latestConditions.code);
      station.fahrenheit = Conversions.fahrenheit(latestConditions.temperature);
      station.beaufort = Conversions.beaufort(latestConditions.getWindSpeed());
      station.beaufortLabel = Conversions.beaufortLabel(station.beaufort);
      station.compassDirection = Conversions.windCompass(latestConditions.windDirection);
      station.windChill = Analytics.windChillCalc(latestConditions.temperature, latestConditions.windSpeed);
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
    return latestConditions;
  }
}