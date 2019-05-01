package my.final_project;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView backgroundImage;
    TextView alarmText, lightBulbText;
    Button alarmButton;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        alarmButton = (Button)findViewById(R.id.setAlaramTimeButton);
        changedBackground();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked())
                    alarmButton.setVisibility(View.VISIBLE);
                else
                    alarmButton.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void changedBackground() {
        int hour;
        Calendar calendar = Calendar.getInstance();
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
        else if((hour>18 || hour < 24 ) && (hour > 0 || hour <6))
        {
            backgroundImage.setImageResource(R.drawable.back_night);
            alarmText.setTextColor(Color.WHITE);
            lightBulbText.setTextColor(Color.WHITE);
        }

    }
}
