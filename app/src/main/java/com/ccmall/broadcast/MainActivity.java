package com.ccmall.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    EditText edtbattery;
    ImageView ivbattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtbattery = findViewById(R.id.edtbattery);
        ivbattery = findViewById(R.id.ivbattery);
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intent.ACTION_BATTERY_CHANGED)) {
                int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                edtbattery.setText("charged :" + remain + "%\n");
                if (remain >= 90) {
                    ivbattery.setImageResource(R.drawable.battery_100);
                } else if (remain >= 70) {
                    ivbattery.setImageResource(R.drawable.battery_80);
                } else if (remain >= 50) {
                    ivbattery.setImageResource(R.drawable.battery_60);
                } else if (remain >= 20) {
                    ivbattery.setImageResource(R.drawable.battery_20);
                } else {
                    ivbattery.setImageResource(R.drawable.battery_0);
                }
                int plug = getIntent().getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                switch (plug) {
                    case 0:
                        edtbattery.append("plug out");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        edtbattery.append("plug on");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        edtbattery.append("usb connection");
                }
            }
        }
    };

    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br, intentFilter);
    }

}
