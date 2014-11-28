package com.nethergrim.bashorg.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nethergrim.bashorg.model.ParsedQoutes;
import com.nethergrim.bashorg.model.Quote;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by nethergrim on 28.11.2014.
 */
public class GetPageAsyncTask extends AsyncTask<Void, Void, ParsedQoutes> {

    private EmptyCallback callback;
    private Context context;
    private int page = -1;
    private int targetPage;

    public GetPageAsyncTask(int targetPage, EmptyCallback callback, Context context) {
        this.callback = callback;
        this.context = context.getApplicationContext();
        this.targetPage = targetPage;
    }

    public static int getPageNumber(Document document) {
        Elements elements = document.select("input");
        for (Element element : elements) {
            if (element.attr("class").equals("page")) {
                String currentPage = element.attr("value");
                String maxPage = element.attr("max"); // TODO write max page in preference
                Log.e("log", "current page: " + currentPage + " max page: " + maxPage);
                return Integer.parseInt(currentPage);
            }
        }
        return -1;
    }

    public static List<Long> getIds(Document document) {
        List<Long> strings = new ArrayList<Long>();
        Elements numberElements = document.select("a");
        for (Element numberElement : numberElements) {
            if (numberElement.attr("class").equals("id")) {
                String text = numberElement.html();
                text = text.substring(1, text.length());
                strings.add(Long.parseLong(text));
            }
        }
        return strings;
    }

    public static List<String> getTexts(Document document) {
        List<String> texts = new ArrayList<String>();
        Elements divs = document.select("div");
        for (Element element : divs) {
            if (element.attr("class").equals("text")) {
                String text = element.html();
                texts.add(text);
            }
        }
        return texts;
    }

    public static List<String> getDates(Document document) {
        List<String> texts = new ArrayList<String>();
        Elements divs = document.select("span");
        for (Element element : divs) {
            if (element.attr("class").equals("date")) {
                String text = element.html();
                texts.add(text);
            }
        }
        return texts;
    }

    @Override
    protected ParsedQoutes doInBackground(Void... integers) {
        try {
            String data = Client.getPage(targetPage);
            if (data != null && data.length() > 0) {
                Document document = Jsoup.parse(data);
                final List<Long> numbers = getIds(document);
                final List<String> texts = getTexts(document);
                final List<String> dates = getDates(document);
                page = getPageNumber(document);// TODO write pageNumber to preferences
                ParsedQoutes parsedQoutes = new ParsedQoutes();
                parsedQoutes.setIds(numbers);
                parsedQoutes.setTexts(texts);
                parsedQoutes.setDates(dates);
                return parsedQoutes;
            } else {
                if (callback != null) {
                    callback.onCall(false, -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onCall(false, -1);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ParsedQoutes result) {
        super.onPostExecute(result);
        if (result != null) {
            final List<Long> numbers = result.getIds();
            final List<String> texts = result.getTexts();
            final List<String> dates = result.getDates();
            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();
            for (int i = 0; i < texts.size(); i++) {
                if (realm.where(Quote.class).equalTo("id", numbers.get(i)).findAll().size() > 0)
                    break;
                Quote quote = realm.createObject(Quote.class);
                quote.setDate(dates.get(i));
                quote.setId(numbers.get(i));
                quote.setText(texts.get(i));
            }
            realm.commitTransaction();
            callback.onCall(true, page);
        }

    }
}
