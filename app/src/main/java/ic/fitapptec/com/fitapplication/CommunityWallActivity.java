package ic.fitapptec.com.fitapplication;

import android.content.Intent;
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

public class CommunityWallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_wall);

        final LinearLayout wall = findViewById(R.id.linearLayoutCommWall);

        final String id_community = getIntent().getStringExtra("COMMUNITY_ID");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(CommunityWallActivity.this);
        //this is the url where you want to send the request
        //TODO Change to the right API request
        String url = "https://binarycoffee.net/fitApp/api/getPublicacionesComunidad.php";
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

                                for(int i = 0; i < arr.length(); i++){
                                    final String idPost = arr.getJSONObject(i).getString("idPublicacion");
                                    String textPost = arr.getJSONObject(i).getString("textoPublicacion");
                                    String datePost = arr.getJSONObject(i).getString("fechaPublicacion");
                                    String userPost = arr.getJSONObject(i).getString("userPublicacion");

                                    TextView searchResult = new TextView(CommunityWallActivity.this);
                                    String displayString = textPost + System.getProperty("line.separator")
                                            +datePost + System.getProperty("line.separator")
                                            + userPost + System.getProperty("line.separator");
                                    searchResult.setText(displayString);
                                    searchResult.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));

                                    Button bComment = new Button(CommunityWallActivity.this);
                                    String bString = "Ver comentarios";
                                    bComment.setText(bString);

                                    bComment.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    bComment.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(CommunityWallActivity.this,
                                                    PostCommentsActivity.class);

                                            intent.putExtra("ID_POST", idPost);
                                            CommunityWallActivity.this.startActivity(intent);

                                        }
                                    });

                                    ((LinearLayout) wall).addView(searchResult);
                                    ((LinearLayout) wall).addView(bComment);





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

                params.put("idComunidad", id_community);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        EditText newPost = (EditText)findViewById(R.id.editTextNewPost);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityWallActivity.this, NewPostActivity.class);
                intent.putExtra("COMMUNITY_ID", id_community);
                CommunityWallActivity.this.startActivity(intent);
            }
        });
    }
}
