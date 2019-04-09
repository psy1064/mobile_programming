package my.identify_input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    TextView tvSchool, tvGrade, tvName, tvResult;
    EditText edtSchool, edtName;
    RadioGroup radioGroup;
    Button btnPrint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("학생 신상 정보");
        checkBox = (CheckBox) findViewById(R.id.cbInput);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvGrade = (TextView) findViewById(R.id.tvGrade);
        tvName = (TextView) findViewById(R.id.tvName);
        tvResult = (TextView) findViewById(R.id.tvResult);
        edtSchool = (EditText) findViewById(R.id.edtSchool);
        edtName = (EditText) findViewById(R.id.edName);
        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        btnPrint = (Button) findViewById(R.id.btnPrint);

// 첫 번째 체크박스 체크 시 visible 속성 값을 수정하는 이벤트 처리 소스 추가
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()==true) {
                    tvSchool.setVisibility(View.VISIBLE);
                    tvGrade.setVisibility(View.VISIBLE);
                    tvName.setVisibility(View.VISIBLE);
                    tvResult.setVisibility(View.VISIBLE);
                    edtSchool.setVisibility(View.VISIBLE);
                    edtName.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                    btnPrint.setVisibility(View.VISIBLE);
                } else {
                    tvSchool.setVisibility(View.INVISIBLE);
                    tvGrade.setVisibility(View.INVISIBLE);
                    tvName.setVisibility(View.INVISIBLE);
                    tvResult.setVisibility(View.INVISIBLE);
                    edtSchool.setVisibility(View.INVISIBLE);
                    edtName.setVisibility(View.INVISIBLE);
                    radioGroup.setVisibility(View.INVISIBLE);
                    btnPrint.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 결과 출력 버튼 이벤트 처리
        btnPrint.setOnClickListener(new View.OnClickListener() {
            String schoolName, studentName;
            @Override
            public void onClick(View v) {
                // 입력된 학교 이름 문자열 얻기
                schoolName = edtSchool.getText().toString();
                // 입력된 학생 이름 문자열 얻기
                studentName = edtName.getText().toString();
                // null 체크 하기
                if(schoolName.isEmpty())
                    Toast.makeText(getApplicationContext(),"학교이름을 입력하세요",Toast.LENGTH_LONG).show();
                else if(studentName.isEmpty())
                    Toast.makeText(getApplicationContext(),"학생이름을 입력하세요", Toast.LENGTH_LONG).show();
                else {
                    switch (radioGroup.getCheckedRadioButtonId()){
                        case R.id.radioButton:
                            tvResult.setText(schoolName + " " + "1학년 " + studentName + " 입니다.");
                            break;
                        case R.id.radioButton2:
                            tvResult.setText(schoolName + " " + "2학년 " + studentName + " 입니다.");
                            break;
                        case R.id.radioButton3:
                            tvResult.setText(schoolName + " " + "3학년 " + studentName + " 입니다.");
                            break;
                        case R.id.radioButton4:
                            tvResult.setText(schoolName + " " + "4학년 " + studentName + " 입니다.");
                            break;
                        default:
                            Toast.makeText(getApplicationContext(),"학년을선택하세요",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
