package ung.seniorproject.topdogtraining;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import ung.seniorproject.topdogtraining.CommunityActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Let user create thread
public class createThreadActivity extends AppCompatActivity {
    Uri myUri = null;//hold image
    String threadQuestion = "";
    int threadID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_thread);

        AppCompatButton chooseImage = (AppCompatButton) findViewById(R.id.user_chooseImage_button);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }
        });

        //User question
        EditText userQuestion = (EditText) findViewById(R.id.create_thread_text);
        userQuestion.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                threadQuestion = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });

        //confirm thread button, confirm_thread_creation
        ImageButton confirm_thread_creation = (ImageButton) findViewById(R.id.confirm_thread_creation);
        confirm_thread_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateThread();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }//End of onCreate

    //Activity to let user choose an image
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                //the selected audio.
                myUri = data.getData();
                ImageView chosenImage = (ImageView) findViewById(R.id.user_chosen_image);
                chosenImage.setImageURI(myUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//End of onActivityResult

    public void setThreadID(){
        int max = 999999999;
        int min = 100000000;
        threadID = (int)(Math.random() * (max - min + 1) + min);
    }

    //create thread?
    private void CreateThread() throws IOException, JSONException {

//        gameActivity.putExtra("userID", userID);
//        gameActivity.putExtra("threadQuestion", threadQuestion);
//        if(myUri != null){
//            gameActivity.putExtra("myUri", myUri.toString());//Have to set it to a string then convert it
//        }else{
//            gameActivity.putExtra("myUri","fart");
//        }
        setThreadID();
        save(this);
      //  finish();
        //close this activity
        Intent gameActivity = new Intent(createThreadActivity.this, CommunityActivity.class);
        gameActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear the activity
        startActivity(gameActivity);

    }//End of CreateThread

    public void save(Context context) throws IOException, JSONException {

        //userID,threadQuestion,myUri,threadID
        //JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        int userID = MainActivity.userID;
        jsonObject.put("userID", userID);
        jsonObject.put("threadQuestion", threadQuestion);
        if(myUri == null){
            jsonObject.put("myUri", "no");
        }else{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
            String newMap = getStringFromBitmap(bitmap);
            jsonObject.put("myUri", newMap);
        }

        jsonObject.put("threadID", threadID);

        //jsonArray.put(jsonObject);

        // Convert JsonObject to String Format
        String userString = jsonObject.toString();
        // Define the File Path and its Name
        File file = new File(context.getFilesDir(),"threads.json");

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
    }//End of save

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

}
