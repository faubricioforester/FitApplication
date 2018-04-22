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

public class CommunityActivity extends AppCompatActivity {
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        final View linearLayout = findViewById(R.id.linearLayoutUserCommunities);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(CommunityActivity.this);
        //this is the url where you want to send the request

        String url = "https://binarycoffee.net/fitApp/api/getComunidadesUsuario.php";

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

                            if(message.equals("OK")){

                                //Save log info to sharedPreferences
                                Log.e("Login successful", "OK");


                                //If the LogIn is correct.
                                //Save info to SharedPreferences
                                JSONArray arr = respReq.getJSONArray("listaDatos");

                                if(arr.length()<=0){
                                    TextView noCommu = new TextView(CommunityActivity.this);
                                    String contenido = "No perteneces a ninguna comunidad";
                                    noCommu.setText(contenido);
                                    noCommu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                    ((LinearLayout) linearLayout).addView(noCommu);
                                } else {
                                    for(int i = 0; i < arr.length(); i++){
                                        final String id = arr.getJSONObject(i).getString("idComunidad");
                                        String nombre = arr.getJSONObject(i).getString("nombreComunidad");
                                        String desc = arr.getJSONObject(i).getString("decripcionComunidad");
                                        String entre = arr.getJSONObject(i).getString("idEntrenador");

                                        TextView searchResult = new TextView(CommunityActivity.this);
                                        String displayString = nombre + System.getProperty("line.separator") + desc + System.getProperty("line.separator") + entre
                                                + System.getProperty("line.separator");

                                        searchResult.setText(displayString);
                                        searchResult.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                        searchResult.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intent = new Intent(CommunityActivity.this, CommunityWallActivity.class);
                                                intent.putExtra("COMMUNITY_ID", id);
                                                CommunityActivity.this.startActivity(intent);


                                            }
                                        });

                                        ((LinearLayout) linearLayout).addView(searchResult);
                                    }
                                }



                            } else{

                                //Login unsuccessful

                            }
                        }catch (JSONException jsone){
                            //Show error in login in.
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Communities","That didn't work!");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
                Map<String, String> params = new HashMap<>();
                params.put("idUser", pref.getString("id", ""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
