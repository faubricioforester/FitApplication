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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static ic.fitapptec.com.fitapplication.MainActivity.PREFS_NAME;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etusername = (EditText) findViewById(R.id.editTextUsernameRegister);
        final EditText etpass = (EditText)findViewById(R.id.editTextPasswordRegister);
        final EditText etname = (EditText)findViewById(R.id.editTextNameRegister);
        final EditText etbio = (EditText)findViewById(R.id.editTextBioRegister);
        final EditText etemail = (EditText)findViewById(R.id.editTextEmailRegister);
        final EditText etphoneNumber = (EditText)findViewById(R.id.editTextPhonenumberRegister);

        String strUsername = etusername.getText().toString();
        String strPassword = etpass.getText().toString();
        String strName = etname.getText().toString();
        String strBio = etbio.getText().toString();
        String strEmail = etemail.getText().toString();
        String strPhoneNumber = etphoneNumber.getText().toString();

        Button bRegister = (Button)findViewById(R.id.buttonRegistro);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //this is the url where you want to send the request

                String url = "https://binarycoffee.net/fitApp/api/registrarCliente.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.e("ResponseRegisterRequest",response);

                                //Analize JSON. Correctness.
                                try{
                                    JSONObject respReq = new JSONObject(response);
                                    String message = respReq.getString("message");

                                    if(message.equals("OK")){
                                        Log.e("ReqReg","Register is ok");
                                        //If the LogIn is correct.
                                        //Save log info to sharedPreferences
                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("currentUser", etusername.getText().toString());
                                        editor.apply();
                                        //Move to next activity
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class  );
                                        RegisterActivity.this.startActivity(intent);

                                    } else{


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
                        params.put("username", etusername.getText().toString());
                        params.put("pass", etpass.getText().toString());
                        params.put("nombre", etname.getText().toString());
                        params.put("biografia", etbio.getText().toString());
                        params.put("correo", etemail.getText().toString());
                        params.put("telefono", etphoneNumber.getText().toString());

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });


    }

    public void onRegisterButtonClick(View view){


    }
}
