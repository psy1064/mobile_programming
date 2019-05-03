package my.final_project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
    static int alarmHour = 99;
    static int alarmMinute = 99;
    static boolean checkboxChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changedBackground();

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        alarmButton = (Button)findViewById(R.id.setAlarmTimeButton);
        turnOnButton = (Button)findViewById(R.id.turnOnButton);
        turnOffButton = (Button)findViewById(R.id.turnOffButton);
        if (checkboxChecked == true) {
            checkBox.setChecked(true);
            alarmButton.setVisibility(View.VISIBLE);
            if(alarmHour > 0 && alarmHour < 12)
                alarmButton.setText("오전 " + alarmHour + "시 " + alarmMinute + "분");
            else if(alarmHour == 12)
                alarmButton.setText("오후 " + alarmHour + "시 " + alarmMinute + "분");
            else if(alarmHour > 12 && alarmHour <24)
                alarmButton.setText("오후 " + (alarmHour-12) + "시 " + alarmMinute + "분");
            else if(alarmHour == 0)
                alarmButton.setText("오전 " + "0시 " + alarmMinute + "분");
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()) {
                    alarmButton.setVisibility(View.VISIBLE);
                    checkboxChecked = true;
                }
                else {
                    Toast.makeText(getApplicationContext(),"알람 설정이 초기화 되었습니다.", Toast.LENGTH_LONG).show();
                    alarmButton.setVisibility(View.INVISIBLE);
                    alarmButton.setText("알람시간설정");
                    checkboxChecked = false;
                    alarmHour = 99;
                    alarmMinute = 99;
                    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
                }
            }
        });
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarmHour = hourOfDay;
                        alarmMinute = minute;
                        if(alarmHour > 0 && alarmHour < 12)
                            alarmButton.setText("오전 " + alarmHour + "시 " + alarmMinute + "분");
                        else if(alarmHour == 12)
                            alarmButton.setText("오후 " + alarmHour + "시 " + alarmMinute + "분");
                        else if(alarmHour > 12 && alarmHour <24)
                            alarmButton.setText("오후 " + (alarmHour-12) + "시 " + alarmMinute + "분");
                        else if(alarmHour == 0)
                            alarmButton.setText("오전 " + "0시 " + alarmMinute + "분");
                        Toast.makeText(getApplicationContext(), "알람 시간 = " + alarmHour + "시 " + alarmMinute + "분입니다.",Toast.LENGTH_LONG).show();
                        showNotify();
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
                alert.setMessage("전등을 끄시겠습니까 ?");
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
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this,"default");
        nbuilder.setSmallIcon(R.drawable.alarm);
        nbuilder.setContentTitle("알람");
        //nbuilder.setAutoCancel(true);
        if(alarmHour > 0 && alarmHour < 12)
            nbuilder.setContentText("설정된 알람 시간은 오전 " + alarmHour + "시 " + alarmMinute + "분");
        else if(alarmHour == 12)
            nbuilder.setContentText("설정된 알람 시간은 오후 " + alarmHour + "시 " + alarmMinute + "분");
        else if(alarmHour > 12 && alarmHour <24)
            nbuilder.setContentText("설정된 알람 시간은 오후 " + (alarmHour-12) + "시 " + alarmMinute + "분");
        else if(alarmHour == 0)
            nbuilder.setContentText("설정된 알람 시간은 오전 " + "0시 " + alarmMinute + "분");
        nbuilder.setContentIntent(pendingIntent);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    new NotificationChannel("default", "기본채널",NotificationManager.IMPORTANCE_DEFAULT));
        }

        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,nbuilder.build());
    }
}