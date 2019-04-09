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

public class Main2Activity extends AppCompatActivity {
    TextView addr_eng, moblie, email, schoolname_eng;
    Button kr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );                       // 화면 가로 고정
        setContentView(R.layout.activity_main2);

        addr_eng = (TextView)findViewById(R.id.schooladdr_eng);
        moblie = (TextView)findViewById(R.id.phonenumber);
        email = (TextView)findViewById(R.id.email);
        kr = (Button)findViewById(R.id.korean);
        schoolname_eng = (TextView)findViewById(R.id.subschoolname_eng);

        addr_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.335139, 127.460704"));
                startActivity(intent);
            }
        });

        moblie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity.this);       // AlertDialog 의 빌더 먼저 생성
                alert.setTitle("choice");
                alert.setMessage("무엇을 하시겠습니까?");
                alert.setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-3538-1064"));
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:010-3538-1064"));
                        startActivity(intent);
                    }
                });
                alert.setNeutralButton("cancel",null);

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:sypark1064@gmail.com"));
                startActivity(intent);
            }
        });
        schoolname_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"Daejeon University");
                startActivity(intent);
            }
        });
        kr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
