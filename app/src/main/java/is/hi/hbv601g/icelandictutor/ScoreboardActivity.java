package is.hi.hbv601g.icelandictutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreboardActivity extends AppCompatActivity {
    private int loopLength = 10;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        mBackButton = findViewById(R.id.backButton);
        populateScoreboard();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
    }

    // Create layout for scoreboard
    private void createTableEntry(String userName, int score, int i) {
        TableLayout tl = (TableLayout) findViewById(R.id.myScoreTable);
        /* Create a new row to be added. */
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        /* Create TextView to be the row-content. */
        TextView place = new TextView(this);
        place.setText(Integer.toString(i));
        place.setLayoutParams(new TableRow.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        place.setGravity(Gravity.CENTER);

        TextView username = new TextView(this);
        username.setText(userName);
        username.setLayoutParams(new TableRow.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        username.setGravity(Gravity.CENTER);

        TextView userScore = new TextView(this);
        userScore.setText(Integer.toString(score));
        userScore.setLayoutParams(new TableRow.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        userScore.setGravity(Gravity.CENTER);

        /* Add TextView to row. */
        tr.addView(place);
        tr.addView(username);
        tr.addView(userScore);

        /* Add row to TableLayout. */
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    // Fetch top user scores
    private void populateScoreboard() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://icelandic-tutor.herokuapp.com/scoreboard";

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());
                        if(response.length() <= 10) {
                            loopLength = response.length();
                        }

                        /* Fetch top ten user scores */
                        for (int i = 0; i < loopLength; i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                                String userName = jsonobject.getString("userName");
                                int score = jsonobject.getInt("score");
                                Log.e("onResponse: ", userName);
                                //Log.e("onResponse: ", score);
                                createTableEntry(userName, score, i+1);  // Create row entry for user
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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


    // Go back to main page
    private void goToMain() {
        Intent intent = new Intent(ScoreboardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
