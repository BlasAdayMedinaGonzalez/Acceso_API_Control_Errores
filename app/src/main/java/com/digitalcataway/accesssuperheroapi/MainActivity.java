package com.digitalcataway.accesssuperheroapi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcataway.accesssuperheroapi.utils.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText searchBox;
    TextView urlDisplay;
    TextView searchResults;
    TextView errorDisplay;

    ProgressBar requestProgress;

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            requestProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String gitHubSearchResults = null;

            try {
                gitHubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            requestProgress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")){
                showJsonData();
                searchResults.setText(s);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.launch) {

            URL apiURL = null;
            try {
                apiURL = NetworkUtils.buildUrl(searchBox.getText().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            urlDisplay.setText(apiURL.toString());

            new GithubQueryTask().execute(apiURL);

        }
        if (itemId == R.id.clear){
            urlDisplay.setText("");
            searchResults.setText("");
        }
        return true;
    }

    private void showJsonData() {
        errorDisplay.setVisibility(View.INVISIBLE);
        searchResults.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        searchResults.setVisibility(View.INVISIBLE);
        errorDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = (EditText) findViewById(R.id.search_box);
        urlDisplay = (TextView) findViewById(R.id.url_display);
        searchResults = (TextView) findViewById(R.id.api_results);
        errorDisplay = (TextView) findViewById(R.id.error_display);

        requestProgress = (ProgressBar) findViewById(R.id.request_progress);
    }
}