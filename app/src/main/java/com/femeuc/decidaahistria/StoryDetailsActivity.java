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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoryDetailsActivity extends AppCompatActivity {
    int storyId;
    boolean isCreated;
    Story story;
    boolean isStoryCreated;
    
    TextView storyNameTextView, storyGenreTextView;
    EditText storyDescriptionEditText;
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

        storyDescriptionEditText.setEnabled(false);
        storyDescriptionEditText.setBackground(null);
        storyDescriptionEditText.setFocusableInTouchMode(false);

        if(!isCreated) { setStoryNotCreatedYet(); return;}
        fetchStoryDetails();
    }

    private void setStoryNotCreatedYet() {
//        storyGenreTextView.setText(StoryGenre.getById(storyId).toString().toUpperCase());
        playCreateButton.setText(R.string.create);
        storyDescriptionEditText.setEnabled(true);
    }

    private void fetchStoryDetails() {
        String url = "https://decida-a-historia.herokuapp.com/story/" + storyId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONArray("response").getJSONObject(0);
                            story = new Story();
                            story.setId(jsonObject.getInt("id"));
                            story.setGenre(jsonObject.getString("genre"));
                            story.setTitle(jsonObject.getString("title"));
                            story.setDescription(jsonObject.getString("description"));
                            story.setBeginning_page_id(jsonObject.getInt("beginning_page"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateLayoutWithFetchedData();
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

    private void updateLayoutWithFetchedData() {
        storyNameTextView.setText(story.getTitle());
        storyGenreTextView.setText(story.getGenre().toUpperCase());
        storyDescriptionEditText.setText(story.getDescription());
        storyDescriptionEditText.setFocusableInTouchMode(true);
        isStoryCreated = true;

    }

    private void findViewsById() {
        storyNameTextView = findViewById(R.id.story_name_text_view);
        storyGenreTextView = findViewById(R.id.story_genre_text_view);
        storyDescriptionEditText = findViewById(R.id.story_description_text_view);
        playCreateButton = findViewById(R.id.playCreate_button);
    }

    private void setOnClickListeners() {
        playCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoryCreated) {
                    //something
                } else {
                    // something
                    Toast.makeText(StoryDetailsActivity.this, "História criada com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
