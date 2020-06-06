package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button loginButton, playButton, signupButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME_NAME = "username";
    public static String USERNAME_VALUE = null;
    public static int STORY_CHECKPOINT = -1;
    public static int USER_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setOnClickListeners();

        USERNAME_VALUE = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getString(USERNAME_NAME, null);
        if(USERNAME_VALUE != null) loadLayoutLoggedIn();
    }

    private void findViewsById() {
        loginButton = findViewById(R.id.login_button);
        playButton = findViewById(R.id.play_button);
        signupButton = findViewById(R.id.signup_button);
    }

    private void setOnClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(MainActivity.this, LoginActivity.class)); }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(MainActivity.this, StoriesActivity.class)); }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(MainActivity.this, SignupActivity.class)); }
        });
    }

    private void loadLayoutLoggedIn() {
        String url = "https://decida-a-historia.herokuapp.com/users/where?username=" + USERNAME_VALUE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject userObject = response.getJSONArray("response").getJSONObject(0);
                            if(!userObject.isNull("story_checkpoint"))
                                STORY_CHECKPOINT = userObject.getInt("story_checkpoint");
                            USER_ID = userObject.getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());

        // Turn login button into Continue button
        loginButton.setText("Continuar");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(STORY_CHECKPOINT == -1) { Toast.makeText(MainActivity.this, "Você ainda não tem nenhuma história para continuar", Toast.LENGTH_SHORT).show(); return; }
                Intent intent = new Intent(MainActivity.this, ChoicesActivity.class);
                intent.putExtra(StoriesActivity.STORY_ID, STORY_CHECKPOINT);
                startActivity(intent);
            }
        });

        // Turn signup button into logout button
        signupButton.setText("Sair");
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_NAME, null);
                editor.apply();
                recreate();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        USERNAME_VALUE = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getString(USERNAME_NAME, null);
        if(USERNAME_VALUE != null) loadLayoutLoggedIn();
    }
}
