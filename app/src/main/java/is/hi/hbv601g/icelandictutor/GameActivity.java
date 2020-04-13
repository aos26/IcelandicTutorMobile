package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class GameActivity extends AppCompatActivity {
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button nextQuestion;

    private Integer correct;
    private String category;
    private String level;
    private Integer total;

    private int currScore = 0;
    private int pointsEarned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("category");
            level = extras.getString("level");
            correct = extras.getInt("correct");
            total = extras.getInt("total");

            Log.e("onCreate: ", category);
            Log.e("onCreate: ", level);

            if (correct == 10) {
                getCurrUserScore();
            } else {
                getQuestion();
                total++;
            }
        }
    }

    // Update users' score
    private void calculateScore(int incorrect, int currScore) {
        int plusMod = correct * 10;
        int minusMod = incorrect * (-5);
        int totalScore = plusMod + minusMod;
        if (totalScore < 0) {
            totalScore = 0;
            return;
        }
        pointsEarned = totalScore;
        Log.e("calcScore: pointsearned", Integer.toString(pointsEarned));
        Log.e("calculateScore:for user", Integer.toString(currScore) );
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.e("calculateScore total: ", Integer.toString(totalScore));
        totalScore += currScore;
        Log.e("calculateScore total2: ", Integer.toString(totalScore));
        String url = "https://icelandic-tutor.herokuapp.com/updateScore?id=" + userID + "&newScore=" + totalScore;
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
        goFinish();
    }
    // get users' score
    private void getCurrUserScore() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);
        final int incorrect = total - correct;

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
                        try {
                            currScore = response.getInt("score");
                            Log.e("onResponse currScore: ", Integer.toString(currScore));
                            calculateScore(incorrect, currScore);
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
    // get a new question
    private void getQuestion() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Piece together the correct url based on category that was selected
        String urlStart = "https://icelandic-tutor.herokuapp.com/questions?cat_id=";
        String urlCat = category;  // Fetch words from category that user selected
        String urlLvl = "&lvl_id=" + level;
        String url = urlStart + urlCat + urlLvl;

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());

                        // Fetch one question
                        Integer lengd = response.length();
                        Integer spurning = (int) (Math.random()*lengd);
                        System.out.println(spurning);

                        JSONObject jsonobject = null;
                        try {
                            jsonobject = response.getJSONObject(spurning);
                            String question = "";
                            if(level.equals("3")){
                                question = jsonobject.getString("question_image");
                            }
                            else{
                                question = jsonobject.getString("questionWord");
                            }
                            String answer = jsonobject.getString("answer");
                            String wrong1 = jsonobject.getString("wrongAnswer1");
                            String wrong2 = jsonobject.getString("wrongAnswer2");
                            createQuestion(question, answer, wrong1, wrong2); // Feed each word into method to create a row entry for the word
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

    private void createQuestion(String question, final String answer, String wrong1, String wrong2) {
        TextView nrspurning = (TextView) findViewById(R.id.questionNumber);
        nrspurning.setText("Question " + total);

        if(level.equals("3")){
            new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(question);
            TextView texti = (TextView) findViewById(R.id.word);
            texti.setVisibility(View.INVISIBLE); // hide textView
        }
        else {
            TextView texti = (TextView) findViewById(R.id.word);
            texti.setText(question);
            ImageView mynd = findViewById(R.id.imageView);
            mynd.setVisibility(View.INVISIBLE); // hide imageView
        }

        valmoguleikar(answer, wrong1, wrong2);


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void valmoguleikar(final String answer, String wrong1, String wrong2) {
        mAnswer1 = findViewById(R.id.ans1);
        mAnswer2 = findViewById(R.id.ans2);
        mAnswer3 = findViewById(R.id.ans3);

        Integer tilfelli = (int) (Math.random()*3);
        if(tilfelli==0){
            mAnswer1.setText(answer);
            mAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer1.setBackgroundColor(Color.GREEN);
                    correct++;
                    mAnswer2.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
            mAnswer2.setText(wrong1);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.RED);
                    mAnswer1.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer3.setText(wrong2);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.RED);
                    mAnswer1.setClickable(false);
                    mAnswer2.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
        }
        else if(tilfelli==1) {
            mAnswer1.setText(wrong1);
            mAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer1.setBackgroundColor(Color.RED);
                    mAnswer2.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer2.setText(answer);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.GREEN);
                    correct++;
                    mAnswer1.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
            mAnswer3.setText(wrong2);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.RED);
                    mAnswer2.setClickable(false);
                    mAnswer1.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
        }
        else if(tilfelli==2) {
            mAnswer1.setText(wrong1);
            mAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer1.setBackgroundColor(Color.RED);
                    mAnswer2.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer2.setText(wrong2);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.RED);
                    mAnswer1.setClickable(false);
                    mAnswer3.setClickable(false);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer3.setText(answer);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.GREEN);
                    correct++;
                    mAnswer1.setClickable(false);
                    mAnswer2.setClickable(false);
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
        }

    }





    // show correct answer
    private void goToAnswer(final Boolean answer, final String correctanswer) {
        TextView texti = (TextView) findViewById(R.id.answer);
        nextQuestion = findViewById(R.id.nextQuestion);

        if(answer){
            texti.setText("Correct");
            nextQuestion.setVisibility(View.VISIBLE);
            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToNextQuestion();
                }
            });
        }
        else {
            String wronganswer = "Wrong! The correct answer is: " + correctanswer;
            texti.setText(wronganswer);
            nextQuestion.setVisibility(View.VISIBLE);
            nextQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToNextQuestion();
                }
            });
        }
    }
    // go to next question
    private void goToNextQuestion() {
        Intent i = new Intent(GameActivity.this, GameActivity.class);
        i.putExtra("category", category);
        i.putExtra("level", level);
        i.putExtra("correct",correct);
        i.putExtra("total",total);
        startActivity(i);
    }

    // go to the finishing page
    private void goFinish() {
        Intent intent = new Intent(GameActivity.this, FinishedgameActivity.class);
        intent.putExtra("correct",correct); //scoreeeee
        intent.putExtra("total",total);
        Log.e("goMain: pointsearned", Integer.toString(pointsEarned));
        intent.putExtra("userScore", pointsEarned);
        intent.putExtra("category", category);
        intent.putExtra("level", level);
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
                goToArticles();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(GameActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(GameActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(GameActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticles() {
        Intent intent = new Intent(GameActivity.this, ArticleActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(GameActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
