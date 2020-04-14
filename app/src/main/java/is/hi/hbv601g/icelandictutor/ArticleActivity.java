package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleActivity extends AppCompatActivity {
    private Button mBackButton;
    private Button mToggleLang;
    private TextView mTitleView;
    private TextView mTextView;

    String value;
    private boolean icelandic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        /* Get info on what category was selected in DictionarySelection */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            Log.e( "onCreateText: ", value);
            insertText();   // Insert words from the selected category
        }
        mToggleLang = findViewById(R.id.languageButton);
        mToggleLang.setText("Read in Icelandic");
        mTitleView = findViewById(R.id.titleView);
        mTextView = findViewById(R.id.textView);
        mBackButton = findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToArticleSelection();
            }
        });
        mToggleLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLang();
            }
        });
    }

    public void setLang() {
        if(icelandic) {
            icelandic = false;
            mToggleLang.setText("Read in Icelandic");
        }
        else {
            icelandic = true;
            mToggleLang.setText("Read in English");
        }

        insertText();
    }

    public void insertText() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Piece together the correct url based on category that was selected
        String urlStart = "https://icelandic-tutor.herokuapp.com/article?id=";
        String urlID = value;  // Fetch text from article that user selected
        String url = urlStart + urlID;

        Log.e("insertText: ", url);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest response", response.toString());

                        try {
                            String titleEN = response.getString("nameen");
                            String titleIS = response.getString("nameis");
                            String textEN = response.getString("articleenglish");
                            String textIS = response.getString("articleicelandic");

                            Log.e("onArticle: ", titleEN);
                            Log.e("onArticle: ", titleIS);

                            if(icelandic) {
                                mTitleView.setText(titleIS);
                                mTextView.setText(textIS);
                            }
                            else {
                                mTitleView.setText(titleEN);
                                mTextView.setText(textEN);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(ArticleActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(ArticleActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(ArticleActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(ArticleActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(ArticleActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(ArticleActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
