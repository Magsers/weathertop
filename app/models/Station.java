package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model {
  public String name;
  public float lat;
  public float lng;
  @OneToMany(cascade = CascadeType.ALL)
  public List<Reading> readings = new ArrayList<Reading>();
  public String weathercode;
  public double fahrenheit;
  public int beaufort;
  public String compassDirection;
  public double windChill;
  public String iconClass;
  public double minTemp;
  public double maxTemp;
  public double minWind;
  public double maxWind;
  public double minPressure;
  public double maxPressure;
  public int tempTrend;
  public int windTrend;
  public int pressureTrend;
  public String arrow;
  public String windarrow;
  public String pressurearrow;

  public Station(String name, float lat, float lng) {
    this.name = name;
    this.lat = lat;
    this.lng = lng;
  }

  public String getName() {
    return name;
  }

}