package com.example.mpti_app.model;

import android.app.Notification;

import com.google.firebase.messaging.RemoteMessage;

public class NotificationModel {
    public String to;
    public Notification notification= new Notification();

    public static class Notification{
        public String title;
        public String text;
    }

}
