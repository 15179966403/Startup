package com.hrc.administrator.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/26.
 */

public class StartupReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //判断接受到的广播是否为收到短信的Broadcast Action
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())){
            Log.d("onReceive","进入if语句");
            StringBuilder sb=new StringBuilder();
            //接受由SmS传过来的数据
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                Log.d("onReceive","bundle数据不为空");
                //通过pdus可以获得接受到的所有短信息
                Object[] objArray=(Object[])bundle.get("pdus");
                //构建短信对象array，并依据收到的对象长度来创建array的大小
                SmsMessage[] messages=new SmsMessage[objArray.length];
                for(int i=0;i<objArray.length;i++){
                    messages[i]=SmsMessage.createFromPdu((byte[])objArray[i]);
                }
                Log.d("onReceive","第一个for循环执行完毕");
                //将送来的短信合并自定信息于StringBuilder中
                for (SmsMessage current:messages){
                    sb.append("短信来源：");
                    //获得接受短信的电话号码
                    sb.append(current.getDisplayOriginatingAddress());
                    sb.append("/n------短信内容-------/n");
                    //获得短信的内容
                    Intent mainIntent=new Intent(context,MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mainIntent);
                    Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
