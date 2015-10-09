package patienthub.binary.com.patienthub;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;

public class QuizPage extends ActionBarActivity {

    //TODO : make the button label change dynamically. Pass in extras including dosageID. Persist it!

    //MISC FIELDS
    String options[] = {  };
    String questionText = "";
    private int currentRadioButtonSelection = -1;
    private String otherText = "";
    private String patientID = null;
    private String[] dosageFeedbackIDs = null;
    private String[] doseageNames = null;

    //FOR EASY CONFIG
    Class buttonDestination = QuizPage.class;
    Class homeClass = MainActivity.class;
    int numQuestions = 3;

    //PASS IN THESE FIELDS IN ORDER TO DYNAMICALLY CONFIGURE THE QUIZ PAGE
    String buttonString = "Next";
    int questionInt = 0;
    String medName = "medication";

    //QUESTIONS AND ANSWERS
    String quiz1q = "Did you sleep through the night last night?\n";
    String quiz1a[] = { "Yes", "Woke once", "Woke a few times", "Slept very badly", "Other" };

    String quiz2q = "Are you feeling in control of your recovery?\n";
    String quiz2a[] = { "Yes", "I don't always know what I should be doing", "I need more guidance", "Other" };

    String quiz3q = "How would you describe your overall motivation?\n";
    String quiz3a[] = { "Great", "A bit lacking", "It's a struggle to motivate myself", "Other" };

    String quizMedsQ = "Why didn't you take your " + medName + " today?\n";
    String quizMedsA[] = { "It makes me feel sick", "I'm feeling better", "I've run out", "It's too expensive", "Other" };

    private void selectQuestions(int intSelect){
        switch ( intSelect ) {
            case 0:
                medName = doseageNames[0];
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
            patientID = extras.getString("patientID");
            dosageFeedbackIDs = extras.getStringArray("dosageFeedbackIDs");
            doseageNames = extras.getStringArray("doseageNames");
        }

        selectQuestions(questionInt);

        final LinearLayout rLayout = (LinearLayout) findViewById(R.id.quizView2);

        final TextView question =(TextView) findViewById(R.id.questionMessage);
        question.setText(questionText);

        final RadioButton[] rb = new RadioButton[options.length];
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupQuiz);

        rg.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < options.length; i++) {
            rb[i] = new RadioButton(this);
            rb[i].setTextSize(22);
            rg.addView(rb[i]);
            rb[i].setText(options[i]);
        }

        final Button nextButton = (Button) findViewById(R.id.quizNextButton);
        nextButton.setText(buttonString);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QuizPage.this);
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                EditText otherField = (EditText) findViewById(R.id.editTextOther);
                otherText = otherField.getText().toString();
                String publishString = "You chose option number " + currentRadioButtonSelection;

                if (currentRadioButtonSelection == options.length) publishString += " " + otherText;

                //alert.setTitle(publishString);
                //alert.setMessage("We will need to persist this and send it to the server");
                //alert.setIcon(android.R.drawable.ic_dialog_alert);
                //alert.show();

                Intent myIntent = null;

                if (questionInt != 0) {
                    ++questionInt;
                }

                //DO API POST CALL WITH DATA!!!!

                //if it is a 'treatment' question set
                if (questionInt == 0) {
                    if (dosageFeedbackIDs != null) {
                        if (dosageFeedbackIDs.length > 1) {

                            //take off the first of the IDs
                            dosageFeedbackIDs = Arrays.copyOfRange(dosageFeedbackIDs, 1, dosageFeedbackIDs.length);
                            doseageNames  = Arrays.copyOfRange(doseageNames, 1, doseageNames.length);

                            myIntent = new Intent(QuizPage.this, QuizPage.class);
                            myIntent.putExtra("questionNum", questionInt);
                            myIntent.putExtra("dosageFeedbackIDs", dosageFeedbackIDs);
                            myIntent.putExtra("doseageNames",doseageNames);
                            myIntent.putExtra("patientID", patientID);
                        } else {
                            myIntent = new Intent(QuizPage.this, homeClass);
                        }
                    }
                } else if (questionInt <= numQuestions) {
                    myIntent = new Intent(QuizPage.this, buttonDestination);
                    myIntent.putExtra("questionNum", questionInt);
                } else {
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
}
