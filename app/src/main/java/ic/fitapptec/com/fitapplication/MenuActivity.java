package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MenuActivity extends AppCompatActivity {
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onMyProfileButtonClick(View v){
        pref = getSharedPreferences(PREFS_NAME, 0);

        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class  );
        intent.putExtra("PROFILE_TYPE", "USER");
        intent.putExtra("PROFILE_OWNER_ID", pref.getString("id",""));
        intent.putExtra("USER_NAME", pref.getString("name",""));
        intent.putExtra("USER_BIO", pref.getString("bio",""));
        intent.putExtra("USER_EMAIL", pref.getString("correo",""));
        intent.putExtra("USER_PHONE", pref.getString("telefono",""));
        MenuActivity.this.startActivity(intent);



    }

    public void onCommunityButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, CommunityActivity.class  );

        MenuActivity.this.startActivity(intent);


    }

    public void onSavedButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, SavedActivity.class  );
        MenuActivity.this.startActivity(intent);

    }

    public void onSearchButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, SearchActivity.class  );
        MenuActivity.this.startActivity(intent);

    }
    public void onMyTrainerButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, MyTrainerActivity.class  );
        MenuActivity.this.startActivity(intent);

    }
    public void onStatisticsButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, StatisticActivity.class  );
        MenuActivity.this.startActivity(intent);

    }
    public void onExitButtonClick(View v){
        //Borrar currentUser de SharedPreferences


        Intent intent = new Intent(MenuActivity.this, MainActivity.class  );
        MenuActivity.this.startActivity(intent);

    }

    public void onMyWorkoutRoutinesClick(View v){

        Intent intent = new Intent(MenuActivity.this, MyWorkoutRoutinesActivity.class);
        MenuActivity.this.startActivity(intent);
    }
}
