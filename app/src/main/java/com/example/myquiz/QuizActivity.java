package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;
    private long backPressedTime;

    private CheckBox cb1, cb2, cb3, cb4;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);

        cb1 = findViewById(R.id.checkBox1);///////////////////
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);
        cb4 = findViewById(R.id.checkBox4);

        cb1.setVisibility(View.INVISIBLE);
        cb2.setVisibility(View.INVISIBLE);
        cb3.setVisibility(View.INVISIBLE);
        cb4.setVisibility(View.INVISIBLE);

        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        imageView = findViewById(R.id.image_view1);
        imageView.setVisibility(View.INVISIBLE);

        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();
        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = new QuizDbHelper(this);
            questionList = dbHelper.getAllQuestions();
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);
            showNextQuestion();
        }
        else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            //score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);
            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    }
                    else if(cb1.isChecked() || cb2.isChecked() ||cb3.isChecked() ||cb4.isChecked()){
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);

        cb1.setTextColor(textColorDefaultRb);///////////////////
        cb2.setTextColor(textColorDefaultRb);
        cb3.setTextColor(textColorDefaultRb);
        cb4.setTextColor(textColorDefaultRb);

        rbGroup.clearCheck();
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            if(currentQuestion.getQuestion().equals("Image1"))
            {
                imageView.setVisibility(View.VISIBLE);

                textViewQuestion.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.image_one);
                imageView.getLayoutParams().height = 500;
                imageView.requestLayout();
            }
            else if(currentQuestion.getQuestion().equals("Image2"))
            {
                imageView.setVisibility(View.VISIBLE);
                textViewQuestion.setVisibility(View.INVISIBLE);
//                imageView.getLayoutParams().height = 800;
//                imageView.getLayoutParams().width = 800;
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.requestLayout();
                imageView.setImageResource(R.drawable.image_second);

            }
            else if(currentQuestion.getQuestion().equals("Image3"))
            {
                imageView.setVisibility(View.VISIBLE);
                textViewQuestion.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.image_three);
            }
            else {
                imageView.setVisibility(View.INVISIBLE);
                textViewQuestion.setVisibility(View.VISIBLE);
                textViewQuestion.setText(currentQuestion.getQuestion());
            }

            int checkAnsr = currentQuestion.getAnswerNr();/////////////////////
            String answr = String.valueOf(checkAnsr);
            if(checkAnsr != 1 && checkAnsr != 2 && checkAnsr != 3 && checkAnsr != 4)
            {
                rb1.setVisibility(View.INVISIBLE);
                rb2.setVisibility(View.INVISIBLE);
                rb3.setVisibility(View.INVISIBLE);
                rb4.setVisibility(View.INVISIBLE);

                cb1.setVisibility(View.VISIBLE);
                cb2.setVisibility(View.VISIBLE);
                cb3.setVisibility(View.VISIBLE);
                cb4.setVisibility(View.VISIBLE);

                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);

                cb1.setText(currentQuestion.getOption1());
                cb2.setText(currentQuestion.getOption2());
                cb3.setText(currentQuestion.getOption3());
                cb4.setText(currentQuestion.getOption4());
            }
            else {
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);

                cb1.setVisibility(View.INVISIBLE);
                cb2.setVisibility(View.INVISIBLE);
                cb3.setVisibility(View.INVISIBLE);
                cb4.setVisibility(View.INVISIBLE);

                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.VISIBLE);
                rb4.setVisibility(View.VISIBLE);

                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
            }
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }
    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();

        int checkAnsr = currentQuestion.getAnswerNr();/////////////////////
        String answr = String.valueOf(checkAnsr);

        if(checkAnsr != 1 && checkAnsr != 2 && checkAnsr != 3 && checkAnsr != 4)
        {
            String ans1 = answr.substring(0,1);
            String ans2 = answr.substring(1,2);

            if(Integer.parseInt(ans1) == 1 && Integer.parseInt(ans2) == 2) {
                if (cb1.isChecked() && cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
            if(Integer.parseInt(ans1) == 1 && Integer.parseInt(ans2) == 3) {
                if (cb1.isChecked() && cb3.isChecked() && !cb2.isChecked() && !cb4.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
            if(Integer.parseInt(ans1) == 1 && Integer.parseInt(ans2) == 4) {
                if (cb1.isChecked() && cb4.isChecked() && !cb2.isChecked() && !cb3.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
            if(Integer.parseInt(ans1) == 2 && Integer.parseInt(ans2) == 3) {
                if (cb2.isChecked() && cb3.isChecked() && !cb1.isChecked() && !cb4.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
            if(Integer.parseInt(ans1) == 2 && Integer.parseInt(ans2) == 4) {
                if (cb2.isChecked() && cb4.isChecked() && !cb1.isChecked() && !cb3.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
            if(Integer.parseInt(ans1) == 3 && Integer.parseInt(ans2) == 4) {
                if (cb3.isChecked() && cb4.isChecked() && !cb1.isChecked() && !cb2.isChecked()) {
                    score++;
                    textViewScore.setText("Score: " + score);
                }
            }
        }
        else
        {
            RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
            int answerNr = rbGroup.indexOfChild(rbSelected) + 1;
            if (answerNr == currentQuestion.getAnswerNr()) {
                score++;
                textViewScore.setText("Score: " + score);
            }
        }
        showSolution();
    }
    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        cb1.setTextColor(Color.RED);
        cb2.setTextColor(Color.RED);
        cb3.setTextColor(Color.RED);
        cb4.setTextColor(Color.RED);

        imageView.setVisibility(View.INVISIBLE);
        textViewQuestion.setVisibility(View.VISIBLE);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 4 is correct");
                break;
            case 12:
                cb1.setTextColor(Color.GREEN);
                cb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 and 2 is correct");
                break;
            case 13:
                cb1.setTextColor(Color.GREEN);
                cb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 and 3 is correct");
                break;
            case 14:
                cb1.setTextColor(Color.GREEN);
                cb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 and 4 is correct");
                break;
            case 23:
                cb2.setTextColor(Color.GREEN);
                cb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 and 3 is correct");
                break;
            case 24:
                cb2.setTextColor(Color.GREEN);
                cb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 and 4 is correct");
                break;
            case 34:
                cb3.setTextColor(Color.GREEN);
                cb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 and 4 is correct");
                break;
        }
        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }
    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        //outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}