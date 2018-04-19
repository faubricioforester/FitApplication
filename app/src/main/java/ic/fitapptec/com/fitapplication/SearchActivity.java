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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static ic.fitapptec.com.fitapplication.MainActivity.PREFS_NAME;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        View linearLayout = findViewById(R.id.linearLayoutVerticalSearch);

        Button bSearchTerm = findViewById(R.id.buttonSearchTerm);
        final EditText etSearchTerm = (EditText)findViewById(R.id.editTextSearchTerm);

        bSearchTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                //this is the url where you want to send the request

                String url = "https://binarycoffee.net/fitApp/api/buscarUsuario.php";

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
                                        //Display text results is correct.

                                        JSONArray arr = respReq.getJSONArray("listaDatos");

                                        for(int i = 0; i < arr.length(); i++){

                                        }



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
                        params.put("query", etSearchTerm.getText().toString());


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
