package com.android.developerchoice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, proximitySensor, lightSensor;
    private TextView accelerometerData, proximityData, lightData;
    private Switch flashlightSwitch;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Set up the "go back" button
        ImageButton goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());


        // Initialize sensors and text views
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightData = findViewById(R.id.lightData);
        proximityData = findViewById(R.id.proximityData);
        accelerometerData = findViewById(R.id.accelerometerData);

        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            // Register sensors
            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                lightData.setText("Light Level Data\nNone");
                Toast.makeText(this, "Light sensor not found!", Toast.LENGTH_SHORT).show();
            }

            if (proximitySensor != null) {
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(this, "Proximity sensor not found!", Toast.LENGTH_SHORT).show();
            }

            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Toast.makeText(this, "Accelerometer sensor not found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }


        // Flashlight setup
        flashlightSwitch = findViewById(R.id.flashlightSwitch);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            // Get the camera ID of the device's back camera
            if (cameraManager != null) {
                cameraId = cameraManager.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Camera access error", Toast.LENGTH_SHORT).show();
        }

        // Set up listener for the flashlight switch
        flashlightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                turnOnFlashlight();
            } else {
                turnOffFlashlight();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightData.setText("Light Level Data\n" + event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityData.setText("Proximity Data\n" + event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerData.setText("Accelerometer Data\n" + event.values[0] + "\n" + event.values[1] + "\n" + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void turnOnFlashlight() {
        try {
            if (cameraManager != null) {
                cameraManager.setTorchMode(cameraId, true);
                isFlashlightOn = true;
                Toast.makeText(this, "Flashlight ON", Toast.LENGTH_SHORT).show();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to turn on flashlight", Toast.LENGTH_SHORT).show();
        }
    }

    private void turnOffFlashlight() {
        try {
            if (cameraManager != null) {
                cameraManager.setTorchMode(cameraId, false);
                isFlashlightOn = false;
                Toast.makeText(this, "Flashlight OFF", Toast.LENGTH_SHORT).show();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to turn off flashlight", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFlashlightOn) {
            turnOffFlashlight();
        }
    }
}
