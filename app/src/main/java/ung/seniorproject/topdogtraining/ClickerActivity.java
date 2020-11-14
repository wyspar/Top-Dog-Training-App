package ung.seniorproject.topdogtraining;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

//TODO: Let Users choose their sound
//TODO: Make Sounds happen

public class ClickerActivity extends AppCompatActivity {

    private static final int[] ITEM_DRAWABLES = {R.mipmap.userownsound,R.mipmap.sound1,R.mipmap.sound2,R.mipmap.sound3,R.mipmap.sound4,};
    int songNumber = 0;//the number of the song selected, 0 default

    Uri myUri = null;//Let it play again if needed?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        //mediaPlayer



        //plays the chosen song
        ImageButton playSoundButton = (ImageButton) findViewById(R.id.playSoundButton);
        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer ring = null;
                switch(songNumber){
                    case 0:
                        if(myUri==null){
                            Intent intent = new Intent();
                            intent.setType("audio/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent,1);
                        }else{
                            try {
                                //and by taken uri you can start your Media
                                MediaPlayer mPlayer = new MediaPlayer();
                                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mPlayer.setDataSource(getApplicationContext(), myUri);
                                mPlayer.prepare();
                                mPlayer.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        ring= MediaPlayer.create(ClickerActivity.this,R.raw.clicker1);
                        ring.start();
                        break;
                    case 2:
                        ring= MediaPlayer.create(ClickerActivity.this,R.raw.clicker2);
                        ring.start();
                        break;
                    case 3:
                        ring= MediaPlayer.create(ClickerActivity.this,R.raw.clicker3);
                        ring.start();
                        break;
                    case 4:
                        ring= MediaPlayer.create(ClickerActivity.this,R.raw.clicker4);
                        ring.start();
                        break;

                }
            }
        });

        //Menu of the different sounds
        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
        initArcMenu(arcMenu, ITEM_DRAWABLES);
    }//End of onCreate



    //Activity to let user choose a sound
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                myUri = data.getData();

//                try {
//                    //and by taken uri you can start your Media
//                    MediaPlayer mPlayer = new MediaPlayer();
//                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mPlayer.setDataSource(getApplicationContext(), myUri);
//                    mPlayer.prepare();
//                    mPlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    songNumber = position;
                    if(songNumber == 0){
                        Intent intent = new Intent();
                        intent.setType("audio/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,1);
                        Toast.makeText(ClickerActivity.this, "User Sound:" + position, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ClickerActivity.this, "Clicker Sound:" + position, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void choosePosition(int chosenSound){

    }
}
