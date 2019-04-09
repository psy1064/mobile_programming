package my.listener;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    CheckBox check;
    RadioGroup rdgroup;
    RadioButton red, blue, green;
    TextView txt;
    // 멤버 변수 선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        check = (CheckBox)findViewById(R.id.checkBox);
        rdgroup = (RadioGroup)findViewById(R.id.radio_group);
        red = (RadioButton)findViewById(R.id.rd_red);
        blue = (RadioButton)findViewById(R.id.rd_blue);
        green = (RadioButton)findViewById(R.id.rd_green);
        txt = (TextView)findViewById(R.id.textView);
        // 멤버 변수 초기화

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    check.setText("딸기");
                    Toast.makeText(getApplicationContext(),check.getText().toString(), Toast.LENGTH_LONG).show();
                }
                else {
                    check.setText("사과");
                    Toast.makeText(getApplicationContext(),check.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        rdgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rdgroup.getCheckedRadioButtonId()) {
                    case R.id.rd_red : {
                        txt.setBackgroundColor(Color.RED);
                        txt.setText("RED");
                        break;
                    }
                    case R.id.rd_blue : {
                        txt.setBackgroundColor(Color.BLUE);
                        txt.setText("BLUE");
                        break;
                    }
                    case R.id.rd_green : {
                        txt.setBackgroundColor(Color.GREEN);
                        txt.setText("GREEN");
                        break;
                    }
                }
            }
        });
        // 이벤트 처리 소스 추가
    }
}
