package my.adapterapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import my.adapterapplication.R;

public class ProgressBarEx extends AppCompatActivity {
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_ex);
        progress = new ProgressDialog(this);
    }

    public void start(View view) {
        progress.setCancelable(true);
        progress.setMessage("네트워크에서 다운로드 중입니다. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.setMax(1000);
        progress.show();

        final Thread t = new Thread() {
            @Override
            public void run() {

                int time = 0;
                while (time < 100) {
                    try {
                        sleep(200);
                        time += 5;
                        progress.setProgress(time);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();
    }
}