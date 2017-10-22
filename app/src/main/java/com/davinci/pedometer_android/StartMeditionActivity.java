package com.davinci.pedometer_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartMeditionActivity extends AppCompatActivity {

    private Button BtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_medition);

        BtnStart = (Button) findViewById(R.id.btn_start);

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(StartMeditionActivity.this, BluetoothSendActivity.class);
                i.putExtra("device_address", getIntent().getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS));
                startActivity(i);
            }
        });

    }

}