package patienthub.binary.com.patienthub;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.data.TreatmentType;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String json = getIntent().getStringExtra("json");
        ObjectMapper mapper = new ObjectMapper();

        Dosage[] dosages = null;

        try {
            dosages = mapper.readValue(json, Dosage[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(dosages!=null){
            setupMenuItem(dosages,TreatmentType.Exercise,R.id.exercise_snippets);
            setupMenuItem(dosages, TreatmentType.Medication, R.id.medication_snippets);
        }

    }


    public void setupExerciseButton(Dosage[] dosages){
        StringBuilder builder = new StringBuilder();
        int numOfExercises = 0;
        for(int i=0;i<dosages.length;i++){
            Dosage dos = dosages[i];
            Log.d("treatment type",dos.getTreatment_name());
            if(dos.getTreatment().getTreatment_type().equals(TreatmentType.Exercise.name())){
                numOfExercises++;
                builder.append(dos.getTreatment_name()+", ");
                if(numOfExercises == 3){
                    break;
                }
            }
        }

        Log.d("builderString",builder.toString());
        TextView text = (TextView) findViewById(R.id.exercise_snippets);
        // -2 to trim last comma
        if(builder.length() > 2){
            text.setText(builder.substring(0,builder.length()-2)+"...");
        }else{
            text.setText("");
        }

    }

    public void setupMenuItem(Dosage[] dosages, TreatmentType treatmentType, int snippetsId){
        StringBuilder builder = new StringBuilder();
        int num = 0;
        for(int i=0;i<dosages.length;i++){
            Dosage dos = dosages[i];
            Log.d("treatment type",dos.getTreatment_name());
            if(dos.getTreatment().getTreatment_type().equals(treatmentType.name())){
                num++;
                builder.append(dos.getTreatment_name()+", ");
                if(num == 3){
                    break;
                }
            }
        }

        Log.d("builderString",builder.toString());
        TextView text = (TextView) findViewById(snippetsId);
        // -2 to trim last comma
        if(builder.length() > 2){
            text.setText(builder.substring(0,builder.length()-2)+"...");
        }else{
            text.setText("");
        }

    }

}
