package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ChoicesActivity extends AppCompatActivity {
    int ID_OF_CURRENT_PAGE;
    JSONObject currentPageJsonObject;
    int ID_OF_CURRENT_BUTTON_1;
    int ID_OF_CURRENT_BUTTON_2;

    TextView storyTextView;
    Button choice1Button, choice2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        findViewsById();

        ID_OF_CURRENT_PAGE = getIntent().getIntExtra(StoriesActivity.STORY_ID, -1);
        loadThisPage();
    }

    private void loadThisPage() {
        String url = "https://decida-a-historia.herokuapp.com/page/" + ID_OF_CURRENT_PAGE;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentPageJsonObject = response.getJSONArray("response").getJSONObject(0);
                            storyTextView.setText(currentPageJsonObject.getString("story"));
                            setChoicesButtonsText();
                        } catch (JSONException e) {
                            Toast.makeText(ChoicesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(ChoicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void setChoicesButtonsText() throws JSONException {
        final int button1Id = currentPageJsonObject.getInt("button1");
        ID_OF_CURRENT_BUTTON_1 = button1Id;
        String url = "https://decida-a-historia.herokuapp.com/button/" + button1Id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            choice1Button.setText(response.getJSONArray("response").getJSONObject(0).getString("name"));
                            setChoiceButton2();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(ChoicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void setChoiceButton2() throws JSONException {
        final int button2Id = currentPageJsonObject.getInt("button2");
        ID_OF_CURRENT_BUTTON_2 = button2Id;
        String url = "https://decida-a-historia.herokuapp.com/button/" + button2Id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            choice2Button.setText(response.getJSONArray("response").getJSONObject(0).getString("name"));
                            setOnClickListeners();
                        } catch (JSONException e) {
                            Toast.makeText(ChoicesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(ChoicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void setOnClickListeners() {
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://decida-a-historia.herokuapp.com/button/" + ID_OF_CURRENT_BUTTON_1;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(!response.getJSONArray("response").getJSONObject(0).isNull("linked_page")) getNextPage(response.getJSONArray("response").getJSONObject(0).getInt("linked_page"));
                                    else createNextPage(ID_OF_CURRENT_BUTTON_1);
                                } catch (JSONException e) {
                                    Toast.makeText(ChoicesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                //  testTextview.setText("Error: " + error.toString());
                                Toast.makeText(ChoicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
            }
        });
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://decida-a-historia.herokuapp.com/button/" + ID_OF_CURRENT_BUTTON_2;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(!response.getJSONArray("response").getJSONObject(0).isNull("linked_page")) getNextPage(response.getJSONArray("response").getJSONObject(0).getInt("linked_page"));
                                    else createNextPage(ID_OF_CURRENT_BUTTON_2);
                                } catch (JSONException e) {
                                    Toast.makeText(ChoicesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                //  testTextview.setText("Error: " + error.toString());
                                Toast.makeText(ChoicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
            }
        });
    }

    private void createNextPage(int buttonId) {
        Intent intent = new Intent(ChoicesActivity.this, StoryBeginningPage.class);
        intent.putExtra(StoriesActivity.IS_STORY_BEGINNING_PAGE, false);
        intent.putExtra(StoriesActivity.STORY_ID, buttonId);
        startActivity(intent);
        finish();
    }

    private void getNextPage(int linked_page) {
        Intent intent = new Intent(ChoicesActivity.this, ChoicesActivity.class);
        intent.putExtra(StoriesActivity.STORY_ID, linked_page);
        startActivity(intent);
        finish();
    }

    private void findViewsById() {
        storyTextView = findViewById(R.id.story_text_text_view);
        choice1Button = findViewById(R.id.choice_1_button);
        choice2Button = findViewById(R.id.choice_2_button);
    }
}
