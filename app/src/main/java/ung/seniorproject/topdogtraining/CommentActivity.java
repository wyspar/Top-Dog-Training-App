package ung.seniorproject.topdogtraining;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CommentActivity extends AppCompatActivity {

    String threadID = " ";
    String commentQuestion = "";
    //create a comment activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            threadID = extras.getString("threadID");
        }

        //User question
        EditText userQuestion = (EditText) findViewById(R.id.create_comment_text);
        userQuestion.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                commentQuestion = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });

        //confirm thread button, confirm_thread_creation
        ImageButton confirm_thread_creation = (ImageButton) findViewById(R.id.confirm_comment_creation);
        confirm_thread_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateComment();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //create comment?
    private void CreateComment() throws IOException, JSONException {
        save(this);
//        finish();
        //close this activity
        Intent gameActivity = new Intent(CommentActivity.this, ThreadActivity.class);
        gameActivity.putExtra("threadID", threadID);
        gameActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear the activity
        startActivity(gameActivity);

    }//End of CreateThread

    public void save(Context context) throws IOException, JSONException {

        //userID,threadQuestion,myUri,threadID
        //JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        int userID = MainActivity.userID;
        jsonObject.put("userID", userID);
        jsonObject.put("commentQuestion", commentQuestion);
        jsonObject.put("threadID", threadID);

        //jsonArray.put(jsonObject);

        // Convert JsonObject to String Format
        String userString = jsonObject.toString();
        // Define the File Path and its Name
        File file = new File(context.getFilesDir(),"comments.json");

        //if it doesnt exist, then make one
        if(!file.exists()){
            FileWriter writer = new FileWriter(file);
            writer.append("");
            writer.flush();
            writer.close();
        }

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        String response = stringBuilder.toString();

        //holy shit this is gross
        String newString = response+userString;

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(newString);
        bufferedWriter.close();
    }

}
 