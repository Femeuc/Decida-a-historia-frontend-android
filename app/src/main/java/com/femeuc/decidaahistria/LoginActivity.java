package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findsViewById();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().length() < 1 || passwordEditText.getText().toString().length() < 1) { Toast.makeText(LoginActivity.this, "Nenhum campo pode ficar vazio", Toast.LENGTH_SHORT).show(); return; }
                tryToLogin();
            }
        });
    }

    private void tryToLogin() {
        String url = "https://decida-a-historia.herokuapp.com/users/where?username=" + usernameEditText.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String password = response.getJSONArray("response").getJSONObject(0).getString("password");
                            if(response.getJSONArray("response").length() < 1 || !password.equals(passwordEditText.getText().toString())) {
                                Toast.makeText(LoginActivity.this, "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(MainActivity.USERNAME_NAME, usernameEditText.getText().toString());
                            editor.apply();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void findsViewById() {
        usernameEditText = findViewById(R.id.username_edit_text_login);
        passwordEditText = findViewById(R.id.password_edit_text_login);
        loginButton = findViewById(R.id.login_button_login);
    }
}
