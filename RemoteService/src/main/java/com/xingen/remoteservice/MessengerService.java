package com.xingen.remoteservice;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ${xingen} on 2017/12/27.
 */

public class MessengerService extends Service {
    private final String TAG = MessengerService.class.getSimpleName();
    private Messenger messenger;
    private MessengerHandler messengerHandler;
    private NotificationManager notificationManager;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, TAG + " onCreate ");
        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        this.messengerHandler = new MessengerHandler();
        this.messenger = new Messenger(this.messengerHandler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + " onDestroy ");
        this.notificationManager.cancelAll();
        this.messengerHandler.getLooper().quit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, TAG + " onStartCommand " + startId);
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, TAG + " onUnbind ");
        return super.onUnbind(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, TAG + " onBind ");
        return messenger.getBinder();
    }
    private class MessengerHandler extends Handler {
        public MessengerHandler() {
            super(Looper.getMainLooper());
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageConstant.what_notify:
                    if (msg.getData() != null) {
                        String content = msg.getData().getString(MessageConstant.key_name);
                        notifyMessage(content);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private void notifyMessage(String content) {
        vibrator.vibrate(1000);
        notificationManager.notify(110, NotificationBuidler.createMessageNotifaction(this, "服务端接受到客户端的来信", content));
    }
}
