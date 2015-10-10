package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import patienthub.binary.com.patienthub.adapters.MedicationListAdapter;
import patienthub.binary.com.patienthub.data.Dosage;


public class Medication_Screen extends Activity {

    private ListView listview;
    private MedicationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication__screen);
        listview = (ListView) findViewById(R.id.medicationListView);
        List<String> dosageList = new ArrayList<>();

        String json = getIntent().getStringExtra("jsonDosages");


        try {
            Dosage[] dosages = new ObjectMapper().readValue(json, Dosage[].class);
            for(Dosage dose : dosages){
                if(dose.takeToday()) {
                    dosageList.add(dose.getTreatment_name());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter  = new MedicationListAdapter(this,dosageList);
        listview.setAdapter(adapter);
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
}
