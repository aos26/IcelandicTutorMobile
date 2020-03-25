package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    private Button nextQuestion;

    private Integer correct;
    private String category;
    private String level;
    private Integer total;

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

            Log.e( "onCreate: ", category);
            Log.e( "onCreate: ", level);

            if (correct == 10) {
                goMain();
            }
            else {
                getQuestion();
                total++;
            }
        }
    }

    private void getQuestion() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Piece together the correct url based on category that was selected
        String urlStart = "https://icelandic-tutor.herokuapp.com/questions?cat_id=";
        String urlCat = category;  // Fetch words from category that user selected
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
                        Integer lengd = response.length();
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
                            createQuestion(question, answer, wrong1, wrong2);  // Feed each word into method to create a row entry for the word
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
        mAnswer1 = findViewById(R.id.ans1);
        mAnswer2 = findViewById(R.id.ans2);
        mAnswer3 = findViewById(R.id.ans3);

        TextView nrspurning = (TextView) findViewById(R.id.questionNumber);
        nrspurning.setText("Question " + total);

        TextView texti = (TextView) findViewById(R.id.word);
        texti.setText(question);

        Integer tilfelli = (int) (Math.random()*3);
        if(tilfelli==0){
            mAnswer1.setText(answer);
            mAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer1.setBackgroundColor(Color.GREEN);
                    correct++;
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
            mAnswer2.setText(wrong1);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.RED);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer3.setText(wrong2);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.RED);
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
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer2.setText(answer);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.GREEN);
                    correct++;
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
            mAnswer3.setText(wrong2);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.RED);
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
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer2.setText(wrong2);
            mAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer2.setBackgroundColor(Color.RED);
                    goToAnswer(Boolean.FALSE, answer);
                }
            });
            mAnswer3.setText(answer);
            mAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnswer3.setBackgroundColor(Color.GREEN);
                    correct++;
                    goToAnswer(Boolean.TRUE, answer);
                }
            });
        }
    }

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

    private void goToNextQuestion() {
        Intent i = new Intent(GameActivity.this, GameActivity.class);
        i.putExtra("category", category);
        i.putExtra("level", level);
        i.putExtra("correct",correct);
        i.putExtra("total",total);
        // System.out.println("FJÖLDI réttra svara er: " + correct);
        startActivity(i);
    }

    private void goMain() {
        Intent intent = new Intent(GameActivity.this, FinishedgameActivity.class);
        intent.putExtra("correct",correct); //scoreeeee
        intent.putExtra("total",total);
        startActivity(intent);
    }


}
