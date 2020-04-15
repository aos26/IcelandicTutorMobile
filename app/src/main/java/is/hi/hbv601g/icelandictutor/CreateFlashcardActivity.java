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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateFlashcardActivity extends AppCompatActivity {
    private Button mAddFlashcardButton;
    private Button mBackButton;
    private EditText mIcelandic;
    private EditText mEnglish;

    private String flashcardURL = "https://icelandic-tutor.herokuapp.com/createflashcard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);
        retrieveFields();

        mAddFlashcardButton = findViewById(R.id.addNewFlashcard);
        mAddFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlashcard();
            }
        });

        mBackButton = findViewById(R.id.backToAllFlashcards);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFlashcards();
            }
        });


    }

    private void retrieveFields() {
        mIcelandic = findViewById(R.id.frontCard);
        mEnglish = findViewById(R.id.backCard);
    }

    private void addFlashcard() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String icelandicInput = mIcelandic.getText().toString();
        String englishInput = mEnglish.getText().toString();

        /* Make sure fields aren't empty */
        if(icelandicInput.equals("") || englishInput.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        Map<String, String> params = new HashMap();
        params.put("icelandic", icelandicInput);
        params.put("english", englishInput);
        params.put("userid", Long.toString(userID));


        Log.e( "loginClick: ", icelandicInput);
        Log.e( "loginClick: ", englishInput);

        JSONObject jsonUser = new JSONObject(params);  // Create JSONObject using info user entered

        /* Send objectRequest to the database to create new user */
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, flashcardURL, jsonUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "You created a new flashcard!", Toast.LENGTH_SHORT).show();
                        Log.e("Rest response", response.toString());
                        createNewFlashcard();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Couldn't add new Flashcard", Toast.LENGTH_SHORT).show();
                        Log.e("Rest login error", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void createNewFlashcard() {
        Intent intent = new Intent(CreateFlashcardActivity.this, CreateFlashcardActivity.class);
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


    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(CreateFlashcardActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(CreateFlashcardActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(CreateFlashcardActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(CreateFlashcardActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(CreateFlashcardActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(CreateFlashcardActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(CreateFlashcardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
