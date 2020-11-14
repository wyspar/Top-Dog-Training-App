package ung.seniorproject.topdogtraining;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;



public class MapsActivity extends AppCompatActivity {




    private final View.OnTouchListener groomerActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z10&q=pet groomer");
                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    // Make the Intent explicit by setting the Google Maps package
                    mapIntent.setPackage("com.google.android.apps.maps");
                    // Attempt to start an activity that can handle the Intent
                    startActivity(mapIntent);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener parksActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z10&q=dog park");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    startActivity(mapIntent);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener vetActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z10&q=veterinary");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    startActivity(mapIntent);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private final View.OnTouchListener petStoreActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z10&q=pet store");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    startActivity(mapIntent);
                    break;
                default:
                    break;
            }
            return false;
        }
    };


    //TODO: MAKE MAP
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //The buttons for the menu
        findViewById(R.id.groomerButton).setOnTouchListener(groomerActivityListener);
        findViewById(R.id.VetButton).setOnTouchListener(vetActivityListener);
        findViewById(R.id.petstoreButton).setOnTouchListener(petStoreActivityListener);
        findViewById(R.id.parksButton).setOnTouchListener(parksActivityListener);
    }
}
