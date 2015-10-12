package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.data.Treatment;
import patienthub.binary.com.patienthub.data.TreatmentType;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        String json = getIntent().getStringExtra("json");
        ObjectMapper mapper = new ObjectMapper();

        LinearLayout morningMedsLayout = (LinearLayout)findViewById(R.id.morning_med_item);
        LinearLayout afternoonMedsLayout = (LinearLayout)findViewById(R.id.afternoon_med_item);
        LinearLayout eveningMedsLayout = (LinearLayout)findViewById(R.id.evening_med_item);
        LinearLayout exerciseLinearLayout = (LinearLayout)findViewById(R.id.exercise_manu_item);

        morningMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Morning");
                startActivity(i);
            }
        });


        afternoonMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Afternoon");
                startActivity(i);
            }
        });

        eveningMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Evening");
                startActivity(i);
            }
        });

        exerciseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,ExercisePage.class);
                startActivity(i);
            }
        });



        Dosage[] dosages = null;
        int morningMeds = 0;
        int afternoonMeds =0;
        int eveningMeds = 0;

        try {
            dosages = mapper.readValue(json, Dosage[].class);
            for(Dosage dosage: dosages){
                if(dosage.getTreatment().getTreatment_type().equals(TreatmentType.Medication.name()) && dosage.isScheduledToday()){
                    Log.d("Time Taken",dosage.getTime_taken());

                    if(dosage.getTime_taken().equals("Morning")){
                        morningMeds++;
                    }else if(dosage.getTime_taken().equals("Afternoon")){
                        afternoonMeds++;
                    }else if(dosage.getTime_taken().equals("Evening")){
                        eveningMeds++;
                    }

                }
            }

            if(morningMeds == 0){
                findViewById(R.id.morning_med_item).setVisibility(View.GONE);
            }
            if(afternoonMeds == 0){
                findViewById(R.id.afternoon_med_item).setVisibility(View.GONE);
            }
            if(eveningMeds == 0){
                findViewById(R.id.evening_med_item).setVisibility(View.GONE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(dosages!=null){
            setupMenuItem(dosages,TreatmentType.Exercise, R.id.exercise_snippet);
            setupMenuItem(dosages, TreatmentType.Medication, R.id.morning_med_snippet);
            setupMenuItem(dosages, TreatmentType.Medication, R.id.afternoon_med_snippet);
            setupMenuItem(dosages, TreatmentType.Medication, R.id.evening_med_snippet);
        }

        LinearLayout quizItem = (LinearLayout) findViewById(R.id.quizItem);
        quizItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(MainMenu.this, QuizPage.class);
                try {
                    myIntent.putExtra("token", readFromFile("token.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myIntent.putExtra("questionNum",1);
                myIntent.putExtra("numQuestions",3);

                startActivity(myIntent);
            }
        });




    }


    public void setupMenuItem(Dosage[] dosages, TreatmentType treatmentType, int snippetsId){
        StringBuilder builder = new StringBuilder();
        int num = 0;
        boolean addDotDotDot = true;
        for(int i=0;i<dosages.length;i++){
            Dosage dos = dosages[i];

            if(dos.getTreatment().getTreatment_type().equals(treatmentType.name())){

                if(snippetsId == R.id.morning_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Morning") && dos.isScheduledToday()){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                }else if(snippetsId == R.id.afternoon_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Afternoon") && dos.isScheduledToday()){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                } else if (snippetsId == R.id.evening_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Evening") && dos.isScheduledToday()){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                } else{
                    addDotDotDot = false;
                    System.out.println("I IS "+i+" length is "+dosages.length);
                    if(i == dosages.length-1){
                        builder.append(dos.getTreatment_name()+" or ");
                    }else{
                        builder.append(dos.getTreatment_name()+", ");
                    }



                }

            }
        }

            TextView text = (TextView) findViewById(snippetsId);
            // -2 to trim last comma
            if(builder.length() > 2) {
                if (addDotDotDot) {
                    text.setText(builder.substring(0, builder.length() - 2) + "...");
                } else {
                    String trimmed = builder.substring(0, builder.length() - 2);

                    int lastComma = trimmed.lastIndexOf(",");
                    String part1 = trimmed.substring(0,lastComma);
                    String part2 = trimmed.substring(lastComma+1,trimmed.length());
                    text.setText(part1+" or"+part2);
                }
            }

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
