package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.data.Treatment;
import patienthub.binary.com.patienthub.data.TreatmentType;

public class MainMenu extends Activity {

    int numberOfTasks= 0;
    private String filePath;
    public static final String completedFileName = "completed.txt";
    private List<String> completedList = new ArrayList<>();
    LinearLayout morningMedsLayout;
    LinearLayout afternoonMedsLayout;
    LinearLayout eveningMedsLayout;
    LinearLayout exerciseLinearLayout;
    LinearLayout quizItem;

    int morningMeds = 0;
    int afternoonMeds =0;
    int eveningMeds = 0;
    int exercises = 0;

    LinearLayout greenTickLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        filePath =  MainMenu.this.getFilesDir()+File.separator+"completed.txt";

        String json = getIntent().getStringExtra("json");

        if(json == null){
            try {
                json = readFromFile(QR_Code.DOSAGES_FILENAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectMapper mapper = new ObjectMapper();

        morningMedsLayout = (LinearLayout)findViewById(R.id.morning_med_item);
        afternoonMedsLayout = (LinearLayout)findViewById(R.id.afternoon_med_item);
        eveningMedsLayout = (LinearLayout)findViewById(R.id.evening_med_item);
        exerciseLinearLayout = (LinearLayout)findViewById(R.id.exercise_manu_item);
        quizItem = (LinearLayout) findViewById(R.id.quizItem);
        greenTickLayout = (LinearLayout) findViewById(R.id.completedLayout);

//        greenTickLayout.setVisibility(View.GONE);


        File file = new File(filePath);
        if(getIntent().hasExtra("timeMedicationTaken")){
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String str = date+","+getIntent().getStringExtra("timeMedicationTaken")+";";
            if(!(file.exists())){
                writeToNewFile(completedFileName,str);
            }else{
                appendToFile(completedFileName,str);
            }
        }

        if(getIntent().hasExtra("quizTaken")){
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String str = date+",quiz"+";";
            if(!(file.exists())){
                writeToNewFile(completedFileName,str);
            }else{
                appendToFile(completedFileName,str);
            }
        }

        if(getIntent().hasExtra("exerciseCompleted")){
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String str = date+",exercise"+";";
            if(!(file.exists())){
                writeToNewFile(completedFileName,str);
            }else{
                appendToFile(completedFileName,str);
            }
        }

        if(file.exists()){
            try {
                String completedString = readFromFile(completedFileName);
                Log.d("CompletedString",completedString);
                populateTodaysCompletedTasks(completedString);

                for(String task: completedList){
                    if(task.equals("Morning")){
                        morningMedsLayout.setVisibility(View.GONE);
                    }else if(task.equals("Afternoon")){
                        afternoonMedsLayout.setVisibility(View.GONE);
                    }else if(task.equals("Evening")){
                        eveningMedsLayout.setVisibility(View.GONE);
                    }else if(task.equals("quiz")){
                        quizItem.setVisibility(View.GONE);
                    }else if(task.equals("exercise")){
                        exerciseLinearLayout.setVisibility(View.GONE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        morningMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Morning");
                startActivity(i);
                finish();
            }
        });


        afternoonMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Afternoon");
                startActivity(i);
                finish();
            }
        });

        eveningMedsLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,Medication_Screen.class);
                i.putExtra("timeOfDay","Evening");
                startActivity(i);
                finish();
            }
        });

        exerciseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,ExercisePage.class);
                startActivity(i);
                finish();
            }
        });



        Dosage[] dosages = null;


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

                }else if(dosage.getTreatment().getTreatment_type().equals(TreatmentType.Exercise.name()) && dosage.isScheduledToday()){
                    exercises++;
                }
            }

            if(morningMeds == 0){
                morningMedsLayout.setVisibility(View.GONE);
            }
            if(afternoonMeds == 0){
                afternoonMedsLayout.setVisibility(View.GONE);
            }
            if(eveningMeds == 0){
                eveningMedsLayout.setVisibility(View.GONE);
            }
            if(exercises == 0){
                exerciseLinearLayout.setVisibility(View.GONE);
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
                finish();
            }
        });


        if(areAllGone()){
            System.out.println("GONE!");
            greenTickLayout = (LinearLayout) findViewById(R.id.completedLayout);
            greenTickLayout.setVisibility(View.VISIBLE);

            System.out.println("I AM ACTUALLY "+greenTickLayout.getVisibility());
        }else{
            System.out.println("NOT GONE!");
            greenTickLayout.setVisibility(View.GONE);
        }


    }

    private boolean areAllGone() {

        return ((exerciseLinearLayout.getVisibility() == View.GONE)&&
                (morningMedsLayout.getVisibility() == View.GONE)&&
                (afternoonMedsLayout.getVisibility() == View.GONE)&&
                (eveningMedsLayout.getVisibility() == View.GONE)&&
                (quizItem.getVisibility() == View.GONE));
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
                        if(morningMeds == 1){
                            builder.append(dos.getTreatment_name());
                        }else{
                            builder.append(dos.getTreatment_name()+", ");
                        }

                        if(num == 3){
                            break;
                        }
                    }
                }else if(snippetsId == R.id.afternoon_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Afternoon") && dos.isScheduledToday()){
                        num++;
                        if(afternoonMeds == 1){
                            builder.append(dos.getTreatment_name());
                        }else{
                            builder.append(dos.getTreatment_name()+", ");
                        }
                        if(num == 3){
                            break;
                        }
                    }
                } else if (snippetsId == R.id.evening_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Evening") && dos.isScheduledToday()){
                        num++;
                        if(morningMeds == 1){
                            builder.append(dos.getTreatment_name());
                        }else{
                            builder.append(dos.getTreatment_name()+", ");
                        }
                        if(num == 3){
                            break;
                        }
                    }
                } else{

                    addDotDotDot = false;
                    System.out.println("I IS "+i+" length is "+dosages.length);
                    if(exercises == 1){
                        builder.append(dos.getTreatment_name());
                    }else if(i == dosages.length-1){
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
                if(builder.toString().contains(",")){
                    if (addDotDotDot) {

                        text.setText(builder.substring(0, builder.length() - 2) + "...");
                    } else {
                        String trimmed = builder.substring(0, builder.length() - 2);

                        if(trimmed.contains(",")){
                            int lastComma = trimmed.lastIndexOf(",");
                            String part1 = trimmed.substring(0,lastComma);
                            String part2 = trimmed.substring(lastComma+1,trimmed.length());
                            text.setText(part1+" or"+part2);
                        }else{
                            text.setText(trimmed);
                        }

                    }
                }else{
                    text.setText(builder.toString());
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

    private void writeToNewFile(String fileName, String data) {
        File file = new File(MainMenu.this.getFilesDir(),fileName);
        FileOutputStream fop;

        try{
            fop = openFileOutput(fileName, Context.MODE_PRIVATE);
            fop.write(data.getBytes());
            fop.close();
            Log.d("path",file.getPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void appendToFile (String fileName,String content) {
        try {
            FileOutputStream fOut = openFileOutput(fileName,MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(content);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        BufferedWriter bw = null;
//
//        try {
//            // APPEND MODE SET HERE
//            bw = new BufferedWriter(new FileWriter(fileName, true));
//            bw.write(content);
//            bw.flush();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } finally {                       // always close the file
//            if (bw != null) try {
//                bw.close();
//            } catch (IOException ioe2) {
//                // just ignore it
//            }
//        } // end try/catch/finally

    } // end test()

    public void populateTodaysCompletedTasks(String fileString){
        String[] allTasks = fileString.split(";");

        for(int i = 0; i<allTasks.length;i++){
            String currentTask = allTasks[i];
            String[] row = currentTask.split(",");

            if(row.length==2){
                String date = row[0];
                String task = row[1];

                String todaysDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                if(date.equals(todaysDate)){
                    completedList.add(task);
                }
            }
        }
    }

}
