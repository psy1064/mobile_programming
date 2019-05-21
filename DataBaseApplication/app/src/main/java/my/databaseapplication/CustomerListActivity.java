package my.databaseapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomerListActivity extends AppCompatActivity {
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        dbManager = new DBManager(this);

        Cursor cursor = dbManager.selectAll();

        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainLayout);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String sms = cursor.getString(cursor.getColumnIndex("sms"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            LinearLayout layout_list = new LinearLayout(this);

            layout_list.setOrientation(LinearLayout.VERTICAL);
            layout_list.setPadding(20,10,20,10);

            final TextView tvList = new TextView(this);
            tvList.setText(name);
            tvList.setTextSize(20);
            tvList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomerListActivity.this, CustomerDetailActivity.class);
                    intent.putExtra("name", tvList.getText().toString());
                    startActivity(intent);
                    finish();
                }
            });
            layout_list.addView(tvList);

            TextView tvList1 = new TextView(this);
            tvList1.setText(gender + '\n');
            tvList1.append(sms + " " +  email + " " + '\n');
            layout_list.addView(tvList1);
            Log.e("Cursor", "hi");
            mainLayout.addView(layout_list);

        }
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
