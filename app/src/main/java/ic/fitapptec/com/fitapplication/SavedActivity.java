package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class SavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(SavedActivity.this);
        //this is the url where you want to send the request
        String url = "https://binarycoffee.net/fitApp/api/getPublicacionesGuardadas.ph";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.e("SavedPostRequest",response);

                        //Analize JSON. Correctness.
                        try{
                            JSONObject respReq = new JSONObject(response);
                            String message = respReq.getString("message");

                            if(message.equals("OK")){
                                Log.e("OK","Saved post request correct");
                                //Display text results is correct.

                                JSONArray arr = respReq.getJSONArray("listaDatos");
                                if (arr.length() <= 0){
                                    for(int i = 0; i < arr.length(); i++){

                                        final String id = arr.getJSONObject(i).getString("idPublicacion");
                                        final String texto = arr.getJSONObject(i).getString("textoPublicacion");
                                        final String fecha = arr.getJSONObject(i).getString("fechaPublicacion");
                                        final String user = arr.getJSONObject(i).getString("userPublicacion");



                                        TextView searchResult = new TextView(SavedActivity.this);
                                        String displayString = texto + System.getProperty("line.separator")
                                                + fecha + System.getProperty("line.separator")
                                                + user + System.getProperty("line.separator");
                                        searchResult.setText(displayString);
                                        searchResult.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        LinearLayout linearLayout = findViewById(R.id.linearLayoutSavedPost);
                                        ((LinearLayout) linearLayout).addView(searchResult);

                                    }
                                } else {
                                    TextView searchResult = new TextView(SavedActivity.this);
                                    String displayString = "No tienes publicaciones guardadas";
                                    searchResult.setText(displayString);
                                    searchResult.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                    LinearLayout linearLayout = findViewById(R.id.linearLayoutSavedPost);
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
                SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
                params.put("idUser", pref.getString("id",""));


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
