package patienthub.binary.com.patienthub;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizPage extends ActionBarActivity {

    //PASS IN THESE FIELDS IN ORDER TO DYNAMICALLY CONFIGURE THE QUIZ PAGE
    String options[] = { "Great", "OK", "Shitty", "Really shitty", "Really really shitty" };
    String questionText = "Question message \"how are you feeling\" etc";
    String buttonString = "Next";
    Class buttonDestination = QuizPage.class;

    private int currentRadioButtonSelection = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        LinearLayout rLayout = (LinearLayout) findViewById(R.id.quizView2);

        TextView question =(TextView) findViewById(R.id.questionMessage);
        question.setText(questionText);

        final RadioButton[] rb = new RadioButton[options.length];
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupQuiz);

        rg.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < 5; i++) {
            rb[i] = new RadioButton(this);
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
                alert.setTitle("You chose option number " + currentRadioButtonSelection);
                alert.setMessage("We will need to persist this and send it to the server");
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();

                //Intent myIntent = new Intent(QuizPage.this, buttonDestination);
                //QuizPage.this.startActivity(myIntent);
            }

        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {

                currentRadioButtonSelection = rg.getCheckedRadioButtonId();

                if (currentRadioButtonSelection != -1) {
                    nextButton.setEnabled(true);
                }
            }
        });



    }
}
