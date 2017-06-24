package com.example.ekta.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private ProgressBar mProgress;
    private EditText mSearch;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressId);
        mSearch = (EditText) findViewById(R.id.searchQuery);
        mTextView = (TextView)findViewById(R.id.displayJSON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            String s = mSearch.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();
        }

        return true;
    }

    class NetworkTask extends AsyncTask<URL, Void, String> {
        String query;

        NetworkTask(String s) {
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            URL url = NetworkUtils.makeURL(query, "stars");
            Log.d(TAG, "url: " + url.toString());
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgress.setVisibility(View.GONE);
            if (s == null) {
                mTextView.setText("Sorry, No text!");
            } else {
                mTextView.setText(s);
            }
        }
    }

}
