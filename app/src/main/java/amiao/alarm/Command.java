package amiao.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class Command extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        String string = intent.getExtras().getString("extra");


        Intent ringtoneIntent = new Intent(context, Ringtone.class);

        ringtoneIntent.putExtra("extra", string);

        context.startService(ringtoneIntent);



    }
}
