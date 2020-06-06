package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

// TODO refactor name of this file
public class StoryBeginningPage extends AppCompatActivity {
    boolean IS_STORY_BEGINNING_PAGE;
    int ID_TO_LINK_THIS_PAGE_TO;
    int PAGE_ID;

    EditText storyTextEditText, choice1EditText, choice2EditText;
    Button createStoryButton, endStoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_beginning_page);

        IS_STORY_BEGINNING_PAGE = getIntent().getBooleanExtra(StoriesActivity.IS_STORY_BEGINNING_PAGE, false);
        ID_TO_LINK_THIS_PAGE_TO = getIntent().getIntExtra(StoriesActivity.STORY_ID, -1);

        findViewsById();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        createStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storyTextEditText.getText().toString().length() < 50) {
                    Toast.makeText(StoryBeginningPage.this, "Uma página deve ter no mínimo 50 caracteres", Toast.LENGTH_LONG).show();
                    return;
                }
                if(choice1EditText.getText().toString().length() == 0 || choice2EditText.getText().toString().length() == 0) {
                    Toast.makeText(StoryBeginningPage.this, "Escolhas não podem ficar vazias", Toast.LENGTH_LONG).show();
                    return;
                }
                createStoryButton.setClickable(false);
                storyTextEditText.setFocusable(false);
                choice1EditText.setFocusable(false);
                choice2EditText.setFocusable(false);
                createPage();
            }
        });

        endStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storyTextEditText.getText().toString().length() < 50) {
                    Toast.makeText(StoryBeginningPage.this, "Uma página deve ter no mínimo 50 caracteres", Toast.LENGTH_LONG).show();
                    return;
                }
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(StoryBeginningPage.this);
                View view = getLayoutInflater().inflate(R.layout.confirmation_popup_window, null);
                Button confirmButton = view.findViewById(R.id.confirm_popup_button);
                Button cancelButton = view.findViewById(R.id.cancel_popup_button);
                mBuilder.setView(view);
                final AlertDialog dialog = mBuilder.create();
                
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endStoryHere();
                        dialog.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                changeConfirmButtonToNewGameButton();
            }
        });
    }

    private void endStoryHere() {
        String storyText = String.valueOf(storyTextEditText.getText());

        String url = "https://decida-a-historia.herokuapp.com/page/add";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("story", storyText);
            jsonBody.put("button1", null);
            jsonBody.put("button2", null);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PAGE_ID = response.getInt("response");
                            linkThisPageToId();
                        } catch (JSONException e) {
                            Toast.makeText(StoryBeginningPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void createPage() {
        String storyText = String.valueOf(storyTextEditText.getText());
        String choice1Text = String.valueOf(choice1EditText.getText());
        String choice2Text = String.valueOf(choice2EditText.getText());

        String url = "https://decida-a-historia.herokuapp.com/page/buttons/add";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("story", storyText);
            jsonBody.put("button1", choice1Text);
            jsonBody.put("button2", choice2Text);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Create Page
                            PAGE_ID = response.getInt("response");
                            linkThisPageToId();
                        } catch (JSONException e) {
                            Toast.makeText(StoryBeginningPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void linkThisPageToId() {
        if(IS_STORY_BEGINNING_PAGE) addPageAsBeginningPageForStory();
        else linkButtonToThisPage();
    }

    private void addPageAsBeginningPageForStory() {
        String url = "https://decida-a-historia.herokuapp.com/story/update/" + ID_TO_LINK_THIS_PAGE_TO;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", "beginning_page");
            jsonBody.put("value", PAGE_ID);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        StoryDetailsActivity.setBeginningPageId(PAGE_ID);
                        Intent intent = new Intent(StoryBeginningPage.this, ChoicesActivity.class);
                        intent.putExtra(StoriesActivity.STORY_ID, PAGE_ID);
                        startActivity(intent);
                        intent.putExtra(StoriesActivity.STORY_ID, ID_TO_LINK_THIS_PAGE_TO);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, "7: ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void linkButtonToThisPage() {
        String url = "https://decida-a-historia.herokuapp.com/button/update/" + ID_TO_LINK_THIS_PAGE_TO;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", "linked_page");
            jsonBody.put("value", PAGE_ID);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(StoryBeginningPage.this, ChoicesActivity.class);
                        intent.putExtra(StoriesActivity.STORY_ID, PAGE_ID);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void findViewsById() {
        storyTextEditText = findViewById(R.id.story_text_edit_text);
        choice1EditText = findViewById(R.id.choice1_edit_text);
        choice2EditText = findViewById(R.id.choice2_edit_text);
        createStoryButton = findViewById(R.id.create_story_button);
        endStoryButton = findViewById(R.id.end_story_button);
    }
}
