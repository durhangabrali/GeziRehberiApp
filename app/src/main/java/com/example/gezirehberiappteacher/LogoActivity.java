package com.example.gezirehberiappteacher;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.example.gezirehberiappteacher.databinding.ActivityLogoBinding;

public class LogoActivity extends AppCompatActivity {
   private ActivityLogoBinding activityLogoBinding;
   ZamanSayaci timer;
   int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        //-----------------------------
        activityLogoBinding = ActivityLogoBinding.inflate(getLayoutInflater());
        View view = activityLogoBinding.getRoot();

        timer = new ZamanSayaci(5000,1000);
        i = 0;
        timer.start();
    }

    class ZamanSayaci extends CountDownTimer{
        public ZamanSayaci(long millisaInFuture, long countDownInterval ){
            super(millisaInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l){
           i++;
        }
        @Override
        public void onFinish(){
            Intent intent = new Intent(LogoActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}