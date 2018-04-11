package android.lalita.com.myfi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv = (TextView) findViewById(R.id.tv);
        Typeface custfont = Typeface.createFromAsset(getAssets(),"fonts/sweet.ttf");
        tv.setTypeface(custfont);
        iv = (ImageView) findViewById(R.id.iv);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.transition);
        tv.startAnimation(anim);
        iv.startAnimation(anim);
        final Intent i = new Intent(this,SignIn.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

}
