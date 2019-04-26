package com.artirex.sutakip.BroadcastReceivers;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.design.widget.Snackbar;

import com.artirex.sutakip.R;

public class BataryaBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    int bataryaSarjbilgi = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);

    boolean  isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                              status == BatteryManager.BATTERY_STATUS_FULL;

    //ŞARJ DA DEGİL VE 20 DEN KUCUK ISE ŞARJI "BATARYA DÜŞÜK" HATASI VERSIN.
        if(!isCharging && bataryaSarjbilgi < 20)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_battery);
            builder.setTitle("Batarya Durumunuz");
            builder.setMessage("Bataryanız Düşüyor Dikkat ediniz."+isCharging);

            builder.show();
        }
    }
}
