package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static ic.fitapptec.com.fitapplication.MainActivity.PREFS_NAME;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String profileType = getIntent().getStringExtra("PROFILE_TYPE");
        String profileOwnerID = getIntent().getStringExtra("PROFILE_OWNER_ID");

        Button askTrainingButton = (Button)findViewById(R.id.buttonProfileAskTraining);

        TextView tvNameEdit = (TextView)findViewById(R.id.textViewNameProfileEdit);
        TextView tvBioEdit = (TextView)findViewById(R.id.textViewBioProfileEdit);
        TextView tvEmailEdit = (TextView)findViewById(R.id.textViewEmailProfileEdit);
        TextView tvPhoneEdit = (TextView)findViewById(R.id.textViewPhoneNumberEdit);

        tvNameEdit.setText(getIntent().getStringExtra("USER_NAME"));
        tvBioEdit.setText(getIntent().getStringExtra("USER_BIO"));
        tvEmailEdit.setText(getIntent().getStringExtra("USER_EMAIL"));
        tvPhoneEdit.setText(getIntent().getStringExtra("USER_PHONE"));

        if(profileType.equals("TRAINER")){
            askTrainingButton.setVisibility(Button.VISIBLE);
        } else{
            askTrainingButton.setVisibility(Button.INVISIBLE);
        }

        askTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                //this is the url where you want to send the request
                //TODO Change to the right API request
                String url = "https://binarycoffee.net/fitApp/api/loginCliente.php";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try{
                                    JSONObject respReq = new JSONObject(response);
                                    String message = respReq.getString("message");

                                    if(message.equals("OK")){

                                        //TODO avisar al usuario el envio de la solicitud


                                    } else{

                                    }
                                }catch (JSONException jsone){
                                    //Show error in login in.
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error On Login","That didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //TODO poner los parametros correctos
                        //params.put("username", etUsername.getText().toString());
                        //params.put("pass", etPass.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        //Fin de listener del boton



    }
}
