package com.nethergrim.bashorg.web;

import android.support.annotation.NonNull;
import android.util.Log;
import com.nethergrim.bashorg.model.Abyss;
import io.realm.Realm;
import io.realm.RealmResults;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssParser {

    public static final String INPUT = "input";
    public static final String CLASS = "class";
    public static final String PAGE = "page";
    public static final String VALUE = "value";
    public static final String MAX = "max";
    public static final String A = "a";
    public static final String ID = "id";
    public static final String DIV = "div";
    public static final String TEXT = "text";
    public static final String BR = "<br>";
    public static final String EMPTY_STRING = "";
    public static final String SPAN = "span";
    public static final String DATE = "date";
    public static final String RATING = "rating";
    public static final String THREE_DOTS = "...";
    public static final String QUESTION = "?";
    public static final String URL_ABYSS_BEST = "http://bash.im/abyssbest/";
    public static final String FIRST_PAGE = "lol";

    public static boolean parsePage(@NonNull String pageDate) {
        String finalUrl;

        if (pageDate.equals(FIRST_PAGE)) {
            finalUrl = URL_ABYSS_BEST;
        } else {
            finalUrl = URL_ABYSS_BEST + pageDate;
        }

        try {
            Document d = Jsoup.connect(finalUrl).get();
            parseDocument(d);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "error during parsing abyss page: " + pageDate + "\n" + e.getMessage());
        }
        return false;
    }

    private static void parseDocument(@NonNull Document d) {
        final String datePage = getDatePage(d);
        final String nextDatePage = getNextDatePage(d);
        final List<String> bodies = getBodies(d);
        final List<String> dates = getDates(d);
        final List<String> ids = getIds(d);
        Realm realm = Realm.getDefaultInstance();
        Log.e("TAG", "nextpagedate: " + nextDatePage);
        if (bodies != null && dates != null && ids != null && bodies.size() == dates.size() && bodies.size() == ids.size()) {
            for (int i = 0, size = ids.size(); i < size; i++) {
                final int finalI = i;

                RealmResults<Abyss> results = realm.where(Abyss.class).equalTo("id", ids.get(i)).findAll();
                if (results != null && !results.isEmpty()) {
                    continue;
                }
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Abyss abyss = realm.createObject(Abyss.class);
                        abyss.setDatePage(datePage);
                        abyss.setNextPageDate(nextDatePage);
                        abyss.setId(ids.get(finalI));
                        abyss.setDate(dates.get(finalI));
                        abyss.setBody(bodies.get(finalI));
                    }
                });
            }
        } else {
            Log.e("TAG", "data is inconsistent");
        }
    }

    private static List<String> getBodies(Document document) {
        List<String> result = new ArrayList<>(100);
        Elements divs = document.select(DIV);
        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(CLASS).equals(TEXT)) {
                String text = element.html();
                text = text.replace(BR, EMPTY_STRING);
                text = StringEscapeUtils.unescapeHtml4(text);
                result.add(text);
            }
        }
        return result;
    }

    private static List<String> getDates(Document d) {
        List<String> result = new ArrayList<>(100);
        Elements divs = d.select(SPAN);
        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(CLASS).equals(DATE)) {
                String text = element.html();
                result.add(text);
            }
        }
        return result;
    }

    private static List<String> getIds(Document d) {
        List<String> result = new ArrayList<>(100);
        Elements divs = d.select(SPAN);
        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(CLASS).equals(ID)) {
                String text = element.html();
                result.add(text);
            }
        }
        return result;
    }

    private static String getDatePage(Document d) {
        Elements divs = d.select(INPUT);
        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(ID).equals("datepicker")) {
                return element.attr("data-date");
            }
        }
        return null;
    }

    private static String getNextDatePage(Document d) {
        Elements spans = d.select(SPAN);
        for (int i = 0, size = spans.size(); i < size; i++) {
            Element span = spans.get(i);
            if (span.attr(CLASS).equals("current")) {
                Element nextSpan = span.nextElementSibling();
                Element nextA = nextSpan.getElementsByAttribute("href").get(0);
                if (nextA.attr("href").contains("/abyssbest/")) {
                    return nextA.attr("href").replace("/abyssbest/", "");
                }
            }
        }
        return null;
    }
}
