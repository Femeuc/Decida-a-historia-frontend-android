package com.femeuc.decidaahistria;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}






////        final TextView testTextview = findViewById(R.id.testTextView);
//
//    RequestQueue requestQueue;
//
//    // Instantiate the cache
//    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//    // Set up the network to use HttpURLConnection as the HTTP client.
//    Network network = new BasicNetwork(new HurlStack());
//
//// Instantiate the RequestQueue with the cache and network.
//                requestQueue = new RequestQueue(cache, network);
//
//                        // Start the queue
//                        requestQueue.start();
//
//                        String url = "https://decida-a-historia.herokuapp.com/users/1";
//
//                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//@Override
//public void onResponse(JSONObject response) {
////                        testTextview.setText(response.toString());
//        }
//        }, new Response.ErrorListener() {
//
//@Override
//public void onErrorResponse(VolleyError error) {
//        // TODO: Handle error
////                        testTextview.setText("Error: " + error.toString());
//        }
//        });
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);