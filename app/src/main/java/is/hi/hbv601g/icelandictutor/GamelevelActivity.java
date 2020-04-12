package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GamelevelActivity extends AppCompatActivity {
    private Button mlevel1Button;
    private Button mlevel2Button;
    private Button mBackButton;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelevel);

        /* Get info on what category was selected in DictionarySelection */
        Bundle extras = getIntent().getExtras();
        value = extras.getString("category");
        Log.e("onValue: ", value);
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

        getUserProgress();

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

    private void getUserProgress() {
        Context context = getApplicationContext();
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

                        try {
                            Boolean animal1 = response.isNull("animals_lvl_1") ? false : response.getBoolean("animals_lvl_1");
                            Boolean animal2 = response.isNull("animals_lvl_2") ? false : response.getBoolean("animals_lvl_2");
                            Boolean animal3 = response.isNull("animals_lvl_3") ? false : response.getBoolean("animals_lvl_3");

                            Boolean clothes1 = response.isNull("clothes_lvl_1") ? false : response.getBoolean("clothes_lvl_1");
                            Boolean clothes2 = response.isNull("clothes_lvl_2") ? false : response.getBoolean("clothes_lvl_2");
                            Boolean clothes3 = response.isNull("clothes_lvl_3") ? false : response.getBoolean("clothes_lvl_3");

                            Log.e("onProgressv: ", value);

                            if(value.equals("1")) {
                                if(animal1) {
                                    mlevel1Button.setBackgroundColor(Color.GREEN);
                                }
                                if(animal2) {
                                    mlevel2Button.setBackgroundColor(Color.GREEN);
                                }
                                /*if(animal3) {

                                }*/
                            }

                            else if(value.equals("2")) {
                                if(clothes1) {
                                    mlevel1Button.setBackgroundColor(Color.GREEN);
                                }
                                if(clothes2) {
                                    mlevel2Button.setBackgroundColor(Color.GREEN);
                                }
                                /*if(clothes3) {

                                }*/
                            }

                            //Log.e("onResponse: ", score);
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
