package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

import patienthub.binary.com.patienthub.webservice.HttpManager;

public class QuizPage extends Activity {

    //TODO : make the button label change dynamically. Pass in extras including dosageID. Persist it!

    //MISC FIELDS
    String options[] = {  };
    String questionText = "";
    private int currentRadioButtonSelection = -1;
    private String otherText = "";
    private String[] dosageFeedbackIDs = null;
    private String[] dosageNames = null;
    private String token = "";

    //FOR EASY CONFIG
    Class buttonDestination = QuizPage.class;
    Class homeClass = MainMenu.class;
    int numQuestions = 3;

    //PASS IN THESE FIELDS IN ORDER TO DYNAMICALLY CONFIGURE THE QUIZ PAGE
    String buttonString = "Next";
    int questionInt = 0;
    String medName = "medication";

    //QUESTIONS AND ANSWERS
    String quiz1q = "How did you sleep last night?\n";
    String quiz1a[] = { "I slept well", "I woke a few times", "I slept very badly", "Other" };

    String quiz2q = "Are you feeling in control of your recovery?\n";
    String quiz2a[] = { "Yes", "I need some more guidance", "I am feeling very confused", "Other" };

    String quiz3q = "Are you feeling motivated about your recovery?\n";
    String quiz3a[] = { "Very motivated", "A bit lacking", "I am struggling to motivate myself", "Other" };

    String quizMedsQ = "Why didn't you take your " + medName + " today?\n";
    String quizMedsA[] = { "It makes me feel sick", "I'm feeling better", "I've run out", "It's too expensive", "Other" };

    private void selectQuestions(int intSelect){
        switch ( intSelect ) {
            case 0:
                medName = dosageNames[0];
                options = quizMedsA;
                questionText = "Why didn't you take your " + medName + " today?\n";
                break;
            case 1:
                options = quiz1a;
                questionText = quiz1q;
                break;
            case 2:
                options = quiz2a;
                questionText = quiz2q;
                break;
            case 3:
                options = quiz3a;
                questionText = quiz3q;
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            questionInt = extras.getInt("questionNum");
            dosageFeedbackIDs = extras.getStringArray("dosageFeedbackIDs");
            dosageNames = extras.getStringArray("dosageNames");
            numQuestions = extras.getInt("numQuestions");
            token = extras.getString("token");
        }

        selectQuestions(questionInt);

        final LinearLayout rLayout = (LinearLayout) findViewById(R.id.quizView2);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.quizProgressBar);
        bar.setMax(numQuestions);

        if(questionInt == 0){
            int currentQuestion = numQuestions + 1 - dosageFeedbackIDs.length;
            bar.setProgress(currentQuestion);
        }else{
            bar.setProgress(questionInt);
        }


        final TextView question =(TextView) findViewById(R.id.questionMessage);
        question.setText(questionText);

        final RadioButton[] rb = new RadioButton[options.length];
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupQuiz);

        rg.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < options.length; i++) {
            rb[i] = new RadioButton(this);
            rb[i].setPadding(0, 50, 0, 50);
            rb[i].setTextSize(22);
            rg.addView(rb[i]);
            rb[i].setText(options[i]);
        }

        final Button nextButton = (Button) findViewById(R.id.quizNextButton);

        final Button backButton = (Button) findViewById(R.id.quizBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });



        if(questionInt == 0){
            if(dosageFeedbackIDs.length == 1){
                nextButton.setText("Submit");
            }else {
                nextButton.setText("Next");
            }
        }else if (questionInt == numQuestions){
            nextButton.setText("Submit");
        }else{
            nextButton.setText("Next");
        }

        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                apiPost(rg);

                Intent myIntent = null;

                if (questionInt != 0) ++questionInt;

                //if it is a 'treatment' question set
                if (questionInt == 0) {
                    if (dosageFeedbackIDs != null) {
                        if (dosageFeedbackIDs.length > 1) {

                            //take off the first of the IDs
                            dosageFeedbackIDs = Arrays.copyOfRange(dosageFeedbackIDs, 1, dosageFeedbackIDs.length);
                            dosageNames = Arrays.copyOfRange(dosageNames, 1, dosageNames.length);

                            myIntent = new Intent(QuizPage.this, QuizPage.class);
                            myIntent.putExtra("questionNum", questionInt);
                            myIntent.putExtra("dosageFeedbackIDs", dosageFeedbackIDs);
                            myIntent.putExtra("dosageNames", dosageNames);
                            myIntent.putExtra("numQuestions",numQuestions);
                            myIntent.putExtra("token",token);
                        } else {
                            myIntent = new Intent(QuizPage.this, homeClass);
                        }
                    }
                } else if (questionInt <= numQuestions) {
                    myIntent = new Intent(QuizPage.this, buttonDestination);
                    myIntent.putExtra("questionNum", questionInt);
                    myIntent.putExtra("numQuestions",numQuestions);
                    myIntent.putExtra("token",token);

                } else                {
                    myIntent = new Intent(QuizPage.this, homeClass);
                }

                QuizPage.this.startActivity(myIntent);

                finish();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {

                currentRadioButtonSelection = rg.getCheckedRadioButtonId();

                EditText otherField = (EditText) findViewById(R.id.editTextOther);

                if (currentRadioButtonSelection != -1) {
                    nextButton.setEnabled(true);
                }

                int lastID = rg.getChildAt(rg.getChildCount() - 1).getId();

                if (currentRadioButtonSelection == lastID) {
                    otherField.setEnabled(true);
                    otherField.setHint("What's up?");
                } else {
                    otherField.setEnabled(false);
                    otherField.setHint("");
                }
            }
        });
    }

    private void apiPost(RadioGroup rg){

        String answer = "";

        //get the ID of the 'other' radio button
        int lastID = rg.getChildAt(rg.getChildCount() - 1).getId();

        //if that button is selected, set answer to be the contents of it.
        if (currentRadioButtonSelection == lastID) {
            EditText otherField = (EditText) findViewById(R.id.editTextOther);
            answer = otherField.getText().toString();
            if(answer == null) answer = "...No response was provided...null";
            if(answer.length() == 0) answer = "...No response was provided...empty";

            //else set the answer to be the radio button label
        } else {
            RadioButton tempRadio = (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());
            answer = tempRadio.getText().toString();
        }

        if(questionInt == 0){
            dosageFeedbackIDs[0] = dosageFeedbackIDs[0]; // ready to be posted for medication question

            int dosageID = Integer.parseInt(dosageFeedbackIDs[0]);

            HttpManager pOSTer = new HttpManager();
            try {
                System.out.println(pOSTer.postMedicationData(answer, token, dosageID, false));
            } catch (IOException e) {
                e.printStackTrace();
            }


            // ready to be posted for medication question
            answer = answer; // ready to be posted for both types of questions
        }else{
            questionText = questionText; //ready to be posted for quiz question
            answer = answer; // ready to be posted for both types of questions

            HttpManager pOSTer = new HttpManager();
            try {
                System.out.println(pOSTer.postQuizData(questionText, token, answer));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
