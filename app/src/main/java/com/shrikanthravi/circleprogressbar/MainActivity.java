package com.shrikanthravi.circleprogressbar;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView stickImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimationDrawable animationDrawable = ((AnimationDrawable) getResources().getDrawable(R.drawable.stick_animation));
        stickImageView = findViewById(R.id.stickImageView);
        stickImageView.setBackground(animationDrawable);
        animationDrawable.start();
    }
}
