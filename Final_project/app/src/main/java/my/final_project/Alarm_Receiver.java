package my.final_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;
    private static PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TEST+", "receiver");
        this.context = context;
        PushWakeLock.acquireCpuWakeLock(context);

        Intent service_intent = new Intent(context,AlarmService.class);

        this.context.startService(service_intent);
    }
}
