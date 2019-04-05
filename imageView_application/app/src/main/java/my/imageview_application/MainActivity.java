package my.imageview_application;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    ImageView imageView;
    TextView contextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.rdGroup);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        imageView = (ImageView)findViewById(R.id.imgView);
        contextText = (TextView)findViewById(R.id.textView);
        registerForContextMenu(contextText);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radioButton : {
                        imageView.setImageResource(R.drawable.jelly);
                        break;
                    }
                    case R.id.radioButton2 : {
                        imageView.setImageResource(R.drawable.kitkat);
                        break;
                    }
                    case R.id.radioButton3 : {
                        imageView.setImageResource(R.drawable.oreo);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch  (item.getItemId()){
            case R.id.apple: {
                Toast.makeText(getApplicationContext(),"선택된 아이템 이름 " + item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.banana: {
                Toast.makeText(getApplicationContext(),"선택된 아이템 이름 " + item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.grape: {
                Toast.makeText(getApplicationContext(),"선택된 아이템 이름 " + item.getTitle(),Toast.LENGTH_LONG).show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("컨텍스트 메뉴");
        menu.add(0,1,0,"배경색 BLUE");
        menu.add(0,2,0,"배경색 RED");
        menu.add(0,3,0,"배경색 YELLOW");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:{
                contextText.setBackgroundColor(Color.BLUE);
                break;
            }
            case 2:{
                contextText.setBackgroundColor(Color.RED);
                break;
            }
            case 3:{
                contextText.setBackgroundColor(Color.YELLOW);
                break;
            }
        }
        return true;
    }

    public void click(View view) {
        PopupMenu pop = new PopupMenu(getApplicationContext(), contextText);
        pop.inflate(R.menu.popup);
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search: {
                        Toast.makeText(getApplicationContext(),"선택된 아이템 "+item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.add: {
                        Toast.makeText(getApplicationContext(),"선택된 아이템 "+item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.edit: {
                        Toast.makeText(getApplicationContext(),"선택된 아이템 "+item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.share: {
                        Toast.makeText(getApplicationContext(),"선택된 아이템 "+item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                return true;
            }
        });
        pop.show();
    }
}

