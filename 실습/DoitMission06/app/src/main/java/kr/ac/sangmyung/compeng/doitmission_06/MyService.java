package kr.ac.sangmyung.compeng.doitmission_06;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public static final String KEY_SIMPLE_DATA = "data";

    public MyService() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return Service.START_STICKY;
        } else {

             Bundle bundle = intent.getExtras();
             SimpleData data = (SimpleData) bundle.getParcelable(KEY_SIMPLE_DATA);

             String i = data.getFrom();

             if (data.getMessage().equals("Customer.class")){
                 Intent showIntent = new  Intent(getApplicationContext(), Customer.class);
                 showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 showIntent.putExtra("form",i);
                 startActivity(showIntent);
             }

            if (data.getMessage().equals("Sales.class")){
                Intent showIntent = new  Intent(getApplicationContext(), Sales.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showIntent.putExtra("form",i);
                startActivity(showIntent);
            }

            if (data.getMessage().equals("Product.class")){
                Intent showIntent = new  Intent(getApplicationContext(), Product.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showIntent.putExtra("form",i);
                startActivity(showIntent);
            }

            if (data.getMessage().equals("Login.class")){
                Intent showIntent = new  Intent(getApplicationContext(), Login.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showIntent.putExtra("form",i);
                startActivity(showIntent);
            }

            if (data.getMessage().equals("Menu.class")){
                Intent showIntent = new  Intent(getApplicationContext(), Menu.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showIntent.putExtra("form",i);
                startActivity(showIntent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
