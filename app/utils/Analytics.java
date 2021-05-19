package utils;

import controllers.StationCtrl;
import models.Member;
import models.Reading;
import models.Station;
import play.Logger;

import java.util.*;

public class Analytics {

  public static double windChillCalc(double tempCelcius, double windSpeed) {
    double windChill;
    windChill = (13.12 + (0.6215 * tempCelcius) - 11.37 * (Math.pow(windSpeed, 0.16))
        + (0.3965 * tempCelcius) * (Math.pow(windSpeed, 0.16)));
    return Math.round(windChill * 100.0) / 100.0;
  }

  public static double getMinTemp(List<Reading> readings) {
    Reading reading;
    double minTemp = 0.0;

    if (readings.size() > 0) {
      reading = readings.get(0);
      minTemp = reading.temperature;
      for (Reading currentReading : readings) {
        if (currentReading.temperature < reading.temperature) {
          minTemp = currentReading.temperature;
        }
      }
    }
    return minTemp;
  }

  public static double getMaxTemp(List<Reading> readings) {
    double maxTemp = 0.0;

    if (readings.size() > 0) {
      Reading reading = readings.get(0);
      maxTemp = reading.temperature;
      for (Reading currentReading : readings) {
        if (currentReading.temperature > reading.temperature) {
          maxTemp = currentReading.temperature;
        }
      }
    }
    return maxTemp;
  }

  public static double getMinWind(List<Reading> readings) {
    Reading reading;
    double minWind = 0.0;

    if (readings.size() > 0) {
      reading = readings.get(0);
      minWind = reading.windSpeed;
      for (Reading currentReading : readings) {
        if (currentReading.windSpeed < reading.windSpeed) {
          minWind = currentReading.windSpeed;
        }
      }
    }
    return minWind;
  }

  public static double getMaxWind(List<Reading> readings) {
    Reading reading;
    double maxWind = 0.0;

    if (readings.size() > 0) {
      reading = readings.get(0);
      maxWind = reading.windSpeed;
      for (Reading currentReading : readings) {
        if (currentReading.windSpeed > reading.windSpeed) {
          maxWind = currentReading.windSpeed;
        }
      }
    }
    return maxWind;
  }

  public static double getMinPressure(List<Reading> readings) {
    Reading reading;
    double minPressure = 0.0;

    if (readings.size() > 0) {
      reading = readings.get(0);
      minPressure = reading.pressure;
      for (Reading currentReading : readings) {
        if (currentReading.pressure < reading.pressure) {
          minPressure = currentReading.pressure;
        }
      }
    }
    return minPressure;
  }

  public static double getMaxPressure(List<Reading> readings) {
    Reading reading;
    double maxPressure = 0.0;

    if (readings.size() > 0) {
      reading = readings.get(0);
      maxPressure = reading.pressure;
      for (Reading currentReading : readings) {
        if (currentReading.pressure > reading.pressure) {
          maxPressure = currentReading.pressure;
        }
      }
    }
    return maxPressure;
  }

  public static int tempRising(List<Reading> readings) {
    Reading readingOne, readingTwo, readingThree;
    int rising = 0;

    if (readings.size() >= 3) {
      readingOne = readings.get(readings.size() - 1);
      readingTwo = readings.get(readings.size() - 2);
      readingThree = readings.get(readings.size() - 3);
      if ((readingThree.temperature < readingTwo.temperature) && (readingTwo.temperature < readingOne.temperature)) {
        rising = 1;

      } else if ((readingThree.temperature > readingTwo.temperature) && (readingTwo.temperature > readingOne.temperature)) {
        rising = 2;
      }
    } else {
      rising = 0;
    }
    return rising;
  }

  public static int windRising(List<Reading> readings) {
    Reading readingOne, readingTwo, readingThree;
    int rising = 0;

    if (readings.size() >= 3) {
      readingOne = readings.get(readings.size() - 1);
      readingTwo = readings.get(readings.size() - 2);
      readingThree = readings.get(readings.size() - 3);
      if ((readingThree.windSpeed < readingTwo.windSpeed) && (readingTwo.windSpeed < readingOne.windSpeed)) {
        rising = 1;

      } else if ((readingThree.windSpeed > readingTwo.windSpeed) && (readingTwo.windSpeed > readingOne.windSpeed)) {
        rising = 2;
      }
    } else {
      rising = 0;
    }
    return rising;
  }

  public static int pressureRising(List<Reading> readings) {
    Reading readingOne, readingTwo, readingThree;
    int rising = 0;

    if (readings.size() >= 3) {
      readingOne = readings.get(readings.size() - 1);
      readingTwo = readings.get(readings.size() - 2);
      readingThree = readings.get(readings.size() - 3);
      if ((readingThree.pressure < readingTwo.pressure) && (readingTwo.pressure < readingOne.pressure)) {
        rising = 1;

      } else if ((readingThree.pressure > readingTwo.pressure) && (readingTwo.pressure > readingOne.pressure)) {
        rising = 2;
      }
    } else {
      rising = 0;
    }
    return rising;
  }

  public static String trendArrow(int rising) {
    switch (rising) {
      case 1:
        return "arrow up icon";
      case 2:
        return "arrow down icon";
      case 0:
        return "";
      default:
        return "Error";
    }
  }

  public static List<Station> alphaStationList(List<Station> stations) {
    stations.sort(Comparator.comparing(Station::getName));
    Logger.info("Sorting stations");
    return stations;
  }

  public static boolean uniqueStation(List<Station> stations, String name) {
    HashSet<String> stationHashSet = new HashSet<String>();
    boolean uniStation = false;
    for (Station station : stations) {
      stationHashSet.add(station.getName());
    }
    if (stationHashSet.contains(name))
      uniStation = false;
    else
      uniStation = true;
    return uniStation;
  }

}
