package patienthub.binary.com.patienthub;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizPage extends ActionBarActivity {

    String options[] = { "Great", "OK", "Shitty", "Really shitty", "Really really shitty" };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        LinearLayout rLayout = (LinearLayout) findViewById(R.id.quizView2);

        TextView question =(TextView) findViewById(R.id.questionMessage);
        question.setText("Question message \"how are you feeling\" etc");

        final RadioButton[] rb = new RadioButton[options.length];
        RadioGroup rg = new RadioGroup(this);

        rg.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < 5; i++) {
            rb[i] = new RadioButton(this);
            rg.addView(rb[i]);
            rb[i].setText(options[i]);

        }
        rLayout.addView(rg);
        }
}
