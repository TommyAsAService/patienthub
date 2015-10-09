package patienthub.binary.com.patienthub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefsFile", MODE_PRIVATE);
        String restoredText = prefs.getString("jsonFile", null);
        if (restoredText != null) {

            //@@@@@@@@@@@
            // INSERT REDIRECT HERE TO MAIN 'LIST VIEW' SCREEN
            //@@@@@@@@@@@

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.setTitle("User already logged in");
            alert.setMessage("This will redirect to the list view screen");
            alert.setIcon(android.R.drawable.ic_dialog_alert);
            alert.show();
        }
        final Button button = (Button) findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Intent myIntent = new Intent(MainActivity.this, QR_Code.class);
                Intent myIntent = new Intent(MainActivity.this, QuizPage.class);

                //NOTE: use '0' to do the single medication question
                //NOTE: using '1' to start the feeling quiz
                myIntent.putExtra("questionNum",0);

                //NOTE: must include the patient ID if doing the feeling quiz
                String patientID = "1245";
                myIntent.putExtra("patientID",patientID);

                //NOTE: insert a string array of the dosage IDs of the dosage to provide feedback for
                String[] dosageFeedbackIDs = {"111","222","333"};
                myIntent.putExtra("dosageFeedbackIDs",dosageFeedbackIDs);

                //NOTE: insert a string array of the dosage names of the dosage to provide feedback for (matching above)
                String[] dosageNames = {"Aspirin","Warfarin","Ibuprofen"};
                myIntent.putExtra("dosageNames",dosageNames);

                MainActivity.this.startActivity(myIntent);
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
