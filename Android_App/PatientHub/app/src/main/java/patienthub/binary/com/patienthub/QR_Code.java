package patienthub.binary.com.patienthub;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.URI;

import patienthub.binary.com.patienthub.data.Medications;
import patienthub.binary.com.patienthub.webservice.HttpManager;


public class QR_Code extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code);
        performScan();





    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                Toast.makeText(this,"SUCCESS",Toast.LENGTH_LONG).show();
                Log.d("SUCCESS", result.toString());
                AsyncTask getRequest = new PerformGetRequest();

                AsyncTask test = getRequest.execute(new String[]{"http://patienthubstage.herokuapp.com/api/v1/patient/medications", contents});
            } else {
                Toast.makeText(this,"FAILED",Toast.LENGTH_LONG).show();
                Log.d("FAILED", result.toString());
            }
        }
    }

    public void performScan(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr__code, menu);
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

    private class PerformGetRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String uri = strings[0];
            String token = strings[1];
            String test = HttpManager.getData(uri,token);
            return test;
        }

        protected void onPostExecute(String result) {
            TextView view = (TextView)findViewById(R.id.qr_code);
            //Medications meds = new ObjectMapper().readValue(result, Medications.class);
            view.setText(result);

        }
    }
}