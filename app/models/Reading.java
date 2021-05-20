package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.util.Date;

@Entity
public class Reading extends Model {

  public int code;
  public double temperature;
  public double windSpeed;
  public double windDirection;
  public int pressure;
  public Date dateTime;

  public Reading(int code, double temperature, double windSpeed, double windDirection, int pressure, Date dateTime) {
    this.code = code;
    this.temperature = temperature;
    this.windSpeed = windSpeed;
    this.windDirection = windDirection;
    this.pressure = pressure;
    this.dateTime = dateTime;
  }

  public double getWindSpeed() {
    return windSpeed;
  }

}