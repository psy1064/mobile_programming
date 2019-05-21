package my.databaseapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("고객등록");
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
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;
            case R.id.action_settings2:
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;
            case R.id.action_settings3:
                intent = new Intent(MainActivity.this, CustomerListActivity.class);
                break;
        }
        startActivity(intent);
        //finish();
        return true;
        // return super.onOptionsItemSelected(item);
    }

    public void register(View v) {
        EditText et_name = (EditText)findViewById(R.id.name1);
        String str_name = et_name.getText().toString();

        RadioGroup rg_sex = (RadioGroup)findViewById(R.id.radiogroup_gender);
        RadioButton rb_male = (RadioButton)findViewById(R.id.male);
        RadioButton rb_female = (RadioButton)findViewById(R.id.female);
        String str_sex = "";
        if (rg_sex.getCheckedRadioButtonId() == R.id.male) {
            str_sex = rb_male.getText().toString();
        }
        if (rg_sex.getCheckedRadioButtonId() == R.id.female) {
            str_sex = rb_female.getText().toString();
        }

        CheckBox chk_msg1 = (CheckBox)findViewById(R.id.msg1);
        String str_mag1 = "";
        if (chk_msg1.isChecked()) {
            str_mag1 = (String)chk_msg1.getText();
        }
        CheckBox   chk_msg2 = (CheckBox)findViewById(R.id.msg2);
        String str_mag2 = "";
        if (chk_msg2.isChecked()) {
            str_mag2 = (String)chk_msg2.getText();
        }

        Intent it    = new Intent(this, RegisterActivity.class);

        it.putExtra("it_name", str_name);
        it.putExtra("it_sex", str_sex);
        it.putExtra("it_msg1",  str_mag1);
        it.putExtra("it_msg2",  str_mag2);

        startActivity(it);

        finish();
    }
}