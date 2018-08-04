package com.example.android.rpnewsapp;

public class NewsArticle {

    /**
     * Title of article.
     */
    private String mNewsTitle;

    /**
     * Author, if any, of article.
     */
    private String mNewsAuthor;

    /**
     * Section of article.
     */
    private String mNewsSection;

    /**
     * When the article was written
     */
    private String mNewsTime;

    /**
     * Website URL of the article.
     */
    private String mNewsUrl;

    /**
     * Website URL of the article.
     */
    private String mNewsThumb;

    /**
     * @param newsTitle  - the title of the article.
     * @param newsAuthor - the primary author of the article.
     * @param newsTime   - the time the article was written.
     * @param newsUrl    - the URL to view the article.
     */
    public NewsArticle(String newsTitle, String newsTime, String newsUrl, String newsAuthor, String newsSection, String newsThumb) {
        mNewsTitle = newsTitle;
        mNewsTime = newsTime;
        mNewsUrl = newsUrl;
        mNewsAuthor = newsAuthor;
        mNewsSection = newsSection;
        mNewsThumb = newsThumb;
    }

    /**
     * getter method for news title
     */
    public String getNewsTitle() {
        return mNewsTitle;
    }

    /**
     * getter method for news author
     */
    public String getNewsAuthor() {
        return mNewsAuthor;
    }

    /**
     * getter method for news author
     */
    public String getNewsSection() {
        return mNewsSection;
    }

    /**
     * getter method for news preview
     */
    public String getNewsTime() {
        return mNewsTime;
    }

    /**
     * getter method for news preview
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }

    /**
     * getter method for news image.
     */
    public String getNewsThumb() {
        return mNewsThumb;
    }


}
