package patienthub.binary.com.patienthub;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences prefs = getSharedPreferences("prefsFile", MODE_PRIVATE);
        String restoredText = prefs.getString("jsonFile", null);
        if (restoredText != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.setTitle("User already logged in");
            alert.setMessage("This will redirect to the list view screen");
            alert.setIcon(android.R.drawable.ic_dialog_alert);
            alert.show();
        }
        final ImageView button = (ImageView) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, QR_Code.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        Button resetButton = (Button)findViewById(R.id.resetButton);
        String filePath = MainActivity.this.getFilesDir()+ File.separator+QR_Code.DOSAGES_FILENAME;

        if(!(new File(filePath).exists())) {
            resetButton.setVisibility(View.GONE);
        }else{
            resetButton.setVisibility(View.VISIBLE);
        }


        resetButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String filePath = MainActivity.this.getFilesDir()+ File.separator+QR_Code.DOSAGES_FILENAME;
                String tokenFile = MainActivity.this.getFilesDir()+ File.separator+"token.txt";
                String completedFile = MainActivity.this.getFilesDir()+ File.separator+MainMenu.completedFileName;

                if((new File(filePath).exists())) {
                    File file = new File(filePath);
                    file.delete();

                }

                if((new File(tokenFile).exists())) {
                    File file = new File(tokenFile);
                    file.delete();
                }

                if((new File(completedFile).exists())) {
                    File file = new File(completedFile);
                    file.delete();
                }

                Toast.makeText(MainActivity.this, "Reset Patient Data", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
