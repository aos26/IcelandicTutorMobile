package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FinishedgameActivity extends AppCompatActivity {
    private Button mScoreButton;
    private Button mDictionaryButton;
    private TextView mCurrUserText;
    private int gamescore;
    private Integer total;
    private String level;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishedgame);
        mCurrUserText = findViewById(R.id.userScoreText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Integer correct = extras.getInt("correct"); //scoreeeee
            total = extras.getInt("total");
            gamescore = extras.getInt("userScore");
            level = extras.getString("level");
            category = extras.getString("category");

            TextView texti = (TextView) findViewById(R.id.word);
            texti.setText("You answered 10 out of " + total + " questions correctly");

            mCurrUserText.setText("You earned " + gamescore + " points");
        }

        updateProgress();

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

    public void updateProgress() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://icelandic-tutor.herokuapp.com/updateProgress?user_id=" + userID + "&cat_id=" + category + "&lvl_id=" + level;
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Update response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest update error", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
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

    // menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    // on click listeners in menubar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu0:
                goToMain();
                return true;
            case R.id.menu1:
                goToDictionarySelection();
                return true;
            case R.id.menu2:
                goToGameselection();
                return true;
            case R.id.menu3:
                goToScoreboard();
                return true;
            case R.id.menu4:
                goToFlashcards();
                return true;
            case R.id.menu5:
                goToArticleSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(FinishedgameActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(FinishedgameActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(FinishedgameActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(FinishedgameActivity.this, FlashcardActivity.class);
        intent.putExtra("number",0);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(FinishedgameActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
