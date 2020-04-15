package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Array;

public class FlashcardActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private Button mTurn;
    private Button mCreateFlashcards;
    private Integer mFlashcardNumber;
    private Button mNextFlashcard;
    private Button mDeleteFlashcard;
    private FrameLayout mFlashcard;
    private String mFlashcardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mFlashcardNumber = extras.getInt("number");
            getFlashcard();
        }


        // flip flashcards
        findViews();
        loadAnimations();
        changeCameraDistance();



        mCreateFlashcards = findViewById(R.id.newFlashcard);
        mCreateFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreatFlashcards();
            }
        });

        mNextFlashcard = findViewById(R.id.nextFlashcard);
        mNextFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFlashcard(mFlashcardNumber);
            }
        });

        mDeleteFlashcard = findViewById(R.id.delete);
        mDeleteFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFlashcard();
                goToNextFlashcard(mFlashcardNumber);
            }
        });


    }

    // Go to dictionary view
    public void goToCreatFlashcards(){
        Intent intent = new Intent(FlashcardActivity.this, CreateFlashcardActivity.class);
        startActivity(intent);
    }

    public void goToNextFlashcard(Integer number){
        Intent intent = new Intent(FlashcardActivity.this, FlashcardActivity.class);
        intent.putExtra("number",number);
        startActivity(intent);
    }



    private void getFlashcard() {
        //Log.e("Tala: ", String.valueOf(numberFlashcard));
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);


        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://icelandic-tutor.herokuapp.com/flashcards?userid=" + userID;
        Log.e("Flashcard: ", String.valueOf(userID));

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());
                        JSONObject jsonobject = null;
                            if (response.length() != 0) {
                                Log.e("Lengd: ", String.valueOf(response.length()));
                                try {
                                    if(response.length()<=mFlashcardNumber) {
                                        mFlashcardNumber = 0;
                                    }
                                    jsonobject = response.getJSONObject(mFlashcardNumber);
                                    String icelandic = jsonobject.getString("icelandic");
                                    Log.e("Flashcard: ", icelandic);
                                    String english = jsonobject.getString("english");
                                    Log.e("Flashcard: ", english);
                                    mFlashcardID = jsonobject.getString("id");
                                    Log.e("id: ", mFlashcardID);
                                    TextView texti1 = (TextView) findViewById(R.id.textFront);
                                    texti1.setText(icelandic);
                                    TextView texti2 = (TextView) findViewById(R.id.textBack);
                                    texti2.setText(english);
                                    mFlashcard = findViewById(R.id.frameLayout);
                                    mFlashcard.setVisibility(View.VISIBLE);
                                    mNextFlashcard = findViewById(R.id.nextFlashcard);
                                    mNextFlashcard.setVisibility(View.VISIBLE);
                                    mDeleteFlashcard = findViewById(R.id.delete);
                                    mDeleteFlashcard.setVisibility(View.VISIBLE);
                                    mFlashcardNumber++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                mFlashcard = findViewById(R.id.frameLayout);
                                mFlashcard.setVisibility(View.INVISIBLE);
                                mNextFlashcard = findViewById(R.id.nextFlashcard);
                                mNextFlashcard.setVisibility(View.INVISIBLE);
                                mDeleteFlashcard = findViewById(R.id.delete);
                                mDeleteFlashcard.setVisibility(View.INVISIBLE);
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

    // delete flashcard
    private void deleteFlashcard() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

        long userID = sharedPreferences.getLong("userID", 0);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://icelandic-tutor.herokuapp.com/delflashcard?id=" + mFlashcardID;
        Toast.makeText(getApplicationContext(), "Flashcard deleted", Toast.LENGTH_SHORT).show();
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
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
    }






    // flip flashcards
    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
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
        Intent intent = new Intent(FlashcardActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

    // Go to dictionary view
    public void goToDictionarySelection(){
        Intent intent = new Intent(FlashcardActivity.this, DictionarySelectionActivity.class);
        startActivity(intent);
    }

    // Go to Location
    public void goToLocation() {
        Intent intent = new Intent(FlashcardActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    // Go to category selection
    public void goToGameselection(){
        Intent intent = new Intent(FlashcardActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    // Go to Articles
    public void goToArticleSelection() {
        Intent intent = new Intent(FlashcardActivity.this, ArticleSelectionActivity.class);
        startActivity(intent);
    }

    // Go to Flashcards
    public void goToFlashcards(){
        Intent intent = new Intent(FlashcardActivity.this, FlashcardActivity.class);
        intent.putExtra("number",0);
        startActivity(intent);
    }

    // Go to Main Page
    public void goToMain(){
        Intent intent = new Intent(FlashcardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
