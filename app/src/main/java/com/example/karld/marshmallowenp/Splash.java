package com.example.karld.marshmallowenp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        Animation splashAnim = AnimationUtils.loadAnimation(this,R.anim.splashtransition);
        tv.startAnimation(splashAnim);
        iv.startAnimation(splashAnim);
        final Intent i = new Intent(this,LoginActivity.class);
        Thread ticker = new Thread()
        {
            public void run ()
            {
                try {
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(i);
                    finish();
                }
            }
        };

        ticker.start();
    }
}
