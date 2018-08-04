package com.example.android.rpnewsapp;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsArticle>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    //Start loading; forces loading.
    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v(NewsLoader.LOG_TAG, "Start loading!");
    }

    //Loader is loading in background.
    @Override
    public List<NewsArticle> loadInBackground() {
        Log.v(NewsLoader.LOG_TAG, "Get a load of that loading.");

        if (mUrl == null) {
            return null;
        }
        //List is being fetched.
        List<NewsArticle> newsArticles = QueryUtils.fetchArticleData(mUrl);
        Log.v(NewsLoader.LOG_TAG, "Fetch!");

        return newsArticles;
    }
}

