package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;
import utils.StationAnalytics;

import javax.swing.*;

public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Reading latestReading = null;

        if(station.readings.size() > 0) {
            latestReading = station.readings.get(station.readings.size() - 1);
            station.weathercode = StationAnalytics.codeToText(latestReading.code);
            station.iconClass = StationAnalytics.weatherIcon(latestReading.code);
            station.fahrenheit = StationAnalytics.fahrenheit(latestReading.temperature);
            station.beaufort = StationAnalytics.beaufort(latestReading.getWindSpeed());
            station.compassDirection = StationAnalytics.windCompass(latestReading.windDirection);
            station.windChill= StationAnalytics.windChillCalc(latestReading.temperature, latestReading.windSpeed);
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

        render("station.html", station, latestReading);
    }

    public static void deletereading (Long id, Long readingid)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info ("Removing " + reading.id);
        station.readings.remove(reading);
        station.save();
        reading.delete();
        redirect ("/stations/" + id);
    }


    public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, int pressure, Date dateTime)
    {
        Date timeStamp = Calendar.getInstance().getTime();
        Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure, timeStamp);
        Station station = Station.findById(id);
        Logger.info("Date / Time : " + timeStamp);
        station.readings.add(reading);
        station.save();
        redirect ("/stations/" + id);
    }

}