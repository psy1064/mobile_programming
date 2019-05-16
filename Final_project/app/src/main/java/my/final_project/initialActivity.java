package my.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class initialActivity extends AppCompatActivity {
    Button bluetoothOnButton;
    Button bluetoothListButton;
    // Debugging
    private static final String TAG = "initial";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private BluetoothService btService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_initial);

        bluetoothOnButton = (Button)findViewById(R.id.bluetoothOn);
        bluetoothListButton = (Button)findViewById(R.id.bluetoothList);
        if(btService == null) {
            // btService = new BluetoothService(this, mHandler);
            btService = new BluetoothService(this);
        }
        bluetoothOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btService.getDeviceState()) {
                    // 블루투스가 지원 가능한 기기일 때
                    btService.enableBluetooth();


                } else {
                    // finish();
                }

            }
        });

        bluetoothListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btService.scanDevice();
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);

        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {

                } else {
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(initialActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
