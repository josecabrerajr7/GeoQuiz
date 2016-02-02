package com.jose.jcdev.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private Integer mScore = 0;



    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void updateScore() {
        mScoreTextView.setText(Integer.toString(this.mScore));
    }

    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;


        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mScore++;
                updateScore();
        } else {
            messageResId = R.string.incorrect_toast;
        }
    }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }

        public QuizActivity() {}


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (data == null) {
                return;
            }
            mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate(Bundle) called");
            setContentView(R.layout.activity_quiz);

            mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
            mScoreTextView = (TextView) findViewById(R.id.score);

            mTrueButton = (Button) findViewById(R.id.true_button);
            mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

            mFalseButton = (Button) findViewById(R.id.false_button);
            mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

            mNextButton = (ImageButton) findViewById(R.id.next_button);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    mIsCheater = false;
                    updateQuestion();
                }
            });

            mPrevButton = (ImageButton) findViewById(R.id.prev_button);
            mPrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            });
            // connecting to CheatActivity
            mCheatButton = (Button)findViewById(R.id.cheat_button);
            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                    // isAnswerTrue() not isTrueQuestion()
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                    startActivityForResult(i, 0);
                }
            });

            if (savedInstanceState != null){
                mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
                mScore = savedInstanceState.getInt(KEY_SCORE, 0);
            }

            updateQuestion();
            updateScore();
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            super.onSaveInstanceState(savedInstanceState);
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
            savedInstanceState.putInt(KEY_SCORE, mScore);
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart() called");
        }
        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause() called");
        }
        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume() called");
        }
        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop() called");
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy() called");
        }
}
