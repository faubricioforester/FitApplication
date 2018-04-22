package ic.fitapptec.com.fitapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static ic.fitapptec.com.fitapplication.MainActivity.PREFS_NAME;

public class MyTrainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainer);

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);

    }
}
