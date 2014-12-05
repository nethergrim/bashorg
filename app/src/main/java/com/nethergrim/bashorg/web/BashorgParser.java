package com.nethergrim.bashorg.web;

import android.content.Context;
import android.util.Log;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.model.Quote;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class BashorgParser {

    public static int parsePage(final String pageNumber, final Context context) {
        int pn = -1;
        try {
            Document document = Jsoup.connect(Constants.BASE_URL + pageNumber).get();
            List<Long> numbers = getIds(document);
            List<String> texts = getTexts(document);
            List<String> dates = getDates(document);
            pn = getPageNumber(document);
            Realm realm = Realm.getInstance(context);
            realm.setAutoRefresh(false);
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
            document = null;
            numbers = null;
            texts = null;
            dates = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pn;
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
                text = text.replace("<br>", "");
                texts.add(StringEscapeUtils.unescapeHtml4(text));
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
}
