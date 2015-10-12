package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import patienthub.binary.com.patienthub.Scheduling.Scheduler;
import patienthub.binary.com.patienthub.adapters.MedicationListAdapter;
import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.data.TreatmentType;


public class Medication_Screen extends Activity {

    private static LayoutInflater inflater = null;

    private ListView listview;
    private MedicationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication__screen);
        listview = (ListView) findViewById(R.id.medicationListView);
        final List<Dosage> dosageList = new ArrayList<>();
        final ArrayList<Dosage> dosageNotTaken = new ArrayList<>();
        final ArrayList<Dosage> dosageTaken = new ArrayList<>();

        String timeOfDay = getIntent().getStringExtra("timeOfDay");

        String json = "";
        try {
            json = readFromFile(QR_Code.DOSAGES_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Dosage[] dosages = new ObjectMapper().readValue(json, Dosage[].class);
            for(Dosage dose : dosages){
                if(dose.getTreatment().getTreatment_type().equals("Medication") && dose.isScheduledToday() && timeOfDay.equals(dose.getTime_taken())) {
                    dosageList.add(dose);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter  = new MedicationListAdapter(this,dosageList);

        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footer = inflater.inflate(R.layout.medication_listview_footer, null);
        listview.addFooterView(footer);
        listview.setAdapter(adapter);

        Button submitButton= (Button) findViewById(R.id.submit_medication_taken_button);
        submitButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dosageTaken.clear();
                dosageNotTaken.clear();
                StringBuilder result = new StringBuilder();
                for(int i=0;i<adapter.mCheckStates.size();i++)
                {
                    if(adapter.mCheckStates.get(i)==true)
                    {
                        dosageTaken.add(dosageList.get(i));
                    } else {
                        dosageNotTaken.add(dosageList.get(i));
                    }
                }
                result.append("TAKEN: ");
                for(Dosage d : dosageTaken){
                    result.append(d.getTreatment_name() + " ");
                }
                result.append("\n"+"NOT TAKEN: ");
                for(Dosage d : dosageNotTaken){
                    result.append(d.getTreatment_name() + " ");
                }
                Toast.makeText(Medication_Screen.this, result, Toast.LENGTH_LONG).show();
            }

        });

        Button backButton= (Button) findViewById(R.id.back_medication_taken_button);
        backButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medication__screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onCheckboxClicked(View v){

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
