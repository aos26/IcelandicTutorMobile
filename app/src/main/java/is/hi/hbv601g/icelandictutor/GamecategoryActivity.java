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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        getUserProgress();

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
                        try {
                            Boolean animal1 = response.isNull("animals_lvl_1") ? false : response.getBoolean("animals_lvl_1");
                            Boolean animal2 = response.isNull("animals_lvl_2") ? false : response.getBoolean("animals_lvl_2");
                            Boolean animal3 = response.isNull("animals_lvl_3") ? false : response.getBoolean("animals_lvl_3");

                            Boolean clothes1 = response.isNull("clothes_lvl_1") ? false : response.getBoolean("clothes_lvl_1");
                            Boolean clothes2 = response.isNull("clothes_lvl_2") ? false : response.getBoolean("clothes_lvl_2");
                            Boolean clothes3 = response.isNull("clothes_lvl_3") ? false : response.getBoolean("clothes_lvl_3");

                            if(animal1 && animal2 && animal3) {
                                mAnimalButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_black_18dp, 0);
                            }
                            if(clothes1 && clothes2 && clothes3) {
                                mClothingButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_circle_black_18dp, 0);
                            }

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
            case R.id.menu6:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(GamecategoryActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(GamecategoryActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(GamecategoryActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(GamecategoryActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(GamecategoryActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(GamecategoryActivity.this, FlashcardActivity.class);
        intent.putExtra("number",0);
        startActivity(intent);
    }

    public void logout() {
        Context context = getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);
        settings.edit().putLong("userID", 0).apply();

        Intent intent = new Intent(GamecategoryActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
