package ung.seniorproject.topdogtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.layout., menu);
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.about:
//                startActivity(new Intent(this, About.class));
//                return true;
//            case R.id.help:
//                startActivity(new Intent(this, Help.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}