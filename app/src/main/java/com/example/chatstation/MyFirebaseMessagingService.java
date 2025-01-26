package com.example.chatstation;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import static com.example.chatstation.App.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.NotificationManager;

import java.util.Objects;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String title = Objects.requireNonNull(message.getNotification()).getTitle();
        String body = message.getNotification().getBody();



       NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
       builder.setContentTitle(title);
       builder.setContentText(body);
       builder.setSmallIcon(R.drawable.logo);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());


    }


}
