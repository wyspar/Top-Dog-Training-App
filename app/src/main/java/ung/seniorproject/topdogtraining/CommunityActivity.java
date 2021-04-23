package ung.seniorproject.topdogtraining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class CommunityActivity extends AppCompatActivity {

    int userID = 0;
    int threadID = 0;
    Uri mainUri = null;
    public static String longImageString = "";

    //touch a thread and it will pull up the comments
    private final View.OnTouchListener threadTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }
            return false;
        }
    };

    //listener for creating a new thread
    private final View.OnTouchListener newThreadActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(CommunityActivity.this, createThreadActivity.class);
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
        setContentView(R.layout.activity_community);

        ImageButton createNewThread = (ImageButton) findViewById(R.id.createNewThread);
        createNewThread.setOnTouchListener(newThreadActivityListener);

        File file = new File(this.getFilesDir(),"threads.json");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
                JSONObject jsonObject = (JSONObject) new JSONTokener(line).nextValue();

//                String response = stringBuilder.toString();
//                System.out.println(response);
//
//                JSONObject jsonObject  = new JSONObject(response);

                String userID = jsonObject.get("userID").toString();
                String threadQuestion = jsonObject.get("threadQuestion").toString();
                String threadID = jsonObject.get("threadID").toString();
                String firstUri = "";
                try{firstUri = jsonObject.get("myUri").toString();}catch (Exception e){}
                Bitmap myUri = null;
                if(!firstUri.equals("no")){
                    myUri = getBitmapFromString(firstUri);
                }
                CreateThread(userID, threadQuestion, myUri,threadID);

            }
            bufferedReader.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
 //       Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String threadQuestion = extras.getString("threadQuestion");
//            userID = extras.getInt("userID");
//            String firstUri = extras.getString("myUri");
//            Uri myUri = null;
//            if(!firstUri.equals("fart")){
//                myUri = Uri.parse(firstUri);
//            }
//        }

    }//End of onCreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // perform your action here
        Intent gameActivity = new Intent(CommunityActivity.this, MainActivity.class);
        gameActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear the activity
        startActivity(gameActivity);
    }

    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public void CreateThread(String userID, String threadQuestion, Bitmap myUri, String threadID) {

        LinearLayout thread_view_holder = (LinearLayout) findViewById(R.id.thread_view_holder);

        LayoutInflater inflater = LayoutInflater.from(this); // some context
        View row = inflater.inflate(R.layout.thread_template,null);

        ImageView threadImage = (ImageView) row.findViewById(R.id.thread_icon);
        if(myUri == null){
            threadImage.setImageResource(R.mipmap.default_thread_icon);
        }else{
            threadImage.setImageBitmap(myUri);
        }


        TextView thread_question = (TextView) row.findViewById(R.id.thread_question);
        thread_question.setText(threadQuestion);

        TextView thread_id = (TextView) row.findViewById(R.id.thread_id);
        thread_id.setText(threadID);
       // thread_id.setId(Integer.parseInt(threadID));

        TextView thread_owner = (TextView) row.findViewById(R.id.thread_owner);
        thread_owner.setText("by: "+(userID));

        thread_view_holder.addView(row);

        View.OnTouchListener threadTouchListen = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){
                    ViewGroup view2 = (ViewGroup) v.getParent();//thread question and owner here
                    ViewGroup view3 = (ViewGroup) view2.getParent();

                    Intent gameActivity = new Intent(CommunityActivity.this, ThreadActivity.class);

                    TextView thread_id = (TextView) v.findViewById(R.id.thread_id);
                    ImageView threadImage = (ImageView) v.findViewById(R.id.thread_icon);
                    try{
                        thread_id.getText();
                    }catch (Exception e){
                        try{
                            thread_id = (TextView) view2.findViewById(R.id.thread_id);
                            thread_id.getText();
                        }catch (Exception y){
                            try{
                                thread_id = (TextView) view3.findViewById(R.id.thread_id);
                                thread_id.getText();
                            }catch (Exception u){
                                System.out.println(u);
                            }
                        }
                    }//End of dumb try catch shit
                    try{
                        threadImage.getDrawable();
                    }catch (Exception e){
                        try{
                            threadImage = (ImageView) view2.findViewById(R.id.thread_icon);
                            threadImage.getDrawable();
                        }catch (Exception y){
                            try{
                                threadImage = (ImageView) view3.findViewById(R.id.thread_icon);
                                threadImage.getDrawable();
                            }catch (Exception u){
                                System.out.println(u);
                            }
                        }
                    }//End of dumb try catch shit

                    //threadImage.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable) threadImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    String newImageString = getStringFromBitmap(bitmap);
                    TextView thread_question = (TextView) view2.findViewById(R.id.thread_question);
                    TextView thread_owner = (TextView) view2.findViewById(R.id.thread_owner);

//                    System.out.println(thread_question.getText());
//                    System.out.println(thread_owner.getText());
//                    System.out.println(thread_id.getText());
//                    System.out.println(newImageString);
                    gameActivity.putExtra("threadID", thread_id.getText());
                    longImageString = newImageString;
                    gameActivity.putExtra("thread_question", thread_question.getText());
                    gameActivity.putExtra("thread_owner", thread_owner.getText());
                    startActivity(gameActivity);
                }
                return true;
            }
        };//End of listener

        thread_question.setOnTouchListener(threadTouchListen);
        thread_owner.setOnTouchListener(threadTouchListen);
        threadImage.setOnTouchListener(threadTouchListen);
        //old
//        LinearLayout threadParent = new LinearLayout(this);
//        threadParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        threadParent.setOrientation(LinearLayout.HORIZONTAL);
//        threadParent.setGravity(17);//17 is center
//
//        ImageView threadImage = new ImageView(this);
//        threadImage.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
//        //use default image

//
//        //second layer linearlayout
//        LinearLayout thread_text_holder = new LinearLayout(this);
//        thread_text_holder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        thread_text_holder.setOrientation(LinearLayout.VERTICAL);
//        thread_text_holder.setGravity(17);//17 is center
//
//        TextView thread_question = new TextView(this);
//        thread_question.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));
//        thread_question.setGravity(17);//17 is center
//        thread_question.setText(threadQuestion);
//        thread_question.setTextAppearance(this,R.style.TextAppearance_AppCompat_Body2);
//        thread_question.setTextSize(this.getResources().getDimension(R.dimen.text20sp));
//        //thread_question.getLayoutParams().height = 60;
//
//        TextView thread_owner = new TextView(this);
//        thread_owner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30));
//        thread_owner.setGravity(17);//17 is center
//        thread_owner.setTextSize(this.getResources().getDimension(R.dimen.text16sp));
//        thread_owner.setTextAppearance(this,R.style.TextAppearance_AppCompat_Body2);
//        thread_owner.setText("by: "+Integer.toString(userID));
//
//        thread_text_holder.addView(thread_question);
//        thread_text_holder.addView(thread_owner);
//        threadParent.addView(threadImage);
//        threadParent.addView(thread_text_holder);
//        thread_view_holder.addView(threadParent);
    }
}
