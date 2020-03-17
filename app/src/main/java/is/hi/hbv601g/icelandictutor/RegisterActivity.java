package is.hi.hbv601g.icelandictutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button mSubmitButton;
    private Button mGoToLoginButton;

    private EditText mNameView;
    private EditText mUnameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private boolean exists = false;

    private String registerURL = "https://icelandic-tutor.herokuapp.com/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        retrieveFields();
        mSubmitButton = findViewById(R.id.submitButton);
        mGoToLoginButton = findViewById(R.id.goToLoginButton);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInfo();
            }
        });
        mGoToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void retrieveFields() {
        mNameView = findViewById(R.id.name);
        mUnameView = findViewById(R.id.username);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
    }

    private void goToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private Boolean checkExisting(final String username) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String usersURL = "https://icelandic-tutor.herokuapp.com/users";
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                usersURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                                String uName = jsonobject.getString("userName");
                                if (username.equals(uName)) {
                                    exists = true;
                                }
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
        return exists;
    }


    private void submitInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String usernameInput = mUnameView.getText().toString();
        String passwordInput = mPasswordView.getText().toString();
        String nameInput = mNameView.getText().toString();
        String emailInput = mEmailView.getText().toString();
        if(usernameInput.equals("") || passwordInput.equals("") || nameInput.equals("") || emailInput.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean checkResult = checkExisting(usernameInput);
        if(checkResult) {
            Toast.makeText(getApplicationContext(), "Username is taken", Toast.LENGTH_SHORT).show();
            return;
        }
        String scoreDefault = "0";

        Log.e( "loginClick: ", usernameInput);
        Log.e( "loginClick: ", passwordInput);
        Map<String, String> params = new HashMap();
        params.put("userName", usernameInput);
        params.put("password", passwordInput);
        params.put("name", nameInput);
        params.put("email", emailInput);
        params.put("score", scoreDefault);
        JSONObject jsonUser = new JSONObject(params);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, registerURL, jsonUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                        Log.e("Rest response", response.toString());
                        goToLogin();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                        Log.e("Rest login error", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }
}
