package ung.seniorproject.topdogtraining;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


public class GuideActivity extends AppCompatActivity {

    private final View.OnTouchListener sitActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(GuideActivity.this, SitActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener shakeActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(GuideActivity.this, ShakeActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener foodAggressionActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(GuideActivity.this, FoodAggressionActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        findViewById(R.id.sitButton).setOnTouchListener(sitActivityListener);
        findViewById(R.id.shakeButton).setOnTouchListener(shakeActivityListener);
        findViewById(R.id.foodAggressionButton).setOnTouchListener(foodAggressionActivityListener);

    }
}
