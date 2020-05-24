package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StoriesActivity extends AppCompatActivity {

    TextView actionGenreTextView, adventureGenreTextView, comedyGenreTextView, dramaGenreTextView,
            fantasyGenreTextView, horrorGenreTextView, isekaiGenreTextView, misteryGenreTextView,
            romanceGenreTextView, scienceFictionGenreTextView, otherGenreTextView;
    Story[] storiesArray = new Story[11];
    public static String STORY_ID = "com.femeuc.decidaahistoria.STORY_ID";
    public static String IS_STORY_CREATED = "com.femeuc.decidaahistoria.IS_STORY_CREATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        findViewsById();
        fetchDataFromServer();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        actionGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[0].getId());
                if(storiesArray[0] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        adventureGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[1].getId());
                if(storiesArray[1] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        comedyGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[2].getId());
                if(storiesArray[2] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        dramaGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[3].getId());
                if(storiesArray[3] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        fantasyGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[4].getId());
                if(storiesArray[4] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        horrorGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[5].getId());
                if(storiesArray[5] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        isekaiGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[6].getId());
                if(storiesArray[6] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        misteryGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[7].getId());
                if(storiesArray[7] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        romanceGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[8].getId());
                if(storiesArray[8] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        scienceFictionGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[9].getId());
                if(storiesArray[9] != null) intent.putExtra(IS_STORY_CREATED, true);
                else intent.putExtra(IS_STORY_CREATED, false);
                startActivity(intent);
            }
        });
        otherGenreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryDetailsActivity.class);
                intent.putExtra(STORY_ID, storiesArray[10].getId());
                if(storiesArray[10] != null) intent.putExtra(IS_STORY_CREATED, true);
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
//        RequestQueue requestQueue;
//
//        // Instantiate the cache
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//        // Instantiate the RequestQueue with the cache and network.
//        requestQueue = new RequestQueue(cache, network);
//
//        // Start the queue
//        requestQueue.start();
        String url = "https://decida-a-historia.herokuapp.com/story";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("response");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject storyJsonObject = jsonArray.getJSONObject(i);
                                int storyId = StoryGenre.getGenreId(storyJsonObject.getString("genre"));
                                storiesArray[storyId] = new Story();
                                storiesArray[storyId].setId(storyJsonObject.getInt("id"));
                                storiesArray[storyId].setGenre(storyJsonObject.getString("genre"));
                                storiesArray[storyId].setTitle(storyJsonObject.getString("title"));
                                storiesArray[storyId].setDescription(storyJsonObject.getString("description"));
                                storiesArray[storyId].setBeginning_page_id(storyJsonObject.getInt("beginning_page"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateLayoutWithServerData();
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
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void updateLayoutWithServerData() {
        for(int i = 0; i < storiesArray.length; i++) {
            if(storiesArray[i] != null) { updateTitleWithServerData(i); continue; }
            setTitleTo(i, "{ vazio }");
        }
    }

    private void updateTitleWithServerData(int i) {
        switch (i) {
            case 0:
                actionGenreTextView.setText(storiesArray[0].getTitle());
                break;
            case 1:
                adventureGenreTextView.setText(storiesArray[1].getTitle());
                break;
            case 2:
                comedyGenreTextView.setText(storiesArray[2].getTitle());
                break;
            case 3:
                dramaGenreTextView.setText(storiesArray[3].getTitle());
                break;
            case 4:
                fantasyGenreTextView.setText(storiesArray[4].getTitle());
                break;
            case 5:
                horrorGenreTextView.setText(storiesArray[5].getTitle());
                break;
            case 6:
                isekaiGenreTextView.setText(storiesArray[6].getTitle());
                break;
            case 7:
                misteryGenreTextView.setText(storiesArray[7].getTitle());
                break;
            case 8:
                romanceGenreTextView.setText(storiesArray[8].getTitle());
                break;
            case 9:
                scienceFictionGenreTextView.setText(storiesArray[9].getTitle());
                break;
            case 10:
                otherGenreTextView.setText(storiesArray[10].getTitle());
                break;
        }
    }

    private void setTitleTo(int i, String newTitle) {
        switch (i) {
            case 0:
                actionGenreTextView.setText(newTitle);
                break;
            case 1:
                adventureGenreTextView.setText(newTitle);
                break;
            case 2:
                comedyGenreTextView.setText(newTitle);
                break;
            case 3:
                dramaGenreTextView.setText(newTitle);
                break;
            case 4:
                fantasyGenreTextView.setText(newTitle);
                break;
            case 5:
                horrorGenreTextView.setText(newTitle);
                break;
            case 6:
                isekaiGenreTextView.setText(newTitle);
                break;
            case 7:
                misteryGenreTextView.setText(newTitle);
                break;
            case 8:
                romanceGenreTextView.setText(newTitle);
                break;
            case 9:
                scienceFictionGenreTextView.setText(newTitle);
                break;
            case 10:
                otherGenreTextView.setText(newTitle);
                break;
        }
    }

}
