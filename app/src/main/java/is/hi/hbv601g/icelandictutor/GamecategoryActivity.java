package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamecategoryActivity extends AppCompatActivity {
    private Button mAnimalButton;
    private Button mClothingButton;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecategory);

        mAnimalButton = findViewById(R.id.animalButton);
        mClothingButton = findViewById(R.id.clothesButton);
        mBackButton = findViewById(R.id.backButton);

        // Send button value to DictionaryActivity to show the words in the selected category
        mAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCat("1");
            }
        });
        mClothingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCat("2");
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
    }

    // Go to dictionary page for relevant category (value of selection is sent to DictionaryActivity to display correct page)
    private void goToCat(String value) {
        Intent i = new Intent(GamecategoryActivity.this, GamelevelActivity.class);
        i.putExtra("category", value);
        startActivity(i);
    }

    private void goToMain() {
        Intent intent = new Intent(GamecategoryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
