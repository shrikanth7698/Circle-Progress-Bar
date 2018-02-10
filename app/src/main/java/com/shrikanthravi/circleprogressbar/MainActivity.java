package com.shrikanthravi.circleprogressbar;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    ImageView stickImageView;
    SeekBar seekBar;
    CircleProgressBar circleProgressBar;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        circleProgressBar = findViewById(R.id.circleProgressBar);
        circleProgressBar.setMax(100);
        seekBar = findViewById(R.id.progressSeekBar);
        aSwitch = findViewById(R.id.switchStickman);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                circleProgressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    circleProgressBar.hideStickMan();
                }
                else {
                    circleProgressBar.showStickMan();
                }
            }
        });
    }
}
