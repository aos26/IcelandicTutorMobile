package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    private Integer gamescore;
    private Integer total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishedgame);
        mCurrUserText = findViewById(R.id.userScoreText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Integer correct = extras.getInt("correct"); //scoreeeee
            total = extras.getInt("total");
            getCurrUserScore();
            TextView texti = (TextView) findViewById(R.id.word);
            texti.setText("You answered 10 out of " + total + " questions correctly");
        }


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


    private void getCurrUserScore() {
        final Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("getCurrUserScore: ", Long.toString(userID));
        String url = "https://icelandic-tutor.herokuapp.com/user?user_id=" + userID;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Score response", response.toString());

                        //JSONObject jsonobject = null;
                        try {
                            //jsonobject = response.getJSONObject(0);

                            int score = response.getInt("score");
                            gamescore= (100*10)-10*(total-10);
                            mCurrUserText.setText("You got " + gamescore + " points for playing this game");
                            //Log.e("onResponse: ", score);
                            score = score + gamescore;
                            // update score - SEINNA
                            response.put("score", score);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest error", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
    }
}
