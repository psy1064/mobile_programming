package my.imageapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

class MyView extends View {
    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    } // xml 이용안할때 생성자

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.jelly);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.kitkat);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(bitmap2,0,300,null);
    } // image bitmap

    /*public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    } // xml 이용할때 생성자*/

    /*@Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);   // 두께
        canvas.drawLine(100,100,700,100,paint);
        canvas.drawRect(100,300,700,700,paint);
        canvas.drawCircle(300,1200,200,paint);
        paint.setTextSize(80);
        canvas.drawText("This is a test",100,900,paint);
    }*/
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);     // xml 사용할때
        setContentView(new MyView(this));        // xml 사용안할때
    }
}
