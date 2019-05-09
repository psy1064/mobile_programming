package my.final_project;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    AlarmManager alarmManager;

    Calendar calendar = Calendar.getInstance();
    Calendar alarmCalendar = Calendar.getInstance();

    PendingIntent alarmPendingIntent;
    Intent alarmIntent;

    Context context;
    static boolean checkboxChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(context.ALARM_SERVICE);
        changedBackground(); // 시간에 따라 배경 사진 변경
        init(); // findViewById 초기화

        if (checkboxChecked == true) {
            checkBox.setChecked(true);
            alarmButton.setVisibility(View.VISIBLE);
            setAlarmButtonText();
        } // 체크박스가 체크된 상태였는지 확인
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
                    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
                }
            }
        });
        // 알람 설정 CheckBox
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, TimePickerDialog.THEME_DEVICE_DEFAULT_DARK,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        alarmCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        alarmCalendar.set(Calendar.MINUTE, minute);
                        Toast.makeText(getApplicationContext(), "알람 시간 = " + alarmCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분입니다.",Toast.LENGTH_LONG).show();

                        setAlarmButtonText();
                        showNotify();
                        setAlarm();
                    }
                },alarmCalendar.get(Calendar.HOUR_OF_DAY),alarmCalendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });
        // 알람 시간 설정 버튼
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
        // 전등 On 버튼
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
        // 전등 Off 버튼
    }

    public void init() {
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        alarmButton = (Button)findViewById(R.id.setAlarmTimeButton);
        turnOnButton = (Button)findViewById(R.id.turnOnButton);
        turnOffButton = (Button)findViewById(R.id.turnOffButton);
    }
    // findViewById 초기화
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
    // 시간에 따라 배경 사진 변경
    public void showNotify() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this,"default");
        nbuilder.setSmallIcon(R.drawable.alarm);
        nbuilder.setContentTitle("알람");
        //nbuilder.setAutoCancel(true);
        if(alarmCalendar.get(Calendar.HOUR_OF_DAY) > 0 && alarmCalendar.get(Calendar.HOUR_OF_DAY) < 12)
            nbuilder.setContentText("설정된 알람 시간은 오전 " + alarmCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) == 12)
            nbuilder.setContentText("설정된 알람 시간은 오후 " + alarmCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) > 12 && alarmCalendar.get(Calendar.HOUR_OF_DAY) <24)
            nbuilder.setContentText("설정된 알람 시간은 오후 " + (alarmCalendar.get(Calendar.HOUR_OF_DAY)-12) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) == 0)
            nbuilder.setContentText("설정된 알람 시간은 오전 " + "0시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        nbuilder.setContentIntent(pendingIntent);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                    new NotificationChannel("default", "기본채널",NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,nbuilder.build());
    }
    // Notification 활성화d
    public void setAlarmButtonText() {
        if(alarmCalendar.get(Calendar.HOUR_OF_DAY) > 0 && alarmCalendar.get(Calendar.HOUR_OF_DAY) < 12)
            alarmButton.setText("오전 " + alarmCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) == 12)
            alarmButton.setText("오후 " + alarmCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) > 12 && alarmCalendar.get(Calendar.HOUR_OF_DAY) <24)
            alarmButton.setText("오후 " + (alarmCalendar.get(Calendar.HOUR_OF_DAY)-12) + "시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
        else if(alarmCalendar.get(Calendar.HOUR_OF_DAY) == 0)
            alarmButton.setText("오전 " + "0시 " + alarmCalendar.get(Calendar.MINUTE) + "분");
    }
    // 알람 설정 버튼의 텍스트 설정
    public void setAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),pendingIntent);
        //intent.putExtra("Hour", alarmCalendar.get(Calendar.HOUR_OF_DAY));
        //intent.putExtra("Minute", alarmCalendar.get(Calendar.MINUTE));
        //startActivity(intent);
    }
    public void cancelAlarm() {
        alarmManager.cancel(alarmPendingIntent);
        alarmIntent.putExtra("state","alarm off");
        sendBroadcast(alarmIntent);
    }

}
