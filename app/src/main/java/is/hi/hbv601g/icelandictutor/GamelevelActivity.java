package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class GamelevelActivity extends AppCompatActivity {
    private Button mlevel1Button;
    private Button mlevel2Button;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelevel);

        /* Get info on what category was selected in DictionarySelection */
        Bundle extras = getIntent().getExtras();
        final String value = extras.getString("category");
        System.out.println(value);
        TextView texti = (TextView) findViewById(R.id.categorySelected);

        if(value.equals("1")){
            texti.setText("Animals");
        }
        else if(value.equals("2")){
            texti.setText("Clothes");
        }

        mlevel1Button = findViewById(R.id.level1Button);
        mlevel2Button = findViewById(R.id.level2Button);
        mBackButton = findViewById(R.id.backButton);


        mlevel1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame(value,"1");
            }
        });

        mlevel2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame(value,"2");
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategorySelection();
            }
        });
    }


    private void goToGame(String category, String level) {
        Intent i = new Intent(GamelevelActivity.this, GameActivity.class);
        i.putExtra("category", category);
        i.putExtra("level", level);
        i.putExtra("correct",0);
        i.putExtra("total",0);
        startActivity(i);
    }

    private void goToCategorySelection() {
        Intent intent = new Intent(GamelevelActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }
}
