package patienthub.binary.com.patienthub;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.data.Treatment;
import patienthub.binary.com.patienthub.data.TreatmentType;

public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String json = getIntent().getStringExtra("json");
        ObjectMapper mapper = new ObjectMapper();

        Dosage[] dosages = null;
        int morningMeds = 0;
        int afternoonMeds =0;
        int eveningMeds = 0;

        try {
            dosages = mapper.readValue(json, Dosage[].class);
            for(Dosage dosage: dosages){
                if(dosage.getTreatment().getTreatment_type().equals(TreatmentType.Medication.name())){
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

    }


    public void setupMenuItem(Dosage[] dosages, TreatmentType treatmentType, int snippetsId){
        StringBuilder builder = new StringBuilder();
        int num = 0;
        for(int i=0;i<dosages.length;i++){
            Dosage dos = dosages[i];

            if(dos.getTreatment().getTreatment_type().equals(treatmentType.name())){

                if(snippetsId == R.id.morning_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Morning")){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                }else if(snippetsId == R.id.afternoon_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Afternoon")){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                } else if (snippetsId == R.id.evening_med_snippet && treatmentType.equals(TreatmentType.Medication)){
                    if(dos.getTime_taken().equals("Evening")){
                        num++;
                        builder.append(dos.getTreatment_name()+", ");
                        if(num == 3){
                            break;
                        }
                    }
                } else{
                    num++;
                    builder.append(dos.getTreatment_name()+", ");
                    if(num == 3){
                        break;
                    }
                }

            }
        }

            TextView text = (TextView) findViewById(snippetsId);
            // -2 to trim last comma
            if(builder.length() > 2){
                text.setText(builder.substring(0,builder.length()-2)+"...");
            }else{
                text.setText("");
            }

    }

}
