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

// TODO refactor name of this file
public class StoryBeginningPage extends AppCompatActivity {
    boolean IS_STORY_BEGINNING_PAGE;
    int ID_TO_LINK_THIS_PAGE_TO;
    int CHOICE_1_BUTTON_ID;
    int CHOICE_2_BUTTON_ID;
    int PAGE_ID;

    EditText storyTextEditText, choice1EditText, choice2EditText;
    Button createStoryButton;

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
                if(storyTextEditText.getText().toString().length() < 400) {
                    Toast.makeText(StoryBeginningPage.this, "Uma história deve ter no mínimo 400 caracteres", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(StoryBeginningPage.this, "7: ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
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

    private void createPage() {
        String choice1Text = String.valueOf(choice1EditText.getText());

        // Create first choice
        String url = "https://decida-a-historia.herokuapp.com/button/add";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", choice1Text);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CHOICE_1_BUTTON_ID = response.getInt("response");
                            // Create second choice
                            createChoiceButton2();
                        } catch (JSONException e) {
                            Toast.makeText(StoryBeginningPage.this, "1: ", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, "6: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void createChoiceButton2() {
        final String choice2Text = String.valueOf(choice2EditText.getText());
        // Create second choice
        String url = "https://decida-a-historia.herokuapp.com/button/add";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", choice2Text);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CHOICE_2_BUTTON_ID = response.getInt("response");
                            // Create Page
                            finallyCreatePage();
                        } catch (JSONException e) {
                            Toast.makeText(StoryBeginningPage.this, "2: ", Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, "5: ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void finallyCreatePage() {
        String storyText = String.valueOf(storyTextEditText.getText());
        // Create second choice
        String url = "https://decida-a-historia.herokuapp.com/page/add";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("story", storyText);
            jsonBody.put("button1", CHOICE_1_BUTTON_ID);
            jsonBody.put("button2", CHOICE_2_BUTTON_ID);
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
                            Toast.makeText(StoryBeginningPage.this, "3: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryBeginningPage.this, "4: ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void linkThisPageToId() {
        if(IS_STORY_BEGINNING_PAGE) addPageAsBeginningPageForStory();
        else linkButtonToThisPage();
    }


    private void findViewsById() {
        storyTextEditText = findViewById(R.id.story_text_edit_text);
        choice1EditText = findViewById(R.id.choice1_edit_text);
        choice2EditText = findViewById(R.id.choice2_edit_text);
        createStoryButton = findViewById(R.id.create_story_button);
    }
}
