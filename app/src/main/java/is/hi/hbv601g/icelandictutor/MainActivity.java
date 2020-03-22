package is.hi.hbv601g.icelandictutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void game(View view) {
        Intent intent = new Intent(MainActivity.this, GamecategoryActivity.class);
        startActivity(intent);
    }

    public void article(View view) {
        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        startActivity(intent);
    }
    public void flashcard(View view) {
        Intent intent = new Intent(MainActivity.this, FlashcardActivity.class);
        startActivity(intent);
    }
    public void location(View view) {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }
}
