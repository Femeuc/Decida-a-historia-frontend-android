package com.femeuc.decidaahistria;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.File;

public class MyJsonRequest extends AppCompatActivity {
    public static void createAndAddRequest(File cacheDir, JsonObjectRequest jsonObjectRequest, Context applicationContext) {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        RequestQueue requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(applicationContext).addToRequestQueue(jsonObjectRequest);
    }

}
