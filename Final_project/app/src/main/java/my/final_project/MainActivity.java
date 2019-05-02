package my.final_project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView backgroundImage;
    TextView alarmText, lightBulbText;
    Button alarmButton, turnOnButton, turnOffButton;
    CheckBox checkBox;
    NotificationManager notificationManager;
    Calendar calendar = Calendar.getInstance();
    int alarmHour = 0;
    int alarmMinute = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changedBackground();

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        alarmButton = (Button)findViewById(R.id.setAlarmTimeButton);
        turnOnButton = (Button)findViewById(R.id.turnOnButton);
        turnOffButton = (Button)findViewById(R.id.turnOffButton);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked())
                    alarmButton.setVisibility(View.VISIBLE);
                else {
                    Toast.makeText(getApplicationContext(),"알람 설정이 초기화 되었습니다.", Toast.LENGTH_LONG).show();
                    alarmButton.setVisibility(View.INVISIBLE);
                    alarmButton.setText("알람시간설정");
                    alarmHour = 0;
                    alarmMinute = 0;
                }
            }
        });
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotify();
                final TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarmHour = hourOfDay;
                        alarmMinute = minute;
                        alarmButton.setText(alarmHour + " 시" + alarmMinute + " 분");
                        Toast.makeText(getApplicationContext(), "알람 시간 = " + alarmHour + "시" + alarmMinute + "분 입니다.",Toast.LENGTH_LONG).show();

                    }
                },alarmHour,alarmMinute,true);
                timePickerDialog.show();

            }
        });
        turnOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("알림");
                alert.setMessage("전등을 키시겠습니까?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Turn On", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        turnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("알림");
                alert.setMessage("전등을 끄시겠습니까?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Turn Off", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        /*while(true)
        {
            if(alarmHour == calendar.get(Calendar.HOUR_OF_DAY))
            {
                Toast.makeText(getApplicationContext(),"Corret",Toast.LENGTH_LONG).show();
            }
        }*/
    }
    public void changedBackground() {
        int hour;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        backgroundImage = (ImageView) findViewById(R.id.background);
        alarmText = (TextView) findViewById(R.id.alarmText);
        lightBulbText = (TextView) findViewById(R.id.lightBulbText);
        if(hour>6 && hour<18)
        {
            backgroundImage.setImageResource(R.drawable.back_sky);
            alarmText.setTextColor(Color.BLACK);
            lightBulbText.setTextColor(Color.BLACK);
        }
        else if((hour>18 && hour < 24 ) || (hour > 0 && hour <6))
        {
            backgroundImage.setImageResource(R.drawable.back_night);
            alarmText.setTextColor(Color.WHITE);
            lightBulbText.setTextColor(Color.WHITE);
        }

    }
    public void showNotify() {
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this,"default");
        nbuilder.setSmallIcon(R.drawable.alarm);
        nbuilder.setContentTitle("알람");
        nbuilder.setContentText("설정된 알람 시간은 " + alarmHour + " 시" + alarmMinute + " 분 입니다");

        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,nbuilder.build());
    }
}