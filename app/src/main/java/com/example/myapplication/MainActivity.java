package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.sensors.AccelerometerCollector;
import com.example.myapplication.sensors.DataCollector;
import com.example.myapplication.sensors.GyroscopeCollector;
import com.example.myapplication.sensors.LightCollector;
import com.example.myapplication.sensors.ProximityCollector;
import com.example.myapplication.sensors.RotationCollector;

import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        LinearLayout layout = findViewById(R.id.dataLayout);
        HashMap<DataCollector, TextView> collectorTextViews = new HashMap<>();

        DataCollector[] collectors = {
                new GyroscopeCollector(sensorManager),
                new AccelerometerCollector(sensorManager),
                new ProximityCollector(sensorManager),
                new LightCollector(sensorManager),
                new RotationCollector(sensorManager)
        };

        for(DataCollector c : collectors) {
            if(c.hasCapability()) {
                c.start();
                TextView tv = new TextView(this);
                tv.setText(c.getIdentifier() + ": NaN");
                collectorTextViews.put(c, tv);
                layout.addView(tv);
            }
        }

        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                for(DataCollector c : collectorTextViews.keySet()) {
                    TextView tv = collectorTextViews.get(c);
                    if(c.getData().size() > 0)
                        tv.setText(c.getIdentifier() + ": " + Arrays.toString(c.getData().get(c.getData().size()-1)));
                }
                timerHandler.postDelayed(this, 500);
            }
        };
        timerHandler.postDelayed(timerRunnable, 0);
    }
}