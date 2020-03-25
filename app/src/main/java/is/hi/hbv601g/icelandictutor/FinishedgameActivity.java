package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishedgameActivity extends AppCompatActivity {
    private Button mScoreButton;
    private Button mDictionaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishedgame);


        // go to dictionary
        mDictionaryButton = findViewById(R.id.dictButton);
        mDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDictionarySelection();
            }
        });


        // go to Score
        mScoreButton = findViewById(R.id.scoreButton);
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScoreboard();
            }
        });
    }

    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(FinishedgameActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(FinishedgameActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }
}
