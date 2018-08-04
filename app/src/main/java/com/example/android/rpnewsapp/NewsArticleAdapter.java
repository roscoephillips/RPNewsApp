package com.example.android.rpnewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    public NewsArticleAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    @Override
    public View getView(int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        NewsArticle currentArticle = getItem(position);

        // Finds the TextView with the view ID article_title, gets the String newsTitle and sets it.
        TextView titleView = (TextView) listItemView.findViewById(R.id.article_title);
        titleView.setText(currentArticle.getNewsTitle());

        // Finds the TextView article_date, gets the string formattedDate and sets it.
        TextView dateView = (TextView) listItemView.findViewById(R.id.article_date);
        String[] splitDate = currentArticle.getNewsTime().split("T");
        dateView.setText(splitDate[0]);

        // Finds the TextView with the view ID article_author.
        TextView authorView = (TextView) listItemView.findViewById(R.id.article_author);
        authorView.setText(currentArticle.getNewsAuthor());

        // Finds the TextView with the view ID article_section.
        TextView sectionView = (TextView) listItemView.findViewById(R.id.article_section);
        sectionView.setText(currentArticle.getNewsSection());

        //Finds the ImageView to make a small preview thumbnail.
        ImageView thumbView = (ImageView) listItemView.findViewById(R.id.article_image);
        Picasso.get().load(currentArticle.getNewsThumb()).into(thumbView);

        return listItemView;
    }



}
