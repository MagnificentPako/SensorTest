package com.example.myapplication.sensors;

import android.hardware.SensorEventListener;

import java.util.ArrayList;

public interface DataCollector extends SensorEventListener {

    boolean hasCapability();

    ArrayList<float[]> getData();

    String getIdentifier();

    void start();
    void stop();

}
