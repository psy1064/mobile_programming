package my.table_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        Button bt1 = new Button(this);
        bt1.setText("첫번째 버튼");
        container.addView(bt1);

        Button bt2 = new Button(this);
        bt2.setText("두번째 버튼");

        bt2.setEnabled(false);

        container.addView(bt2);

        setContentView(container);
    }
}
