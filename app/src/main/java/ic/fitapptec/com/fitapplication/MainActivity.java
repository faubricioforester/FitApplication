package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bRegister = (Button)findViewById(R.id.buttonLogIn);
        final EditText etUsername = (EditText)findViewById(R.id.editTextUsername);
        final EditText etPass = (EditText)findViewById(R.id.editTextPass);
        String username = etUsername.getText().toString();
        String pass = etPass.getText().toString();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //this is the url where you want to send the request

                String url = "https://binarycoffee.net/fitApp/api/loginCliente.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.e("Response of LoginJSON",response);

                                //Analize JSON. Correctness.
                                try{
                                    JSONObject respReq = new JSONObject(response);
                                    String message = respReq.getString("message");

                                    if(message == "OK"){

                                    } else{
                                        //If the LogIn is correct.
                                        //Save log info to sharedPreferences
                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("currentUser", etUsername.getText().toString());
                                        editor.apply();
                                        //Move to next activity
                                        Intent intent = new Intent(MainActivity.this, MenuActivity.class  );
                                        MainActivity.this.startActivity(intent);

                                    }

                                }catch (JSONException jsone){
                                    //Show error in login in.

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error On LoginJSON","That didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", etUsername.getText().toString());
                        params.put("pass", etPass.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }


    public void onRegisterButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, MenuActivity.class  );
        MainActivity.this.startActivity(intent);

    }


}
