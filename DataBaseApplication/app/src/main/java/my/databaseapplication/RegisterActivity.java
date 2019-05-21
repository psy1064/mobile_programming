package my.databaseapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.RegionIterator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("고객정보");

        dbManager = new DBManager(this);

        Intent it = getIntent();
        String str_name = it.getStringExtra("it_name");
        String str_sex  = it.getStringExtra("it_sex");
        String str_mag1 = it.getStringExtra("it_msg1");
        String str_mag2 = it.getStringExtra("it_msg2");

        boolean result = dbManager.insertData(str_name,str_sex,str_mag1,str_mag2);

        AlertDialog.Builder builder;
        if(result == true) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("데이터 입력 결과");
            builder.setPositiveButton("성공",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("데이터 입력 결과");
            builder.setPositiveButton("실패",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        TextView tv_name   = (TextView)findViewById(R.id.name1);
        TextView tv_gender = (TextView)findViewById(R.id.gender);
        TextView tv_msg    = (TextView)findViewById(R.id.msg);

        tv_name.append(": " + str_name);
        tv_gender.append(": " + str_sex);
        tv_msg.append(": " + str_mag1 + " " + str_mag2);
    }

    public void goBack(View v) {
        Intent it    = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
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
