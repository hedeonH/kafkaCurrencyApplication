package org.kpi.model;

import javax.persistence.*;

@Table(name = "metrics")
@Entity
public class SensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private double temperature;

    private int humidity;

    public SensorEntity(int id, String name, double temperature, int humidity) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public SensorEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
