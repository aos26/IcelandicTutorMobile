package is.hi.hbv601g.icelandictutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class DictionaryActivity extends AppCompatActivity {
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        /* Get info on what category was selected in DictionarySelection */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            Log.e( "onCreate: ", value);
            populateDictionary(value);   // Insert words from the selected category
        }
        mBackButton = findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDictionarySelection();
            }
        });
    }

    // Create layout for words in category
    private void createTableEntry(String question, String answer) {
        TableLayout tl = (TableLayout) findViewById(R.id.myTable);
        /* Create a new row to be added. */
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        /* Create TextView to be the row-content. */
        TextView isl = new TextView(this);
        isl.setText(question);
        isl.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        isl.setGravity(Gravity.CENTER);

        TextView eng = new TextView(this);
        eng.setText(answer);
        eng.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        eng.setGravity(Gravity.CENTER);

        /* Add TextView to row. */
        tr.addView(isl);
        tr.addView(eng);

        /* Add row to TableLayout. */
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    // Fetch words from category
    private void populateDictionary(String value) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Piece together the correct url based on category that was selected
        String urlStart = "https://icelandic-tutor.herokuapp.com/questions?cat_id=";
        String urlCat = value;  // Fetch words from category that user selected
        String urlLvl = "&lvl_id=1";
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

                        // Fetch all the Icelandic words and their corresponding English word
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                                String question = jsonobject.getString("questionWord");
                                String answer = jsonobject.getString("answer");
                                Log.e("onResponse: ", question);
                                Log.e("onResponse: ", answer);
                                createTableEntry(question, answer);  // Feed each word into method to create a row entry for the word
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

    private void goToDictionarySelection() {
        Intent intent = new Intent(DictionaryActivity.this, DictionarySelectionActivity.class);
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
                goToDictionary();
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
        Intent intent = new Intent(DictionaryActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionary(){
        Intent intent = new Intent(DictionaryActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(DictionaryActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(DictionaryActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(DictionaryActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(DictionaryActivity.this, FlashcardActivity.class);
        intent.putExtra("number",0);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(DictionaryActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void logout() {
        Context context = getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);
        settings.edit().putLong("userID", 0).apply();

        Intent intent = new Intent(DictionaryActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
