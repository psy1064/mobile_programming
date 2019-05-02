package my.final_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class initialActivity extends AppCompatActivity {
    Button bluetoothbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        bluetoothbtn = (Button)findViewById(R.id.bluetoothButton);
        bluetoothbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(initialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
