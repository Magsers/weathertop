package utils;

import java.util.HashMap;

public class Conversions {

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
    if ((windDirectionDegrees > 348.75 && windDirectionDegrees <= 360)
        || (windDirectionDegrees > 0 && windDirectionDegrees <= 11.25)) {
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
    } else {
      windDirectionText = "Error";
    }
    return windDirectionText;
  }

}
