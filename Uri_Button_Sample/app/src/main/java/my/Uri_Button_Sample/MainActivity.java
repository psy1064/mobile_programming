package my.Uri_Button_Sample;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickedGoogle(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(intent);
    }
    public void onClickedCall(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-1234-5678"));
        startActivity(intent);
    }
    public void onClickedSMS(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:010-1234-5678"));
        intent.putExtra("sms_body","The SMS text");
        startActivity(intent);
    }

    public void onClickedmore(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,"how to use uri in android");
        startActivity(intent);
    }

}
