package patienthub.binary.com.patienthub;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import patienthub.binary.com.patienthub.data.Dosage;

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
            setupExerciseButton(dosages);
        }

    }


    public void setupExerciseButton(Dosage[] dosages){
        StringBuilder builder = new StringBuilder();
        int numOfExercises = 0;
        for(int i=0;i<dosages.length;i++){
            //THIS SHOULD BE AN ENUM
            Dosage dos = dosages[i];
            Log.d("treatment type",dos.getTreatment_name());
            if(dos.getTreatment().getTreatment_type().equals("Exercise")){
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
        text.setText(builder.substring(0,builder.length()-2)+"...");
    }

}
