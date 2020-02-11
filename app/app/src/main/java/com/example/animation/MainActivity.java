package com.example.animation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {
    TextView txtMessage;
    Button btn;
    Animation animation;
    //  AnimationDrawable rocketanimation;
    //   ImageView imageView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        txtMessage = findViewById(R.id.text);

   /*     imageView = findViewById(R.id.image);

        imageView.setBackgroundResource(R.drawable.anim);
        rocketanimation = (AnimationDrawable) imageView.getBackground();
*/
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        // set animation listener
        animation.setAnimationListener(this);
        btn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        txtMessage.setVisibility(View.VISIBLE);

        // start the animation
        txtMessage.startAnimation(animation);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animation) {
            Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
