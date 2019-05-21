package my.databaseapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        // 고객 정보를 추가할 전체 레이아웃 인식
        LinearLayout layout = (LinearLayout)findViewById(R.id.detail);

        //정보 추출할 인텐트 호출
        Intent intent = getIntent();
        // 인텐트로 상세 조회할 성명값을 추출함
        String result_name = intent.getStringExtra("name");
        String db_name = "";

        DBManager dbManager = new DBManager(this);
        Cursor cursor = dbManager.selectData(result_name);

        while (cursor.moveToNext()) {
            db_name = cursor.getString(cursor.getColumnIndex("name"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String sms = cursor.getString(cursor.getColumnIndex("sms"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            //레이아웃에 추가할 고객정보(성명)을 위한 텍스트 뷰 생성
            TextView textView = new TextView(this);
            textView.setText(db_name);
            textView.setTextSize(20);
            textView.setTextColor(Color.YELLOW);
            textView.setBackgroundColor(Color.BLUE);
            layout.addView(textView);

            //레이아웃에 추가할 성별, 수신여부를  위한 텍스트 뷰 생성
            TextView textView2 = new TextView(this);
            textView2.setText(gender + "\n");
            textView2.append(sms + "   " + email + "\n");
            layout.addView(textView2);

        }
            cursor.close();
            dbManager.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_settings1:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.action_settings2:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.action_settings3:
                intent = new Intent(this, CustomerListActivity.class);
                break;
        }
        startActivity(intent);
        finish();
        return true;
        // return super.onOptionsItemSelected(item);
    }
}
