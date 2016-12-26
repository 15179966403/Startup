package com.hrc.administrator.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvBatteryChanged;
    private BroadcastReceiver batteryChangedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //判断接收到的是否为电量变化的Broadcast Action
            if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //level表示当前电量的值
                int level=intent.getIntExtra("level",0);
                int scale=intent.getIntExtra("scale",100);
                Log.d("onReceive","进入BroadcastReceive广播中");
                tvBatteryChanged.setText("电池电量："+(level*100/scale)+"%");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBatteryChanged=(TextView)findViewById(R.id.tvBatteryChanged);
        registerReceiver(batteryChangedReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy","将注册的广播注销");
        unregisterReceiver(batteryChangedReceiver);
    }
}
