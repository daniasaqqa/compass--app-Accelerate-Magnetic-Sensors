package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorAcc;
    private Sensor sensorMF;

    private float[] rotationMatrix=new float[9];
    private float[] arrayAcc= new float[3];
    private float[] arrayMF= new float[3];
    private float[] values = new float[3];

    TextView textView;
    ImageView imageView;
    ImageView arraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.infoValues);
        imageView=findViewById(R.id.imgCompass);
        arraw=findViewById(R.id.arrowN);

        sensorManager=(SensorManager) getSystemService(this.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!= null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            sensorAcc=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorMF=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        }else {
            Toast.makeText(this,"this Sensor is not Found...",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
//            arrayAcc[0]=sensorEvent.values[0];
//            arrayAcc[1]=sensorEvent.values[1];
//            arrayAcc[2]=sensorEvent.values[2];
            arrayAcc=sensorEvent.values;
        }

        if (sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            arrayMF=sensorEvent.values;

        }
        SensorManager.getRotationMatrix(rotationMatrix,null,arrayAcc,arrayMF);
        SensorManager.getOrientation(rotationMatrix,values);

        //1.....3
       int finalVal=(int) Math.toDegrees(values[0]);
       //0 ....360
         if (finalVal<0){

             // ************* Method in the down ***************
           oriantationTow(finalVal+360);
       }else{
             // ************* Method in the down ***************
            oriantationTow(finalVal);
       }
        //1...360

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorAcc,sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorMF,sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void oriantationTow(int finalVal){


        if ( finalVal <=25){
            textView.setText("الشمال");
        }else if( finalVal <= 77){
            textView.setText("الشمال الشرقي");
        }else if(finalVal  <= 112){
            textView.setText("الشرق");
        }
        else if( finalVal <= 158){
            textView.setText("الجنوب الشرقي");
        }else if ( finalVal  <= 202){
            textView.setText("الجنوب ");
        }else if(finalVal <= 247) {
            textView.setText("الجنوب- الغربي " );

        }else if(finalVal<= 292){
            textView.setText("الغرب");
        }else if(finalVal<= 350){
            textView.setText("الشمال الغربي");
        }else {
            textView.setText("الشمال");
        }
        imageView.setRotation(finalVal);
        arraw.setRotation(finalVal*-1);

    }
}