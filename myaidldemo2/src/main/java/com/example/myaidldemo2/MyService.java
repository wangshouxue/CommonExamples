package com.example.myaidldemo2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    IMyAidlInterface.Stub myIBinder=new IMyAidlInterface.Stub(){
        private String txt;

        @Override
        public void sendMsg(String msg) throws RemoteException {
            this.txt=msg;
        }

        @Override
        public String getMsg() throws RemoteException {
            return txt+"：服务端已处理过";
        }
    };

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }
}