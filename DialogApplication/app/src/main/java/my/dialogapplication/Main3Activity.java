package my.dialogapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {
    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button noti = findViewById(R.id.noti);

        noti.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                showNotify();
            }
        });
    }
    public void showNotify(){
        NotificationCompat.Builder nbuilder =
                new NotificationCompat.Builder(Main3Activity.this,"default");
        nbuilder.setSmallIcon(R.mipmap.ic_launcher);
        nbuilder.setContentTitle("알림제목");
        nbuilder.setContentText("알람세부 텍스트");
        nbuilder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        nbuilder.setAutoCancel(true);
        // 알림 표시
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        // id값은 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, nbuilder.build());
    }
}
