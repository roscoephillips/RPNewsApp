package com.example.android.rpnewsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<NewsArticle> fetchArticleData(String requestUrl) {

        //create URL object.
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "HTTP request experienced problems.", e);
        }

        List<NewsArticle> newsArticles = extractFeatureFromJson(jsonResponse);

        return newsArticles;
    }

    /**
     * Returns URL object from the given URL string.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL...", e);
        }
        return url;
    }

    //URL will make an HTTP request with a jsonResponse.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if null, return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem getting the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //returns list of objects from JSONResponse.
    private static List<NewsArticle> extractFeatureFromJson(String jsonRepsonse) {
        if (TextUtils.isEmpty(jsonRepsonse)) {
            return null;
        }

        //Creates an empty ArrayList that starts building new News items to the list.
        List<NewsArticle> newsArticles = new ArrayList<>();

        // makes an attempt to parse the JSON response string.
        try {

            JSONObject baseJSON = new JSONObject(jsonRepsonse);

            JSONObject responseObject = baseJSON.getJSONObject("response");

            JSONArray jsonArray = responseObject.getJSONArray("results");

            //For each article, keep making relevant objects in the array.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentArticle = jsonArray.getJSONObject(i);

                String articleTitle = currentArticle.getString("webTitle");

                String articleDate = currentArticle.getString("webPublicationDate");

                String articleUrl = currentArticle.getString("webUrl");

                JSONObject fields = currentArticle.getJSONObject("fields");

                String articleAuthor = fields.getString("byline");

                String articleSection = currentArticle.getString("sectionName");

                String articleThumb = fields.getString("thumbnail");

                NewsArticle article = new NewsArticle(articleTitle, articleDate, articleUrl, articleAuthor, articleSection, articleThumb);

                newsArticles.add(article);
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing results.", e);

        }
        return newsArticles;
    }
}
