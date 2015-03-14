package com.example.mustafatopcu.servisogreniyorum;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ZamanServisi extends Service {

    /*
    Ilk once duzenli araliklarla is yapmak icin bir Timer nesnesi ve
    servis icinden ekrana Toast ile yazi yazabilmek icin bir Handler nesnesi tanimlayalim.
    */
    Timer zamanlayici;
    Handler yardimci;

    /*
    Buraya milisaniye cinsinden bir zaman belirtmemiz gerekiyor.
    1 sn = 1000 ms Ornegin ben 10 saniyede bir calismasini istedim.
    */
    final static long ZAMAN = 10000;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();

        zamanlayici= new Timer();

        /*
        yardimci nesnesine uygulamanin ana Looper nesnesini atiyoruz. Boylece bu Handler nesnesine
        gonderecegimiz bilgiler, icinde bulundugumuz sinif yerine (bu durumda bizim servisimiz)
        uygulamanin kendisi üzerinde isletilecek. Eger bunu yapmazsak servis sinifi icinden Toast
         nesnesi ile bilgi vermemiz mumkun olmayacak.
         */
        yardimci=new Handler(Looper.getMainLooper());

        zamanlayici.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bilgiVer();
            }
        },0,ZAMAN);  //yazilan ikinci parametre, ne kadar gecikme ile baslayacagi.
    }

    public void bilgiVer(){
        long zaman=System.currentTimeMillis();
        SimpleDateFormat bilgi= new SimpleDateFormat("dd MMMM yyyy, EEEE / HH:mm");
        final String sonuc = bilgi.format(new Date(zaman));

        yardimci.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZamanServisi.this, sonuc, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        zamanlayici.cancel();
        super.onDestroy();
    }
}
