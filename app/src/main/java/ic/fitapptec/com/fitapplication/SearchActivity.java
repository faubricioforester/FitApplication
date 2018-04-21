package ic.fitapptec.com.fitapplication;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final View linearLayout = findViewById(R.id.linearLayoutVerticalSearch);

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

                                            final String id = arr.getJSONObject(i).getString("id");
                                            final String nombre = arr.getJSONObject(i).getString("nombre");
                                            final String biografia = arr.getJSONObject(i).getString("biografia");
                                            final String correo = arr.getJSONObject(i).getString("correo");
                                            final String telefono = arr.getJSONObject(i).getString("telefono");


                                            TextView searchResult = new TextView(SearchActivity.this);
                                            String displayString = nombre + System.getProperty("line.separator") + biografia + System.getProperty("line.separator") + correo + System.getProperty("line.separator")
                                                    + telefono + System.getProperty("line.separator");
                                            searchResult.setText(displayString);
                                            searchResult.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            searchResult.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(SearchActivity.this, ProfileActivity.class  );
                                                    intent.putExtra("PROFILE_TYPE", "USER");
                                                    intent.putExtra("PROFILE_OWNER_ID", id);
                                                    intent.putExtra("USER_NAME", nombre);
                                                    intent.putExtra("USER_BIO", biografia);
                                                    intent.putExtra("USER_EMAIL", correo);
                                                    intent.putExtra("USER_PHONE", telefono);
                                                    SearchActivity.this.startActivity(intent);

                                                }
                                            });

                                            ((LinearLayout) linearLayout).addView(searchResult);

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
