package utils;

import controllers.StationCtrl;
import models.Reading;
import models.Station;
import play.Logger;

import java.util.*;

import java.util.stream.Stream;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class StationAnalytics {

    public static String codeToText(int code) {
       switch (code) {
           case 100:
               return "Clear";
           case 200:
               return "Partial Clouds";
           case 300:
               return "Cloudy";
           case 400:
               return "Light Showers";
           case 500:
               return "Heavy Showers";
           case 600:
               return "Rain";
           case 700:
               return "Snow";
           case 800:
               return "Thunder";
           default:
               return "error";
       }
     }

    public static String weatherIcon(int code) {
        HashMap<Integer, String> weatherIcons;
        weatherIcons = new HashMap<Integer, String>();
        weatherIcons.put(100, "sun icon");
        weatherIcons.put(200, "cloud sun icon");
        weatherIcons.put(300, "cloud icon");
        weatherIcons.put(400, "cloud sun rain icon");
        weatherIcons.put(500, "cloud showers heavy icon");
        weatherIcons.put(600, "cloud rain icon");
        weatherIcons.put(700, "snowflake icon");
        weatherIcons.put(800, "poo storm icon");
        return weatherIcons.get(code);
    }

    public static double fahrenheit(double centigrade) {
        double f;
        f = (centigrade * 9 / 5) + 32;
        return f;
    }

    public static int beaufort(double windSpeed) {
        int bf = 0;

        if (windSpeed <= 1) {
            bf = 0;
        } else if (windSpeed > 1 && windSpeed <= 5) {
            bf = 1;
        } else if (windSpeed >= 6 && windSpeed <= 11) {
            bf = 2;
        } else if (windSpeed >= 12 && windSpeed <= 19) {
            bf = 3;
        } else if (windSpeed >= 20 && windSpeed <= 28) {
            bf = 4;
        } else if (windSpeed >= 29 && windSpeed <= 38) {
            bf = 5;
        } else if (windSpeed >= 39 && windSpeed <= 49) {
            bf = 6;
        } else if (windSpeed >= 50 && windSpeed <= 61) {
            bf = 7;
        } else if (windSpeed >= 62 && windSpeed <= 74) {
            bf = 8;
        } else if (windSpeed >= 75 && windSpeed <= 88) {
            bf = 9;
        } else if (windSpeed >= 89 && windSpeed <= 102) {
            bf = 10;
        } else if (windSpeed >= 103 && windSpeed <= 117) {
            bf = 11;
        }
        return bf;
    }

    public static String windCompass(double windDirectionDegrees) {
        String windDirectionText = "";

        if (windDirectionDegrees > 348.75 || windDirectionDegrees <= 11.25) {
            windDirectionText = "North";
        } else if (windDirectionDegrees > 11.25 && windDirectionDegrees <= 33.75) {
            windDirectionText = "North North East";
        } else if (windDirectionDegrees > 33.75 && windDirectionDegrees <= 56.25) {
            windDirectionText = "North East";
        } else if (windDirectionDegrees > 56.25 && windDirectionDegrees <= 78.75) {
            windDirectionText = "East North East";
        } else if (windDirectionDegrees > 78.75 && windDirectionDegrees <= 101.25) {
            windDirectionText = "East";
        } else if (windDirectionDegrees > 101.25 && windDirectionDegrees <= 123.75) {
            windDirectionText = "East South East";
        } else if (windDirectionDegrees > 123.75 && windDirectionDegrees <= 146.25) {
            windDirectionText = "South East";
        } else if (windDirectionDegrees > 146.25 && windDirectionDegrees <= 168.75) {
            windDirectionText = "South South East";
        } else if (windDirectionDegrees > 168.75 && windDirectionDegrees <= 191.25) {
            windDirectionText = "South";
        } else if (windDirectionDegrees > 191.25 && windDirectionDegrees <= 213.75) {
            windDirectionText = "South South West";
        } else if (windDirectionDegrees > 213.75 && windDirectionDegrees <= 236.25) {
            windDirectionText = "South West";
        } else if (windDirectionDegrees > 236.25 && windDirectionDegrees <= 258.75) {
            windDirectionText = "West South West";
        } else if (windDirectionDegrees > 258.75 && windDirectionDegrees <= 281.25) {
            windDirectionText = "West";
        } else if (windDirectionDegrees > 281.25 && windDirectionDegrees <= 303.75) {
            windDirectionText = "West North West";
        } else if (windDirectionDegrees > 303.75 && windDirectionDegrees <= 326.25) {
            windDirectionText = "North West";
        } else if (windDirectionDegrees > 326.25 && windDirectionDegrees <= 348.75) {
            windDirectionText = "Norht North West";
        }
        return windDirectionText;
    }

    public static double windChillCalc(double tempCelcius, double windSpeed) {

        double windChill = 0.0;

        windChill = (13.12 + (0.6215 * tempCelcius) - 11.37 * (Math.pow(windSpeed, 0.16)) + (0.3965 * tempCelcius) * (Math.pow(windSpeed, 0.16)));

        return Math.round(windChill * 100.0) / 100.0;
    }

    public static double getMinTemp(List<Reading> readings) {
        Reading reading = null;
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
        Reading reading = null;
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
        Reading reading = null;
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
        Reading reading = null;
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
        Reading reading = null;
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
        Reading readingOne, readingTwo, readingThree = null;
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
        Reading readingOne, readingTwo, readingThree = null;
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
        Reading readingOne, readingTwo, readingThree = null;
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
                return "error";
        }
    }

    public static List<Station> alphaStationList(List<Station> stations) {
        stations.sort(Comparator.comparing(Station::getName));
        Logger.info("Sorting stations");
        return stations;
    }
}
