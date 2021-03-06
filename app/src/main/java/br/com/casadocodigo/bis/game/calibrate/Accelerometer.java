package br.com.casadocodigo.bis.game.calibrate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import br.com.casadocodigo.bis.config.DeviceSettings;

/**
 * Created by antonio on 10/30/17.
 */

public class Accelerometer implements SensorEventListener {

    private float currentAccelerationX;
    private float currentAccelerationY;
    static Accelerometer sharedAccelerometer = null;
    private AccelerometerDelegate delegate;
    private SensorManager sensorManager;
    private float calibratedAccelerationX;
    private float calibratedAccelerationY;
    private int calibrated;

    @Override
    public void onSensorChanged(SensorEvent acceleration) {

        if(calibrated < 100){
            this.calibratedAccelerationX += acceleration.values[0];
            this.calibratedAccelerationY += acceleration.values[1];

            calibrated++;

            if(calibrated == 100){
                this.calibratedAccelerationX /= 100;
                this.calibratedAccelerationY /= 100;
            }

            return;
        }

        // Leitura da aceleracao
        this.currentAccelerationX = acceleration.values[0] - this.calibratedAccelerationX;
        this.currentAccelerationY = acceleration.values[1] - this.calibratedAccelerationY;

        // Envia leitura do acelerometro
        if (this.delegate != null) {
            this.delegate.accelerometerDidAccelerate(
                    currentAccelerationX, currentAccelerationY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static Accelerometer sharedAccelerometer() {
        if (sharedAccelerometer == null) {
            sharedAccelerometer = new Accelerometer();
        }
        return sharedAccelerometer;
    }

    public AccelerometerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(AccelerometerDelegate delegate) {
        this.delegate = delegate;
    }

    public Accelerometer() {
        this.catchAccelerometer();
    }

    public void catchAccelerometer() {
        sensorManager = DeviceSettings.getSensormanager();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }
}
