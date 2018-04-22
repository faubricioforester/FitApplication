package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        final String idCom = getIntent().getStringExtra("COMMUNITY_ID");

        final EditText body = (EditText) findViewById(R.id.editTextPostBody);
        Button bPost = (Button)findViewById(R.id.buttonPost);

        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(NewPostActivity.this);
                //this is the url where you want to send the request
                //TODO Change to the right API request
                String url = "https://binarycoffee.net/fitApp/api/agregarPublicacion.php";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try{
                                    JSONObject respReq = new JSONObject(response);
                                    String message = respReq.getString("message");

                                    if(message.equals("OK")){
                                        Intent intent = new Intent(NewPostActivity.this,
                                                CommunityWallActivity.class);

                                        intent.putExtra("COMMUNITY_ID", idCom);
                                        NewPostActivity.this.startActivity(intent);
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


                        SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);


                        params.put("idComunidad", idCom);
                        params.put("idUser",pref.getString("id",""));
                        params.put("texto", body.getText().toString());

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }
}
