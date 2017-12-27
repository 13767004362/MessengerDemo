package com.xingen.messengerdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Messenger messenger;
    private boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startRemoteService();
        findViewById(R.id.main_send_message).setOnClickListener((view)->{
              if (messenger!=null){
                  sendRemoteMessage();
              }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unBindService();
    }
    private Intent createIntent(){
        Intent intent=new Intent("com.xingen.remoteservice.MessengerService");
        intent.setPackage("com.xingen.remoteservice");
        return intent;
    }
    private void startRemoteService(){
        try{
            startService(createIntent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void bindService(){
              bindService(createIntent(),serviceConnection, Context.BIND_AUTO_CREATE);
    }
    private void unBindService(){
           if (isBound){
                 unbindService(serviceConnection);
           }
    }
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger=new Messenger(service);
            isBound=true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
                  messenger=null;
                  isBound=false;
        }
    };
    private void sendRemoteMessage(){
        try{
            Message message=Message.obtain();
            message.what=MessageConstant.what_notify;
            Bundle bundle=new Bundle();
            bundle.putString(MessageConstant.key_name,"我是客户端，我为你打call");
            message.setData(bundle);
            messenger.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
