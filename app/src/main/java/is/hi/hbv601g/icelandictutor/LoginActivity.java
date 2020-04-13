package is.hi.hbv601g.icelandictutor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mUnameView;
    private EditText mPasswordView;

    private String loginURL = "https://icelandic-tutor.herokuapp.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrieveFields();

        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClick();
            }
        });
        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    // Send user to main activity page
    public void logIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Send user to registration page
    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    // Retrieve the text that user entered in login fields
    private void retrieveFields() {
        mUnameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
    }

    // Check whether username/password combo exist in our database, if successful user is sent to main
    // page. If user does not exist an error message is displayed.
    private void loginClick() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String usernameInput = mUnameView.getText().toString();
        String passwordInput = mPasswordView.getText().toString();
        Map<String, String> params = new HashMap();
        params.put("userName", usernameInput);
        params.put("password", passwordInput);
        final JSONObject jsonUser = new JSONObject(params); // Create a JSONObject using info that user entered

            /* Create an object request to database to check whether user exists */
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, loginURL, jsonUser,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            Log.e("Rest response breyttur", response.toString());
                            Context context = getApplicationContext();
                            SharedPreferences settings = context.getSharedPreferences("currUser", Context.MODE_PRIVATE);

                            try {
                                settings.edit().putLong("userID", response.getLong("id")).apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            logIn();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Login failed, incorrect username or password", Toast.LENGTH_SHORT).show();
                            Log.e("Rest login error", error.toString());
                        }
                    });
            requestQueue.add(objectRequest);
    }
}
