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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    TextView alarmText, lightBulbText, tempText;
    Button alarmButton, turnOnButton, turnOffButton;
    CheckBox checkBox;
    NotificationManager notificationManager;
    AlarmManager alarmManager;

    Calendar calendar = Calendar.getInstance();
    Calendar alarmCalendar = Calendar.getInstance();

    FloatingActionButton fab;

    PendingIntent alarmPendingIntent;

    Context context;

    static boolean checkboxChecked = false;
    static int alarmHour ;
    static int alarmMinute ;
    static int timePickerMode = 0;
    public static int alarmMode = 0;
    private static boolean lightbulbSwitch = false;

    private static final String TAG = "TEST+MAactivity";
    // 블루투스 사용
    public static final int MODE_REQUEST = 1 ;
    public static final int MESSAGE_WRITE = 2;
    private static final int STATE_SENDING = 1;
    private static final int STATE_NO_SENDING = 2;
    private int mSendingState ;
    private StringBuffer stringBuffer;
    private BluetoothService bluetoothServiceMain = null;

    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d(TAG,"handle");
            switch (message.what) {
                case MESSAGE_WRITE : {
                    switch (message.arg1) {
                        case 1 : {
                            Log.d(TAG,"handle");
                            tempText = findViewById(R.id.tempText);
                            tempText.setText(message.obj.toString());
                            tempText.setTextSize(30);
                            break;
                        }
                    }
                    break;
                }

            }
        }
    };

    private synchronized void sendMessage( String message, int mode ) {

        if ( mSendingState == STATE_SENDING ) {
            try {
                wait() ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mSendingState = STATE_SENDING ;

        // Check that we're actually connected before trying anything
        if ( bluetoothServiceMain.getState() != BluetoothService.STATE_CONNECTED ) {
            mSendingState = STATE_NO_SENDING ;
            return ;
        }

        // Check that there's actually something to send
        if ( message.length() > 0 ) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes() ;
            bluetoothServiceMain.write(send, mode) ;

            // Reset out string buffer to zero and clear the edit text field
            stringBuffer.setLength(0) ;

        }
        mSendingState = STATE_NO_SENDING ;
        notify() ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changedBackground(); // 시간에 따라 배경 사진 변경
        init(); // findViewById 초기화

        if(bluetoothServiceMain == null) {
            bluetoothServiceMain = initialActivity.btService;
            bluetoothServiceMain.setHandler(handler);
            stringBuffer = new StringBuffer("");
        } // initialActivity의 블루투스 서비스를 가져오고 Handler 만 세팅

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
                switch (timePickerMode) {
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
                        if(bluetoothServiceMain.getState()==BluetoothService.STATE_CONNECTED) {
                            Log.d(TAG,"send success");
                            sendMessage("1", MODE_REQUEST); // ATmega에 전등 키라는 명령 전송
                        }
                        else {
                            Log.e(TAG, "블루투스 연결 오류");
                        }
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
                        Log.d(TAG,"send success");
                        sendMessage("0", MODE_REQUEST); // ATmega에 전등 끄라는 명령 전송
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
                final String item1 [] = {"Circle Mode", "Spinner Mode"};

                final String item2 [] = {"기본","실로폰"};

                int[] selected = {0};
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("설정");
                alertBuilder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 : {
                                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(MainActivity.this);
                                alertBuilder1.setTitle("TimePickerMode 세팅");
                                alertBuilder1.setItems(item1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0: {
                                                Toast.makeText(getApplicationContext(), "Circle Mode", Toast.LENGTH_LONG).show();
                                                timePickerMode = 0;
                                                break;
                                            }
                                            case 1: {
                                                Toast.makeText(getApplicationContext(), "Spinner Mode", Toast.LENGTH_LONG).show();
                                                timePickerMode = 1;
                                                break;
                                            }
                                        }
                                    }
                                });
                                AlertDialog alertDialog1 = alertBuilder1.create();
                                alertDialog1.show();
                                break;
                            }
                            case 1 : {
                                AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(MainActivity.this);
                                alertBuilder1.setTitle("AlarmMode 세팅");
                                alertBuilder1.setItems(item2, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0: {
                                                Toast.makeText(getApplicationContext(), "기본 Mode", Toast.LENGTH_LONG).show();
                                                alarmMode = 0;
                                                break;
                                            }
                                            case 1: {
                                                Toast.makeText(getApplicationContext(), "실로폰 Mode", Toast.LENGTH_LONG).show();
                                                alarmMode = 1;
                                                break;
                                            }
                                        }
                                    }
                                });
                                AlertDialog alertDialog1 = alertBuilder1.create();
                                alertDialog1.show();
                                break;
                            }
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
        } // 세팅한 알람 시간이 현재 시간보다 클 경우
        else if(alarmHour < calendar.get(Calendar.HOUR_OF_DAY)) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis()+interval,alarmPendingIntent);
        } // 세팅한 알람 시간이 현재 시간보다 작을 경우
        else if(alarmHour == calendar.get(Calendar.HOUR_OF_DAY)) {
            if(alarmMinute > calendar.get(Calendar.MINUTE)) {
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),alarmPendingIntent);
            }
            else {
                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis()+interval,alarmPendingIntent);
            }
        }

    }
    public void cancelAlarm() {
        alarmManager.cancel(alarmPendingIntent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료하시겠습니까?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}