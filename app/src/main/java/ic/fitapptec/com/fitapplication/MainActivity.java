package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogInButtonClick(View v){
        EditText etUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText etPass = (EditText)findViewById(R.id.editTextPass);
        String username = etUsername.getText().toString();
        String pass = etPass.getText().toString();

        if(logIn(username, pass )){
            //Save log info to sharedPreferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("currentUser", username);
            editor.apply();


            //Move to next activity
            Intent intent = new Intent(MainActivity.this, MenuActivity.class  );
            MainActivity.this.startActivity(intent);
        } else {
            //Show login error

        }

    }

    public void onRegisterButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class  );
        MainActivity.this.startActivity(intent);

    }

    public boolean logIn(String username, String pass){
        //api/loginCliente.php
        try {
            String result = ConnectionHandler.login(username, pass);
            Log.e("JSONRESULT ", result);

        } catch (JSONException exc){

            Log.e("JSONException", exc.toString());

        }


        return true;

    }
}
