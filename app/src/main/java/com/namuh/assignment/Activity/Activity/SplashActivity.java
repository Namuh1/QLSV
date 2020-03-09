package com.namuh.assignment.Activity.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namuh.assignment.R;

public class SplashActivity extends AppCompatActivity {
    ImageView imghinh;
    TextView txtten, txtpower, txthoten;
    ProgressBar progressBar;
    static int TimeOut_Millis = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        anhXa();
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, TimeOut_Millis);
        ;
    }

    // tạo animation cho slash
    private void animation() {
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation_fadein);
        imghinh.startAnimation(animation);
        txthoten.startAnimation(animation);
        txtpower.startAnimation(animation);
        txtten.startAnimation(animation);
    }

    // ánh xạ
    private void anhXa() {
        imghinh = findViewById(R.id.hinh);
        txtten = findViewById(R.id.assignment);
        txtpower = findViewById(R.id.power);
        txthoten = findViewById(R.id.hoten);
    }
}
