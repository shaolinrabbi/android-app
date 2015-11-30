package com.perrchick.someapplication.uiexercises;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.perrchick.someapplication.R;
import com.perrchick.someapplication.utilities.PerrFuncs;

public class AnimationsActivity extends AppCompatActivity {
    ImageView spinnerView;
    private RotateAnimation rotateAnimation;
    private TextView txtScaleValue;
    private SeekBar scaleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);

        this.scaleSeekBar = (SeekBar) findViewById(R.id.seekBar);
        this.scaleSeekBar.setProgress(6);
        this.scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += 1;
                scaleImage(progress);

                ObjectAnimator fade = ObjectAnimator.ofFloat(txtScaleValue, "alpha", txtScaleValue.getAlpha(), (float)progress / 10.0f);
                long duration = 300;
                fade.setDuration(duration);
                fade.start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        this.spinnerView = (ImageView) findViewById(R.id.spinnerImage);
        this.spinnerView.setImageResource(R.drawable.ic_spinner_image);
        this.spinnerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSpinnerAnimation();
            }
        });

        this.txtScaleValue = (TextView) findViewById(R.id.txtScaleValue);
        this.txtScaleValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerrFuncs.sayNo(v);
            }
        });
    }

    private void scaleImage(float scaleSize) {
        if (scaleSize > 0) { // Check anyway to prevent exceptions
            txtScaleValue.setText("" + scaleSize);

            scaleSize *= 0.1;
            ObjectAnimator scaleImageX = ObjectAnimator.ofFloat(spinnerView, "scaleX", spinnerView.getScaleX(), scaleSize);
            scaleImageX.setDuration(200);
            scaleImageX.start();

            ObjectAnimator scaleImageY = ObjectAnimator.ofFloat(spinnerView, "scaleY", spinnerView.getScaleY(), scaleSize);
            scaleImageY.setDuration(200);
            scaleImageY.start();
        }
    }

    private void toggleSpinnerAnimation(boolean start) {
        if (start) {
            rotateAnimation = getRotateAnimation(this.spinnerView);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    rotateAnimation = null;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            this.spinnerView.startAnimation(rotateAnimation);
        } else {
            rotateAnimation.setRepeatCount(0);
        }
    }

    private void toggleSpinnerAnimation() {
        this.toggleSpinnerAnimation(rotateAnimation == null);
    }

    @Override
    public void onResume() {
        super.onResume();

        scaleImage(this.scaleSeekBar.getProgress());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_animations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public RotateAnimation getRotateAnimation(View viewToRotate) {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, -360f,viewToRotate.getWidth() / 2.0f,viewToRotate.getHeight() / 2.0f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);

        return rotateAnimation;
    }
}