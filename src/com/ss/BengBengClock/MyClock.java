package com.ss.BengBengClock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClock extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView time = (TextView) findViewById(R.id.textView);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date now = new Date(System.currentTimeMillis());
        time.setText(formatter.format(now));
        final Handler myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.i("message:", "received");
                switch (msg.what){
                    case 0x1234:
                        Date now = new Date(System.currentTimeMillis());
                        time.setText(formatter.format(now));
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
        final Thread myThread = new Thread() {
            @Override
            public void run() {
                do{
                    Message msg = new Message();
                    msg.what = 0x1234;
                    myHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while(Thread.interrupted() == false);
            }
        };
        myThread.start();
    }

}
