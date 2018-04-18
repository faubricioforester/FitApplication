package ic.fitapptec.com.fitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onMyProfileButtonClick(View v){
        Intent intent = new Intent(MenuActivity.this, MyProfileActivity.class  );
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
}
