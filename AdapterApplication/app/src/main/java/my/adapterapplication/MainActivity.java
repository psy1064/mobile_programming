package my.adapterapplication;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] value = {"Apple", "Apricot","Avocado","Banana","Blackberry","Blueberry","Cherry","Apple", "Apricot","Avocado","Banana","Blackberry","Blueberry","Cherry","Apple", "Apricot","Avocado","Banana","Blackberry","Blueberry","Cherry"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, value);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this,item+" selected", Toast.LENGTH_LONG).show();
    }

}
