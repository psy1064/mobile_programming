package my.dialogapplication;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    Button date, time;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        date = (Button) findViewById(R.id.date);
        time = (Button) findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            int year = 0;
            int month = 0;
            int m_day = 0;
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                m_day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month+1) + "/"    + year);
                    }
                }, year, month, m_day);
                datePickerDialog.show();
            }
        });
    }
}
