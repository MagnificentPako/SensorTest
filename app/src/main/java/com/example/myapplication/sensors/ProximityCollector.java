package com.example.myapplication.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import java.util.ArrayList;

public class ProximityCollector implements DataCollector {

    private SensorManager sensorManager;
    private ArrayList<float[]> data;

    public ProximityCollector(SensorManager manager) {
        this.sensorManager = manager;
        data = new ArrayList();
    }

    @Override
    public boolean hasCapability() {
        return this.sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null;
    }

    @Override
    public ArrayList<float[]> getData() {
        return data;
    }

    @Override
    public String getIdentifier() {
        return "PROX";
    }

    @Override
    public void start() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.data.add(sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
