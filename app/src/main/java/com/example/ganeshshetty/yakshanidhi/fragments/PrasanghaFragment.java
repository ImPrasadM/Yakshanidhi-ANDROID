package com.example.ganeshshetty.yakshanidhi.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.adapters.PrasanghaAdapter;
import com.example.ganeshshetty.yakshanidhi.details.PrasanghaDetailActivity;
import com.example.ganeshshetty.yakshanidhi.model.Prasangha_class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrasanghaFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";
    private List<Prasangha_class> prasangha_classList;
    private RecyclerView mRecyclerView;
    private PrasanghaAdapter adapter;
    private ProgressBar progressBar;
    public PrasanghaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prasangha, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);

        String url = getString(R.string.url) + "/api/v1/prasangha";
        new DownloadTask().execute(url);

        return view;
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            prasangha_classList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Prasangha_class item = new Prasangha_class();
                item.setName(post.optString("prasangha_name"));
                item.setAuthor(post.optString("prasangha_author"));
                item.setYear(post.optInt("prasangha_year"));
                prasangha_classList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new PrasanghaAdapter(getContext(), prasangha_classList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
