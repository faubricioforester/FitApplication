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

public class PostCommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        final LinearLayout linearLayout = findViewById(R.id.linearLayoutAllComments);
        final String id_post = getIntent().getStringExtra("ID_POST");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(PostCommentsActivity.this);
        //this is the url where you want to send the request
        //TODO Change to the right API request
        String url = "https://binarycoffee.net/fitApp/api/getComentariosPublicacion.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject respReq = new JSONObject(response);
                            String message = respReq.getString("message");

                            if(message.equals("OK")){

                                JSONArray arr = respReq.getJSONArray("listaDatos");

                                for(int i = 0; i < arr.length(); i++) {

                                    String idComentario = arr.getJSONObject(i).getString("idComentario");
                                    String textoComentario = arr.getJSONObject(i).getString("textoComentario");
                                    String fechaComentario = arr.getJSONObject(i).getString("fechaComentario");
                                    String userComentario = arr.getJSONObject(i).getString("userComentario");

                                    TextView commentPosted = new TextView(PostCommentsActivity.this);
                                    String displayString = textoComentario + System.getProperty("line.separator")
                                            + fechaComentario + System.getProperty("line.separator")
                                            + "Por " + userComentario + System.getProperty("line.separator")
                                            + System.getProperty("line.separator");
                                    commentPosted.setText(displayString);
                                    commentPosted.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                    ((LinearLayout) linearLayout).addView(commentPosted);

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
                Log.e("Error On Login","That didn't work!");
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("idPublicacion", id_post);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onPostCommentClick(View view){


        TextView textView = (TextView)findViewById(R.id.editTextCommentToPost);
        final String comment = textView.getText().toString();
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        final String idUser = preferences.getString("id", "");
        final String idpost = getIntent().getStringExtra("ID_POST");


        RequestQueue queue = Volley.newRequestQueue(PostCommentsActivity.this);
        //this is the url where you want to send the request
        //TODO Change to the right API request
        String url = "https://binarycoffee.net/fitApp/api/agregarComentarioPublicacion.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject respReq = new JSONObject(response);
                            String message = respReq.getString("message");

                            if(message.equals("OK")){
                                startActivity(new Intent(PostCommentsActivity.this, PostCommentsActivity.class));
                                finish();

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
                params.put("idUser", idUser);
                params.put("idPublicacion", idpost);
                params.put("texto", comment);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
