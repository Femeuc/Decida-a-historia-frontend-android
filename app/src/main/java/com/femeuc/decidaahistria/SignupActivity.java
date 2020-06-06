package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {
    Button registerButton;
    EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewsById();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
                if(usernameEditText.getText().toString().length() < 1) { Toast.makeText(SignupActivity.this, "Nome de usuário não pode ficar vazio", Toast.LENGTH_SHORT).show(); return;}
                if(passwordEditText.getText().toString().length() < 1) { Toast.makeText(SignupActivity.this, "Senha não pode ficar vazia", Toast.LENGTH_SHORT).show(); return;}
                // If chosen username is already taken, it fails
                tryToAddUser();
            }
        });
    }

    private void tryToAddUser() {
        String url = "https://decida-a-historia.herokuapp.com/users/add";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", usernameEditText.getText().toString());
            jsonBody.put("password", passwordEditText.getText().toString());
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.isNull("response")) { Toast.makeText(SignupActivity.this, "Nome de usuário indisponível, tente outro", Toast.LENGTH_SHORT).show(); return; }
                        // else
                        Toast.makeText(SignupActivity.this, "SUCESS!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void findViewsById() {
        registerButton = findViewById(R.id.register_button);
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
    }
}
