package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import patienthub.binary.com.patienthub.data.Dosage;
import patienthub.binary.com.patienthub.webservice.HttpManager;


public class QR_Code extends Activity {

    public final static String GET_MEDICATIONS_URL = "http://patienthub.herokuapp.com/api/v1/patient/dosages";
    public final static String DOSAGES_FILENAME = "dosages.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__code);
        String filePath = QR_Code.this.getFilesDir()+File.separator+DOSAGES_FILENAME;

        if(!(new File(filePath).exists())){
            performScan();
        }else{
            try {
                String json = readFromFile(DOSAGES_FILENAME);
                Intent i = new Intent(this,MainMenu.class);
                i.putExtra("json",json);

                startActivity(i);
                finish();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();

            String tokenFilePath = "token.txt";

            writeToNewFile(tokenFilePath,contents);

            System.out.println("THE FILE WRITE PATH " + tokenFilePath);

            if (contents != null) {

                Toast.makeText(this,"SUCCESS",Toast.LENGTH_LONG).show();
                Log.d("SUCCESS", result.toString());
                AsyncTask getRequest = new PerformGetRequest();
                getRequest.execute(new String[]{GET_MEDICATIONS_URL, contents});
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
            String getData = HttpManager.getData(uri,token);
            return getData;
        }

        protected void onPostExecute(String result) {

            writeToNewFile(DOSAGES_FILENAME,result);

            Intent i = new Intent(QR_Code.this, MainMenu.class);
            i.putExtra("json",result);
            startActivity(i);

        }
    }


    private void writeToNewFile(String fileName, String data) {
        File file = new File(QR_Code.this.getFilesDir(),fileName);
        FileOutputStream fop;

        try{
            fop = openFileOutput(fileName,Context.MODE_PRIVATE);
            fop.write(data.getBytes());
            fop.close();
            Log.d("path",file.getPath());
        }catch (Exception e){
            e.printStackTrace();
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
