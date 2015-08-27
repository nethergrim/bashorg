package com.nethergrim.bashorg.web;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author andrej on 27.08.15.
 */
public class GistParser {

    public static final String SHOW_ADS_GIST_URL
            = "https://gist.github.com/nethergrim/bcb5d87aa78a67837766";
    public static final String GIST_URL = "https://gist.github.com";
    public static final String TAG = GistParser.class.getSimpleName();

    public static String getMyRawUrl(String githubGistURl) {
        try {
            Document d = Jsoup.connect(githubGistURl).get();
            Elements a = d.select("a");
            for (int i = 0, size = a.size(); i < size; i++) {
                if (a.get(i).attr("class").equals("btn btn-sm ") || a.get(i).html().equals("raw")) {
                    return a.get(i).attr("href");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return null;
    }

    public static String getGistBody(String rawUrl) {
        if (rawUrl == null) {
            return null;
        }
        rawUrl = GIST_URL + rawUrl;
        try {
            Document d = Jsoup.connect(rawUrl).get();
            return d.body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
