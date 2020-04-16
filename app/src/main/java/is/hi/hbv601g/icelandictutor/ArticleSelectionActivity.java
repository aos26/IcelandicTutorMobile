package is.hi.hbv601g.icelandictutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleSelectionActivity extends AppCompatActivity {
    private Button mHildurButton;
    private Button mTheaterButton;
    private Button mReykjavikButton;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleselection);

        mHildurButton = findViewById(R.id.HildurWins);
        mTheaterButton = findViewById(R.id.Theater);
        mReykjavikButton = findViewById(R.id.visitReykjavik);
        mBackButton = findViewById(R.id.backButton);

        // Send button value to DictionaryActivity to show the words in the selected category
        mHildurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToArticle("2");
            }
        });
        mTheaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToArticle("1");
            }
        });
        mReykjavikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToArticle("3");
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
    private void goToArticle(String value) {
        Intent i = new Intent(ArticleSelectionActivity.this, ArticleActivity.class);
        i.putExtra("key", value);
        startActivity(i);
    }

    private void goToMain() {
        Intent intent = new Intent(ArticleSelectionActivity.this, MainActivity.class);
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
            case R.id.menu6:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(ArticleSelectionActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(ArticleSelectionActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(ArticleSelectionActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(ArticleSelectionActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(ArticleSelectionActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(ArticleSelectionActivity.this, FlashcardActivity.class);
        intent.putExtra("number",0);
        startActivity(intent);
    }

    public void logout() {
        Context context = getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);
        settings.edit().putLong("userID", 0).apply();

        Intent intent = new Intent(ArticleSelectionActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
