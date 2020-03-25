package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity {
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String category = extras.getString("category");
            String level = extras.getString("level");
            Integer correctCounter = extras.getInt("correct");
            Log.e( "onCreate: ", category);
            Log.e( "onCreate: ", level);

            if (correctCounter == 10) {
                // finish game - set texts and remove buttons
            }
            else {
                createQuestion(category, level, correctCounter);
            }
        }

        /*
        TextView c = (TextView) findViewById(R.id.categorySelect);
        TextView l = (TextView) findViewById(R.id.levelSelect);

        if(category.equals("1")){
            //c.setText("Animals");
        }
        else if(category.equals("2")){
            //c.setText("Clothes");
        }

        if(level.equals("1")){
            //l.setText("Level 1");
        }
        else if(level.equals("2")){
            //l.setText("Level 2");
        }
        */


/*
        mBackButton = findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

 */


    }

    private void counter(String category, String level, Integer counter) {
        if (counter == 10){
            // finish game -
        }
        else {
            createQuestion(category, level, counter);
        }
    }

    private void createQuestion(String value, String level, Integer counter) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Piece together the correct url based on category that was selected
        String urlStart = "https://icelandic-tutor.herokuapp.com/questions?cat_id=";
        String urlCat = value;  // Fetch words from category that user selected
        String urlLvl = "&lvl_id=" + level;
        String url = urlStart + urlCat + urlLvl;
        System.out.println(url);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());

                        // Fetch one question
                        Integer lengd = response.length()+1;
                        Integer spurning = (int) (Math.random()*lengd);
                        System.out.println(spurning);


                        JSONObject jsonobject = null;
                        try {
                            jsonobject = response.getJSONObject(spurning);
                            String question = jsonobject.getString("questionWord");
                            String answer = jsonobject.getString("answer");
                            String wrong1 = jsonobject.getString("wrongAnswer1");
                            String wrong2 = jsonobject.getString("wrongAnswer2");
                            Log.e("onResponse: ", question);
                            Log.e("onResponse: ", answer);
                            Log.e("onResponse: ", wrong1);
                            Log.e("onResponse: ", wrong2);
                            //createTableEntry(question, answer);  // Feed each word into method to create a row entry for the word
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

    private void goHome() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
