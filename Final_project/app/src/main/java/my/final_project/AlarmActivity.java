package my.final_project;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    TextView hourText, minuteText;
    Button stopButton;
    MediaPlayer mediaPlayer;
    CheckBox checkBox;
    Button alarmButton;
    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        hourText = (TextView)findViewById(R.id.hourText);
        minuteText = (TextView)findViewById(R.id.minuteText);
        stopButton = (Button)findViewById(R.id.stopButton);
        alarmButton = (Button)findViewById(R.id.setAlarmTimeButton);
        checkBox = (CheckBox)findViewById(R.id.checkBox);


        if(calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 12) {
            hourText.setText("오전 " + calendar.get(Calendar.HOUR_OF_DAY)  +  "시 ");
            minuteText.setText(calendar.get(Calendar.MINUTE) + "분");
        }
        else if(calendar.get(Calendar.HOUR_OF_DAY) == 12) {
            hourText.setText("오후 " + calendar.get(Calendar.HOUR_OF_DAY)  +  "시 ");
            minuteText.setText(calendar.get(Calendar.MINUTE) + "분");
        }
        else if(calendar.get(Calendar.HOUR_OF_DAY) > 12 && calendar.get(Calendar.HOUR_OF_DAY) <24) {
            hourText.setText("오후 " + (calendar.get(Calendar.HOUR_OF_DAY)-12)  +  "시 ");
            minuteText.setText(calendar.get(Calendar.MINUTE) + "분");
        }
        else if(calendar.get(Calendar.HOUR_OF_DAY) == 0) {
            hourText.setText("오전 0시");
            minuteText.setText(calendar.get(Calendar.MINUTE) + "분");
        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ringtone);
        mediaPlayer.start();

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
