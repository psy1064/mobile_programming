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
import android.support.design.widget.FloatingActionButton;
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

    FloatingActionButton fab;

    PendingIntent alarmPendingIntent;
    Intent alarmIntent;

    Context context;
    static boolean checkboxChecked = false;
    static int alarmHour ;
    static int alarmMinute ;
    static int setTimePickerValue = 0;
    static int alarmMode = 0;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changedBackground(); // 시간에 따라 배경 사진 변경
        init(); // findViewById 초기화
        //registerForContextMenu(fab);

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
                    cancelAlarm();
                }
            }
        });
        // 알람 설정 CheckBox
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                switch (setTimePickerValue) {
                    case 0: {
                        final TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this ,TimePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                alarmCalendar.set(Calendar.MINUTE, minute);
                                alarmCalendar.set(Calendar.SECOND,00);
                                alarmHour = hourOfDay;
                                alarmMinute = minute;
                                Toast.makeText(getApplicationContext(), "알람 시간 = " + alarmHour + "시 " + alarmMinute + "분입니다.",Toast.LENGTH_LONG).show();

                                setAlarmButtonText();

                                showNotify();
                                setAlarm();
                            }
                        },alarmHour,alarmMinute,false);
                        timePickerDialog.show();
                        break;
                    }

                    case 1: {
                        final TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                alarmCalendar.set(Calendar.MINUTE, minute);
                                alarmCalendar.set(Calendar.SECOND,00);
                                alarmHour = hourOfDay;
                                alarmMinute = minute;
                                Toast.makeText(getApplicationContext(), "알람 시간 = " + alarmHour + "시 " + alarmMinute + "분입니다.",Toast.LENGTH_LONG).show();

                                setAlarmButtonText();

                                showNotify();
                                setAlarm();
                            }
                        },alarmHour,alarmMinute,false);
                        timePickerDialog.show();
                        break;
                    }

                }

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item [] = {"TimePickerMode 세팅", "AlarmMode 세팅"};
                final String item2 [] = {"Circle Mode", "Spinner Mode"};
                int[] selected = {0};
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("설정");
                alertBuilder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(MainActivity.this);
                                alertBuilder1.setTitle("TimePickerMode 세팅");
                                alertBuilder1.setItems(item2, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0 :
                                                Toast.makeText(getApplicationContext(), "Circle Mode", Toast.LENGTH_LONG).show();
                                                setTimePickerValue = 0;
                                                break;
                                            case 1:
                                                Toast.makeText(getApplicationContext(), "Spinner Mode", Toast.LENGTH_LONG).show();
                                                setTimePickerValue = 1;
                                                break;
                                        }
                                    }
                                });
                                AlertDialog alertDialog1 = alertBuilder1.create();
                                alertDialog1.show();
                        }
                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();

            }
        });

    }
    public void init() {
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        alarmButton = (Button)findViewById(R.id.setAlarmTimeButton);
        turnOnButton = (Button)findViewById(R.id.turnOnButton);
        turnOffButton = (Button)findViewById(R.id.turnOffButton);
        fab = (FloatingActionButton)findViewById(R.id.fab);
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
    // Notification 활성화
    public void setAlarmButtonText() {
        if(alarmHour > 0 && alarmHour < 12)
            alarmButton.setText("오전 " + alarmHour + "시 " + alarmMinute + "분");
        else if(alarmHour == 12)
            alarmButton.setText("오후 " + alarmHour + "시 " + alarmMinute + "분");
        else if(alarmHour > 12 && alarmHour <24)
            alarmButton.setText("오후 " + (alarmHour-12) + "시 " + alarmMinute + "분");
        else if(alarmHour == 0)
            alarmButton.setText("오전 " + "0시 " + alarmMinute + "분");
    }
    // 알람 설정 버튼의 텍스트 설정
    public void setAlarm() {
        int interval = 1000 * 60 * 60 * 24 ;
        // 설정된 알람 시간이 현재 시간보다 작을 경우 다음날 알람으로 적용해줘야 하는데 필요한 변수
        alarmManager = (AlarmManager) getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmActivity.class);
        alarmPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if(alarmHour > calendar.get(Calendar.HOUR_OF_DAY)) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),alarmPendingIntent);
            intent.putExtra("alarmMode",alarmMode);
        } // 세팅한 알람 시간이 현재 시간보다 클 경우
        else if(alarmHour < calendar.get(Calendar.HOUR_OF_DAY)) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis()+interval,alarmPendingIntent);
        } // 세팅한 알람 시간이 현재 시간보다 작을 경우
        else if(alarmHour == calendar.get(Calendar.HOUR_OF_DAY)) {
            if(alarmMinute > calendar.get(Calendar.MINUTE))
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),alarmPendingIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis()+interval,alarmPendingIntent);
        }
    }
    public void cancelAlarm() {
        alarmManager.cancel(alarmPendingIntent);
    }
}