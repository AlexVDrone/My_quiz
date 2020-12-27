package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    private TextView textViewHighscore;
    private int highscore;
    private int mark;
    static final String FILE_NAME = "StudentResultJSON";
    private ArrayList<StudentResult> studentsList = new ArrayList<StudentResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewHighscore = findViewById(R.id.text_view_highscore);
        //loadHighscore();
        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);

        TextView textMark = findViewById(R.id.text_view_mark);
        textMark.setVisibility(View.INVISIBLE);

        TextView srFIO = findViewById(R.id.text_FIO);

        //if(highscore != 0 )
            //textMark.setVisibility(View.VISIBLE);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!srFIO.getText().toString().trim().isEmpty())
                    startQuiz();
                else
                    Toast.makeText(MainActivity.this, "Please input correct FIO", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView textMark = findViewById(R.id.text_view_mark);
        super.onActivityResult(requestCode, resultCode, data);

        StudentResult student = new StudentResult();

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score != highscore) {
                    updateHighscore(score);
                    textMark.setVisibility(View.VISIBLE);
                    String str = getString(R.string.mark);

                    if(highscore <= 2) {
                        textMark.setText(str + " " + 1);
                        student.mark =1;
                    }
                    else if(highscore >= 3 && highscore < 4) {
                        textMark.setText(str + " " + 2);
                        student.mark = 2;
                    }
                    else if(highscore >=5  && highscore < 6) {
                        textMark.setText(str + " " + 3);
                        student.mark = 3;
                    }
                    else if(highscore >= 7 && highscore < 8) {
                        textMark.setText(str + " " + 4);
                        student.mark = 4;
                    }
                    else if(highscore >= 9 && highscore < 10) {
                        textMark.setText(str + " " + 5);
                        student.mark = 5;
                    }
                    else if(highscore >= 11 && highscore < 12) {
                        textMark.setText(str + " " + 6);
                        student.mark = 6;
                    }
                    else if(highscore >= 13 && highscore < 14) {
                        textMark.setText(str + " " + 7);
                        student.mark = 7;
                    }
                    else if(highscore >= 15 && highscore < 16) {
                        textMark.setText(str + " " + 8);
                        student.mark = 8;
                    }
                    else if(highscore >= 17 && highscore < 18){
                        textMark.setText(str + " " +  9);
                        student.mark =9;
                    }
                    else if(highscore >= 19 && highscore <= 20){
                        textMark.setText(str + " " + 10);
                        student.mark =10;
                    }
                }
                TextView textFIO = findViewById(R.id.text_FIO);
                student.name = textFIO.getText().toString();
                textFIO.setText("");

                getJsonFromFile();
                createJSONFile(student);

                QuizDbHelper dbHelper = new QuizDbHelper(this);
                dbHelper.addStudentResult(student);


            }
        }
    }
//    private void loadHighscore() {
//        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
//        textViewHighscore.setText("Correct answers: " + highscore);
//    }
    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Correct answers: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

    public  void createJSONFile(StudentResult sr) {
        File myFile = new File(super.getFilesDir(), FILE_NAME);

        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<StudentResult>>(){}.getType();
            studentsList.add(sr);
            String json = gson.toJson(studentsList, collectionType);

            FileOutputStream fstream = new FileOutputStream(myFile);
            fstream.write(json.getBytes());
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getJsonFromFile() {
        File myFile = new File(super.getFilesDir(), FILE_NAME);

        try {
            if(myFile.exists()){
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<StudentResult>>(){}.getType();
                FileInputStream fstream = new FileInputStream(myFile);
                byte[] bytes = new byte[fstream.available()];
                fstream.read(bytes);
                String json = new String (bytes);
                studentsList = gson.fromJson(json, collectionType);
                fstream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}