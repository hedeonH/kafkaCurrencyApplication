package org.kpi.model;

public class Sensor {

    private String name;

    private float temperature;

    private int humidity;

    public Sensor(String name, float temperature, int humidity) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Sensor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
