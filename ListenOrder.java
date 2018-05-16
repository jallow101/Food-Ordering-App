package com.example.reds0n.foodorderclient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase db;
    DatabaseReference requests;


    public ListenOrder() {


    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db= FirebaseDatabase.getInstance();
        requests = db.getReference("orders");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requests.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        // when there's a change

        Order request = dataSnapshot.getValue(Order.class);
        showNotification(dataSnapshot.getKey(),request);
    }

    private void showNotification(String key, Order request) {

        Intent  intent = new Intent(getBaseContext(),OrderStatus.class);
        intent.putExtra("userPhone",request.getPhone());
        PendingIntent content = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Jallow101")
                .setContentInfo("Your Order was Updated!!")
                .setContentText(Common.currentuser.getName() + " Your Order is "+ Common.convertCodeToStatus(request.getStatus()))
                .setContentInfo("Info")
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager NotiManger =  (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotiManger.notify(1,builder.build());


    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
