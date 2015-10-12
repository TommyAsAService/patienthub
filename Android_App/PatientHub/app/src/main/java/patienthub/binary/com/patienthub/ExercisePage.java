package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.webservice.HttpManager;

public class ExercisePage extends Activity {

    private String q1Text = "\nWhat exercise did you do today?";
    private String q2Text = "\nHow long did you exercise for?";
    private String[] exercises = null;
    private String[] times = null;
    private boolean exerciseSelected = false;
    private boolean durationSelected = false;
    private int dosageID = -1;
    private String token = "";
    List<Dosage> dosageList = new ArrayList<>();
    public static final String[] timesArray = {"Choose Duration...","5 minutes", "10 minutes",
            "30 minutes","60 minutes", "120 minutes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);


        List<String> timesList = Arrays.asList(timesArray);

        String json = "";
        try {
            json = readFromFile(QR_Code.DOSAGES_FILENAME);
            token = readFromFile("token.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> exerciseList = new ArrayList<>();
        exerciseList.add("Choose Exercise...");
        try {
            Dosage[] dosages = new ObjectMapper().readValue(json, Dosage[].class);
            for(Dosage dose : dosages){
                if(dose.getTreatment().getTreatment_type().equals("Exercise")) {
                    dosageList.add(dose);
                    exerciseList.add(dose.getTreatment_name());
                    //timesList.add(String.valueOf(dose.getQuantity()) + " "+dose.getUnit());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final TextView question1 =(TextView) findViewById(R.id.exerciseQ1);
        question1.setText(q1Text);

        final TextView question2 =(TextView) findViewById(R.id.exerciseQ2);
        question2.setText(q2Text);

        final Spinner q1Spinner =(Spinner) findViewById(R.id.exerciseSpinner1);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        q1Spinner.setAdapter(dataAdapter);

        final Spinner q2Spinner =(Spinner) findViewById(R.id.exerciseSpinner2);


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timesList);
        dataAdapter2.setDropDownViewResource(R.layout.spinner_layout);
        q2Spinner.setAdapter(dataAdapter2);


        dosageID = dosageList.get(q1Spinner.getSelectedItemPosition()).getId();
        System.out.println("THE ID IS: "+dosageID);

        final Button submitButton = (Button) findViewById(R.id.exercise_button);

        submitButton.setEnabled(false);

        submitButton.setText("Finish");
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String q1Answer = q1Spinner.getSelectedItem().toString();//POST THIS
                String q2Answer = q2Spinner.getSelectedItem().toString();//POST THIS

                dosageID = dosageList.get(q1Spinner.getSelectedItemPosition()-1).getId();
                System.out.println("THE ID IS: "+dosageID);

                HttpManager POSTer = new HttpManager();

                try {
                    System.out.println(POSTer.postMedicationData(q1Answer + q2Answer, token, dosageID,true));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                Intent myIntent = new Intent(ExercisePage.this, MainMenu.class);
                myIntent.putExtra("exerciseCompleted",true);
                ExercisePage.this.startActivity(myIntent);
               finish();
            }
        });

        Button backButton = (Button)findViewById(R.id.exercise_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExercisePage.this,MainMenu.class);
                startActivity(i);
                finish();
            }
        });

        q1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {




                if (arg2 == 0) {

                    exerciseSelected = false;
                } else {
                    dosageID = dosageList.get(arg2-1).getId();
                    System.out.println("THE ID IS: " + dosageID);
                    exerciseSelected = true;
                }

                if(exerciseSelected && durationSelected){
                    submitButton.setEnabled(true);
                }else{
                    submitButton.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        q2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2 == 0) {
                    durationSelected = false;
                }else {
                    durationSelected = true;
                }

                if(exerciseSelected && durationSelected){
                    submitButton.setEnabled(true);
                }else{
                    submitButton.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private String readFromFile(String filename) throws IOException{

        String ret = "";

        try {
            InputStream inputStream = openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
