package my.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TEST+", "receiver");
        this.context = context;

        Intent in = new Intent(context, AlarmService.class);
        context.startService(in);
    }
}
