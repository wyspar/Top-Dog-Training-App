package ung.seniorproject.topdogtraining;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int max = 999999999;
    private static int min = 100000000;
    public static int userID = (int)(Math.random() * (max - min + 1) + min);
    //TODO: use BigInteger class instead?

    private final View.OnTouchListener communityActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(MainActivity.this, CommunityActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener clickerActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(MainActivity.this, ClickerActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener guideActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(MainActivity.this, GuideActivity.class);
                    startActivity(gameActivity);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener mapsActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(MainActivity.this, MapsActivity.class);
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
        setContentView(R.layout.activity_main);



        //The buttons for the menu
        findViewById(R.id.communityButton).setOnTouchListener(communityActivityListener);
        findViewById(R.id.clickerButton).setOnTouchListener(clickerActivityListener);
        findViewById(R.id.guideButton).setOnTouchListener(guideActivityListener);
        findViewById(R.id.mapButton).setOnTouchListener(mapsActivityListener);

    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.layout., menu);
//        return true;
//    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }



}