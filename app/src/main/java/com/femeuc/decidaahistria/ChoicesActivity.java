package com.femeuc.decidaahistria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
        decideHowToLoadThisPage();

        // if user is logged in
        if(MainActivity.USERNAME_VALUE != null) { loadSaveProgressButton(); }
    }

    private void decideHowToLoadThisPage() {
        String url = "https://decida-a-historia.herokuapp.com/page/" + ID_OF_CURRENT_PAGE;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentPageJsonObject = response.getJSONArray("response").getJSONObject(0);
                            if(currentPageJsonObject.isNull("button1") || currentPageJsonObject.isNull("button2")) {
                                loadThisAsLastPage();
                            }
                            else loadThisPage();
                        } catch (JSONException e) {
                            Toast.makeText(ChoicesActivity.this, "2: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  testTextview.setText("Error: " + error.toString());
                        Toast.makeText(ChoicesActivity.this, "1: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        MyJsonRequest.createAndAddRequest(getCacheDir(), jsonObjectRequest, getApplicationContext());
    }

    private void loadThisAsLastPage() throws JSONException {
//        choice1Button.setText("FIM");
        ((ViewGroup) choice1Button.getParent()).removeView(choice1Button);
        ViewGroup layout = (ViewGroup) findViewById(R.id.choices_activity_linear_layout);
        TextView tv = new TextView(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText("FIM");
        tv.setTextColor(getResources().getColor(R.color.titles));
        tv.setPadding(10, 20, 10, 20);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv.setTypeface(null, Typeface.BOLD);
        layout.addView(tv, 1);

//        choice1Button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        storyTextView.setText(currentPageJsonObject.getString("story"));
        choice2Button.setText("Voltar pro começo");
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoicesActivity.this, ChoicesActivity.class);
                intent.putExtra(StoriesActivity.STORY_ID, StoryDetailsActivity.getBeginningPageId());
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadThisPage() {
        String url = "https://decida-a-historia.herokuapp.com/page/" + ID_OF_CURRENT_PAGE + "/buttons";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentPageJsonObject = response.getJSONArray("response").getJSONObject(0);
                            storyTextView.setText(currentPageJsonObject.getString("story"));
                            choice1Button.setText(currentPageJsonObject.getString("button_1"));
                            choice2Button.setText(currentPageJsonObject.getString("button_2"));
                            ID_OF_CURRENT_BUTTON_1 = currentPageJsonObject.getInt("btn1_id");
                            ID_OF_CURRENT_BUTTON_2 = currentPageJsonObject.getInt("btn2_id");
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

    private void loadSaveProgressButton() {
        ViewGroup layout = findViewById(R.id.choices_activity_linear_layout);
        Button btn = new Button(this);
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setText("SALVAR PROGRESSO");
//        btn.setTextColor(getResources().getColor(R.color.titles));
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        btn.setBackground(getDrawable(R.drawable.button_background_primary));

        float d = getResources().getDisplayMetrics().density;
        int thirtyDp = (int)(30 * d); // margin from dp to pixels
        int tenDp = (int)(10 * d);

        btn.setPadding(tenDp, tenDp, tenDp, tenDp);
        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
        marginParams.setMargins(thirtyDp, tenDp * 2, thirtyDp, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
        btn.setLayoutParams(layoutParams);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://decida-a-historia.herokuapp.com/users/update/" + MainActivity.USER_ID;
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", "story_checkpoint");
                    jsonBody.put("value", ID_OF_CURRENT_PAGE);
                } catch (JSONException e) {
                    Toast.makeText(ChoicesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(ChoicesActivity.this, "Progresso salvo aqui", Toast.LENGTH_SHORT).show();
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
        layout.addView(btn, layout.getChildCount());
    }

    private void findViewsById() {
        storyTextView = findViewById(R.id.story_text_text_view);
        choice1Button = findViewById(R.id.choice_1_button);
        choice2Button = findViewById(R.id.choice_2_button);
    }
}
