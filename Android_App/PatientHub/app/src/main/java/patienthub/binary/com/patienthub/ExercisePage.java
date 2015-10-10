package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ExercisePage extends Activity {

    private String q1Text = "What exercise did you do today?";
    private String q2Text = "How long do you do it for?";
    private String[] exercises = null;
    private String[] times = null;
    private boolean exerciseSelected = false;
    private boolean durationSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exercises = extras.getStringArray("exercises");
            times = extras.getStringArray("times");
        }

        final TextView question1 =(TextView) findViewById(R.id.exerciseQ1);
        question1.setText(q1Text);

        final TextView question2 =(TextView) findViewById(R.id.exerciseQ2);
        question2.setText(q2Text);

        final Spinner q1Spinner =(Spinner) findViewById(R.id.exerciseSpinner1);

        List<String> exerciseList = Arrays.asList(exercises);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        q1Spinner.setAdapter(dataAdapter);

        final Spinner q2Spinner =(Spinner) findViewById(R.id.exerciseSpinner2);

        List<String> timesList = Arrays.asList(times);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timesList);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_layout);
        q2Spinner.setAdapter(dataAdapter2);


        final Button nextButton = (Button) findViewById(R.id.exerciseButton);

        nextButton.setText("Finish");
        nextButton.setEnabled(false);

        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String q1Answer = q1Spinner.getSelectedItem().toString();
                String q2Answer = q2Spinner.getSelectedItem().toString();




                Intent myIntent = new Intent(ExercisePage.this, MainActivity.class);
                ExercisePage.this.startActivity(myIntent);
                finish();
            }
        });

        q1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2 == 0) exerciseSelected = false;
                else exerciseSelected = true;

                if (exerciseSelected && durationSelected) nextButton.setEnabled(true);
                else nextButton.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        q2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if(arg2 == 0) durationSelected = false;
                else durationSelected = true;

                if(exerciseSelected && durationSelected) nextButton.setEnabled(true);
                else nextButton.setEnabled(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }

}
