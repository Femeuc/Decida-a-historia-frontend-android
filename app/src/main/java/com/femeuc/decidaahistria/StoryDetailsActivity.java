package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class StoryDetailsActivity extends AppCompatActivity {
    int storyId;
    boolean isCreated;
    JSONObject jsonObject;
    
    TextView storyGenreTextView;
    EditText storyNameEditText, storyDescriptionEditText;
    Button playCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        
        Intent intent = getIntent();
        storyId = intent.getIntExtra(StoriesActivity.STORY_ID, -1);
        isCreated = intent.getBooleanExtra(StoriesActivity.IS_STORY_CREATED, false);

        findViewsById();
        setOnClickListeners();

        if(!isCreated) setStoryNotCreatedYet();
        else setStoryAlreadyCreated();

    }

    private void setStoryAlreadyCreated() {
        storyDescriptionEditText.setEnabled(false);
        storyDescriptionEditText.setFocusableInTouchMode(false);
        storyDescriptionEditText.setBackground(null);

        storyNameEditText.setEnabled(false);
        storyNameEditText.setFocusableInTouchMode(false);
        storyNameEditText.setBackground(null);

        fetchStoryDetails();
    }

    private void setStoryNotCreatedYet() {
        storyGenreTextView.setText(StoryGenre.getById(storyId).toString().toUpperCase());
        playCreateButton.setText(R.string.create);
    }

    private void fetchStoryDetails() {
        String url = "https://decida-a-historia.herokuapp.com/story/" + storyId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonObject = response.getJSONArray("response").getJSONObject(0);
                        } catch (JSONException e) {
                            Toast.makeText(StoryDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        updateLayoutWithServerData();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryDetailsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void updateLayoutWithServerData() {
        try {
            storyNameEditText.setText(jsonObject.getString("title"));
            storyGenreTextView.setText(StoryGenre.getById(jsonObject.getInt("genre") - 1).toString().toUpperCase());
            storyDescriptionEditText.setText(jsonObject.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViewsById() {
        storyNameEditText = findViewById(R.id.story_name_edit_text);
        storyGenreTextView = findViewById(R.id.story_genre_text_view);
        storyDescriptionEditText = findViewById(R.id.story_description_edit_text);
        playCreateButton = findViewById(R.id.playCreate_button);
    }

    private void setOnClickListeners() {
        playCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCreated) {
                    //
                } else {
                    createStoryAndSendToServer();
                    disableInteractiveViews();
                }
            }
        });
    }

    private void disableInteractiveViews() {
        storyDescriptionEditText.setEnabled(false);
        storyDescriptionEditText.setFocusableInTouchMode(false);
        storyDescriptionEditText.setBackground(null);
        storyNameEditText.setEnabled(false);
        storyNameEditText.setFocusableInTouchMode(false);
        storyNameEditText.setBackground(null);
        playCreateButton.setClickable(false);
    }

    private void createStoryAndSendToServer() {
        String title = String.valueOf(storyNameEditText.getText());
        int genre = StoryGenre.getGenreId(String.valueOf(storyGenreTextView.getText())) + 1;
        String description = String.valueOf(storyDescriptionEditText.getText());

        String url = "https://decida-a-historia.herokuapp.com/story/add";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("genre", genre);
            jsonBody.put("title", title);
            jsonBody.put("description", description);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(StoryDetailsActivity.this, String.valueOf(response.getInt("response")), Toast.LENGTH_SHORT).show();
                            recreateThisIntent(response.getInt("response"));
                        } catch (JSONException e) {
                            Toast.makeText(StoryDetailsActivity.this, "erro aqui", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoryDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void recreateThisIntent(int idOfNewStory) {
        Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
        intent.putExtra(StoriesActivity.STORY_ID, idOfNewStory);
        intent.putExtra(StoriesActivity.IS_STORY_CREATED, true);
        startActivity(intent);
        finish();
    }

}
