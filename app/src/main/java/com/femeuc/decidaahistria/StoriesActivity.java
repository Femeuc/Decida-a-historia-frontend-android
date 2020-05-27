package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoriesActivity extends AppCompatActivity {

    TextView actionGenreTextView, adventureGenreTextView, comedyGenreTextView, dramaGenreTextView,
            fantasyGenreTextView, horrorGenreTextView, isekaiGenreTextView, misteryGenreTextView,
            romanceGenreTextView, scienceFictionGenreTextView, otherGenreTextView;
    TextView[] textViews = new TextView[11];
    JSONObject[] jsonObjects = new JSONObject[11];
    int[] storiesIdArray = new int[11];
    JSONArray jsonArray;

    public static String STORY_ID = "com.femeuc.decidaahistoria.STORY_ID";
    public static String IS_STORY_CREATED = "com.femeuc.decidaahistoria.IS_STORY_CREATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        findViewsById();
        initTextViewArray();
        initStoriesIdArray();
        fetchDataFromServer();
    }

    private void initStoriesIdArray() {
        storiesIdArray[0] = 0;
        storiesIdArray[1] = 1;
        storiesIdArray[2] = 2;
        storiesIdArray[3] = 3;
        storiesIdArray[4] = 4;
        storiesIdArray[5] = 5;
        storiesIdArray[6] = 6;
        storiesIdArray[7] = 7;
        storiesIdArray[8] = 8;
        storiesIdArray[9] = 9;
        storiesIdArray[10] = 10;
    }

    private void initTextViewArray() {
        textViews[0] = actionGenreTextView;
        textViews[1] = adventureGenreTextView;
        textViews[2] = comedyGenreTextView;
        textViews[3] = dramaGenreTextView;
        textViews[4] = fantasyGenreTextView;
        textViews[5] = horrorGenreTextView;
        textViews[6] = isekaiGenreTextView;
        textViews[7] = misteryGenreTextView;
        textViews[8] = romanceGenreTextView;
        textViews[9] = scienceFictionGenreTextView;
        textViews[10] = otherGenreTextView;
    }

    private void setOnClickListeners() {
        actionGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[0]);
                if(jsonObjects[0] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        adventureGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[1]);
                if(jsonObjects[1] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        comedyGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[2]);
                if(jsonObjects[2] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        dramaGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[3]);
                if(jsonObjects[3] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        fantasyGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[4]);
                if(jsonObjects[4] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        horrorGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[5]);
                if(jsonObjects[5] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        isekaiGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[6]);
                if(jsonObjects[6] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        misteryGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[7]);
                if(jsonObjects[7] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        romanceGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[8]);
                if(jsonObjects[8] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        scienceFictionGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[9]);
                if(jsonObjects[9] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        otherGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesIdArray[10]);
                if(jsonObjects[10] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });

    }

    private void findViewsById() {
        actionGenreTextView = findViewById(R.id.action_genre_text_view);
        adventureGenreTextView = findViewById(R.id.adventure_genre_text_view);
        comedyGenreTextView = findViewById(R.id.comedy_genre_text_view);
        dramaGenreTextView = findViewById(R.id.drama_genre_text_view);
        fantasyGenreTextView = findViewById(R.id.fantasy_genre_text_view);
        horrorGenreTextView = findViewById(R.id.horror_genre_text_view);
        isekaiGenreTextView = findViewById(R.id.isekai_genre_text_view);
        misteryGenreTextView = findViewById(R.id.mistery_genre_text_view);
        romanceGenreTextView = findViewById(R.id.romance_genre_text_view);
        scienceFictionGenreTextView = findViewById(R.id.science_fiction_genre_text_view);
        otherGenreTextView = findViewById(R.id.other_genre_text_view);
    }

    private void fetchDataFromServer() {
        String url = "https://decida-a-historia.herokuapp.com/story";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArray = response.getJSONArray("response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateLayout();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(StoriesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void updateLayout() {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObjects[i] = jsonArray.getJSONObject(i);
                textViews[i].setText(jsonObjects[i].getString("title"));
                storiesIdArray[i] = jsonObjects[i].getInt("id");
            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        setOnClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTextViewArray();
        initStoriesIdArray();
        fetchDataFromServer();
    }
}
