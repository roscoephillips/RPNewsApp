package com.example.android.rpnewsapp;

import android.app.LoaderManager;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<NewsArticle>> {


    //Guardian API dataset URL.
    private static final String GUARDIAN_REQUEST_LINK =
            "https://content.guardianapis.com/search?q=vegetables&show-fields=all&api-key=3cbb42b8-efc7-4f89-9a52-f267e2f35bd0";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    //Loader ID for the news app.
    private static final int LOADER_ID = 1;

    private NewsArticleAdapter mAdapter;

    //Empty view if the list is empty.
    private View mNoNewsView;
    private TextView noNewsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Displays a toolbar programmatically.
        Toolbar toolbar = (Toolbar) findViewById(R.id.rpnews_toolbar);
        setSupportActionBar(toolbar);

        //References the ListView in the layout.
        ListView articleListView = (ListView) findViewById(R.id.list);

        mNoNewsView = (View) findViewById(R.id.no_connection_screen);
        noNewsText = (TextView) findViewById(R.id.no_news_text_view);

        //Creates a new adapter with empty list of articles as input.
        mAdapter = new NewsArticleAdapter(this, new ArrayList<NewsArticle>());

        //Sets the adapter on the ListView to populate the list.
        articleListView.setAdapter(mAdapter);

        //Adds an Intent/onclicklistener so the list items can be clicked to view the online articles.
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsArticle currentArticle = mAdapter.getItem(i);

                Uri newsUri = Uri.parse(currentArticle.getNewsUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webIntent);
            }
        });

        //References ConnectivityManager to check network connectivity.
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        //gets active network information.
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if network isn't null and the network info is connected, fetch!
        if (networkInfo != null && networkInfo.isConnected()) {

            //refers to loadermanager
            LoaderManager loaderManager = getLoaderManager();


            //Initializes loader.
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            //An error occurred.  This will remove the progressbar and display the nonewsview.
            View loadingProg = findViewById(R.id.loading_progress);
            loadingProg.setVisibility(View.GONE);
            //Displays the View NoNewsView.
            mNoNewsView.setVisibility(View.VISIBLE);
            noNewsText.setText(R.string.an_error_occured);

        }
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_REQUEST_LINK);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {
        //Hides loading indicator since the data has finished loading.
        View loadingProg = findViewById(R.id.loading_progress);
        loadingProg.setVisibility(View.GONE);

        mAdapter.clear();

        if (newsArticles != null && !newsArticles.isEmpty()) {
            mAdapter.addAll(newsArticles);
        } else {
            mNoNewsView.setVisibility(View.VISIBLE);
            noNewsText.setText(R.string.nothing_could_load);
        }
    }

    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        //Loader's reset, clear existing data.
        mAdapter.clear();
    }
}
