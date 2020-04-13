package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button mScoreButton;
    private Button mDictionaryButton;
    private Button mGamecategoryButton;
    private Button mArticleButton;
    private Button mFlashcardButton;
    private Button mLocationButton;

    //used for register alarm manager
    PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // go to dictionary
        mDictionaryButton = findViewById(R.id.dictButton);
        mDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDictionarySelection();
            }
        });


        // go to Score
        mScoreButton = findViewById(R.id.scoreButton);
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScoreboard();
            }
        });

        // go to category selection
        mGamecategoryButton = findViewById(R.id.categoryButton);
        mGamecategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameselection();
            }
        });

        // go to articles
        mArticleButton = findViewById(R.id.articleButton);
        mArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToArticles();
            }
        });

        // go to location
        mLocationButton = findViewById(R.id.locationButton);
        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLocation();
            }
        });

        // go to flashcards
        mFlashcardButton = findViewById(R.id.flashcardButton);
        mFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFlashcards();
            }
        });

    }

    // Go to scoreboard view
    public void goToScoreboard() {
        Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(MainActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(MainActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticles() {
        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(MainActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }

    // notifications

}
