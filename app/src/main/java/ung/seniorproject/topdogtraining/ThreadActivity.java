package ung.seniorproject.topdogtraining;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Show the thread that the user clicked on
public class ThreadActivity extends AppCompatActivity {

    public static String threadIDLocal = "";
    public static String threadOwnerLocal = "";
    public static String threadQuestionLocal = "";

    //listener for creating a new thread
    private final View.OnTouchListener newCommentActivityListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    Intent gameActivity = new Intent(ThreadActivity.this, CommentActivity.class);
                    gameActivity.putExtra("threadID", threadIDLocal);
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
        setContentView(R.layout.thread_activity);


        ImageButton createNewComment = (ImageButton) findViewById(R.id.createNewComment);
        createNewComment.setOnTouchListener(newCommentActivityListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                String threadID = extras.getString("threadID");
                String threadQuestion = extras.getString("thread_question");
                String threadowner = extras.getString("thread_owner");
                if(threadowner != null){
                    threadOwnerLocal = threadowner;
                    threadIDLocal = threadID;
                    threadQuestionLocal = threadQuestion;
                }

                ImageView thread_icon = (ImageView) findViewById(R.id.thread_icon);
                TextView thread_owner_view = (TextView) findViewById(R.id.thread_owner);
                TextView thread_question_view = (TextView) findViewById(R.id.thread_question);

                thread_question_view.setText(threadQuestionLocal);
                thread_owner_view.setText(threadOwnerLocal);
                Bitmap newMap = getBitmapFromString(CommunityActivity.longImageString);
                thread_icon.setImageBitmap(newMap);
            }catch (Exception e){
                System.out.println(e);
            }//End of try
            loadComments();
        }
    }//End of oncreate

    public void loadComments(){
        File file = new File(this.getFilesDir(),"comments.json");
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
                String commentQuestion = jsonObject.get("commentQuestion").toString();
                String thread_ID = jsonObject.get("threadID").toString();

                System.out.println("json: "+thread_ID);
                System.out.println("local: "+threadIDLocal);

                if(thread_ID.equals(threadIDLocal)){
                    System.out.println("create comment");
                    createComment(userID, commentQuestion);
                }


            }
            bufferedReader.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//End of loadComments

    public void createComment(String userID, String commentQuestion){
        LinearLayout comment_text_holder = (LinearLayout) findViewById(R.id.thread_comment_holder);

        LayoutInflater inflater = LayoutInflater.from(this); // some context
        View row = inflater.inflate(R.layout.comment_template,null);

        TextView comment_question = (TextView) row.findViewById(R.id.comment_question);
        comment_question.setText(commentQuestion);

        TextView thread_owner = (TextView) row.findViewById(R.id.comment_owner);
        thread_owner.setText("by: "+(userID));

        comment_text_holder.addView(row);
    }//End of create comment

    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // perform your action here
        Intent gameActivity = new Intent(ThreadActivity.this, CommunityActivity.class);
        gameActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear the activity
        startActivity(gameActivity);
    }

}
