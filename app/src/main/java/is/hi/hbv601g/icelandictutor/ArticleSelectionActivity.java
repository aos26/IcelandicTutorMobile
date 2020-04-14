package is.hi.hbv601g.icelandictutor;

import android.content.Intent;
import android.os.Bundle;
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
}
