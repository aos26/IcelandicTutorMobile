package is.hi.hbv601g.icelandictutor;

import android.content.Intent;
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
        Log.e("onCreate: test","testset" );
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

    public void logIn() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void retrieveFields() {
        mUnameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
    }

    private void loginClick() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String usernameInput = mUnameView.getText().toString();
        String passwordInput = mPasswordView.getText().toString();
        Log.e( "loginClick: ", usernameInput);
        Log.e( "loginClick: ", passwordInput);
        Map<String, String> params = new HashMap();
        params.put("userName", usernameInput);
        params.put("password", passwordInput);
        JSONObject jsonUser = new JSONObject(params);

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, loginURL, jsonUser,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            Log.e("Rest response", response.toString());
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

                /*
                {
                @Override
                protected Map<String, String> getParams()
                {
                    String usernameInput = mUnameView.getText().toString();
                    String passwordInput = mUnameView.getText().toString();
                    Map<String, String> jsonUser = new HashMap<String, String>();
                    jsonUser.put("userName", usernameInput);
                    jsonUser.put("password", passwordInput);
                    return jsonUser;
                }
            };*/
            requestQueue.add(objectRequest);

    }
}
