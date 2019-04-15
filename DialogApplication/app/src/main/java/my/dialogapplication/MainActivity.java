package my.dialogapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.channels.AlreadyBoundException;

public class MainActivity extends AppCompatActivity {
    Button button, gonoti;
    //Button pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        //pick = (Button) findViewById(R.id.picker);
        //gonoti = (Button) findViewById(R.id.goNoti);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);       // AlertDialog 의 빌더 먼저 생성
                alert.setTitle("알림");
                alert.setMessage("결제하시겠습니까?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage();
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage();
                    }
                });
                alert.setNeutralButton("cancel",null);

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }

            public void showMessage() {
                Toast.makeText(getApplicationContext(),"버튼이 클릭됨",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void open(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button: {
                break;
            }
            case R.id.picker: {
                intent = new Intent(MainActivity.this, Main2Activity.class);     // 화면전환 (현재 화면.this, 가고자 하는 화면.class)
                break;
            }
            case R.id.notibutton: {
                intent = new Intent(MainActivity.this, Main3Activity.class);     // 화면전환 (현재 화면.this, 가고자 하는 화면.class)
                break;
            }
        }
        startActivity(intent);
    }
}
