package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GamecategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecategory);
    }

    public void category(View view) {
        Intent intent = new Intent(GamecategoryActivity.this, GamelevelActivity.class);
        startActivity(intent);
    }
    public void home(View view) {
        Intent intent = new Intent(GamecategoryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
