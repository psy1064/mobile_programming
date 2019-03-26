package my.table_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // xml을 자바 코드에서 참조하는 방법(시험중요)
        EditText addr = (EditText) findViewById(R.id.addr_edit);    // view를 반환해주는 method에 EditText 형변환
        EditText name = (EditText) findViewById(R.id.name_edit);

        addr.setText("대전동구용운동");
        name.setText("박세용");

        name.setEnabled(false);

        Toast.makeText(this, "Message : <주소와 이름이 변경됨>", Toast.LENGTH_LONG).show();  // Toast.makeText(위치, 문자열, 띄울 시간(간격)).show() <- 띄워주는
    }
}
