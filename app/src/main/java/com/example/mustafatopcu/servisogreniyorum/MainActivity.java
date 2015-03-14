package com.example.mustafatopcu.servisogreniyorum;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dugme= (Button) findViewById(R.id.dugmeServis);

        //servis calismiyorken butonda baslat yazacak, calisirken durdur yazacak.
        if(servisCalisiyorMu())
        {
            dugme.setText(getString(R.string.durdur));
        }
        else
        {
            dugme.setText(getString(R.string.baslat));
        }
    }

    private boolean servisCalisiyorMu()
    {
        ActivityManager servisYoneticisi = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo servis : servisYoneticisi.getRunningServices(Integer.MAX_VALUE))
        {
            if(getApplication().getPackageName().equals(servis.service.getPackageName()))
            {
                return true;
            }
        }
        return false;
    }

    public void dugmeServisTikla(View v)
    {
        Button dugme = (Button) v;

        //burda ise servis calisiyorsa , once durdur ve sonra butona baslat yazisini set et.
        if(servisCalisiyorMu())
        {
            //durdur
            stopService(new Intent(this, ZamanServisi.class));
            dugme.setText(getString(R.string.baslat));
        }
        //servis calismiyor ise baslat ve butona durdur textini set et.
        else
        {
            //calistir
            startService(new Intent(this, ZamanServisi.class));
            dugme.setText(getString(R.string.durdur));
        }

    }

}
