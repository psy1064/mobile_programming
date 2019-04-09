package my.business_card;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView addr, moblie, email, schoolname;
    Button eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );                       // 화면 가로 고정
        setContentView(R.layout.activity_main);

        addr = (TextView)findViewById(R.id.schooladdr);
        moblie = (TextView)findViewById(R.id.phonenumber);
        email = (TextView)findViewById(R.id.email);
        schoolname = (TextView)findViewById(R.id.subschoolname);
        eng = (Button)findViewById(R.id.english);

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.335139, 127.460704"));
                startActivity(intent);
            }
        });
        // 학교 주소 텍스트 클릭 시 이벤트

        moblie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);       // AlertDialog 의 빌더 먼저 생성
                alert.setTitle("choice");
                alert.setMessage("무엇을 하시겠습니까?");
                alert.setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-3538-1064"));
                        startActivity(intent);
                    }
                }); // Call 클릭 시 전화 화면으로 이동
                alert.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:010-3538-1064"));
                        startActivity(intent);
                    }
                }); // SMS 클릭 시 문자 화면으로 이동
                alert.setNeutralButton("cancel",null);

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
        // 폰 번호 텍스트 클릭 시 이벤트

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:sypark1064@gmail.com"));
                startActivity(intent);
            }
        });
        // 이메일 주소 텍스트 클릭 시 이벤트
        schoolname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"대전대학교");
                startActivity(intent);
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        // English 텍스트 클릭 시 이벤트 -> Main2Activity로 이동
    }
}
