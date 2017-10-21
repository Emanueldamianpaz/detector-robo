package com.davinci.pedometer_android.pedometer;

import android.util.Log;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StepDetector {

    private static final int ACCEL_RING_SIZE = 50;
    private static final int VEL_RING_SIZE = 10;


    private int accelRingCounter = 0;
    private float[] accelRingX = new float[ACCEL_RING_SIZE];
    private float[] accelRingY = new float[ACCEL_RING_SIZE];
    private float[] accelRingZ = new float[ACCEL_RING_SIZE];
    private double[] velocityFinal = new double[3];
    private double[] distanceFinal = new double[3];


    public void updateAccel(long timeNs, float x, float y, float z) {

        long timeInMillis = (new Date()).getTime() + (timeNs - System.nanoTime()) / 1000000L;
        Timestamp stamp = new Timestamp(timeInMillis);
        String dateFormat = new SimpleDateFormat("hh:mm:ss").format(new Date(stamp.getTime()));

        int seconds = Integer.parseInt(new SimpleDateFormat("ss").format(new Date(stamp.getTime())));

        float[] currentAccel = new float[3];
        currentAccel[0] = x;
        currentAccel[1] = y;
        currentAccel[2] = z;

        accelRingCounter++;
        accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0];
        accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1];
        accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2];

        float[] worldZ = new float[3];
        worldZ[0] = SensorFilter.sum(accelRingX) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[1] = SensorFilter.sum(accelRingY) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[2] = SensorFilter.sum(accelRingZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE);

        float normalization_factor = SensorFilter.norm(worldZ);

        worldZ[0] = worldZ[0] / normalization_factor;
        worldZ[1] = worldZ[1] / normalization_factor;
        worldZ[2] = worldZ[2] / normalization_factor;

        // Acumulando muestras
        for (int i = 0; i < worldZ.length; i++) {
            worldZ[i] += worldZ[i];
        }

        if (seconds % 2 == 0) { // Cada 2 segundos

            Log.i("Tiempo", dateFormat);

            for (int i = 0; i < velocityFinal.length; i++) {

                velocityFinal[i] = 0.5 * (worldZ[i] / accelRingCounter);
                Log.i("VectorVelocidad", velocityFinal[i] + "");


                //Calcular la distancia
                distanceFinal[i] = velocityFinal[i] * 2; // Velocidad * 2 segundos

                velocityFinal[i] = 0;
                worldZ[i] = 0;

            }

            // Enviar datos

        }
    }
}

