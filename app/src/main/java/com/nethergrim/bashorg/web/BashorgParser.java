package com.nethergrim.bashorg.web;

import com.nethergrim.bashorg.Constants;
import com.nethergrim.bashorg.Prefs;
import com.nethergrim.bashorg.db.DB;
import com.nethergrim.bashorg.model.Quote;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class BashorgParser {

    private static int lastPage = -1;

    public static int parsePageFromTop(int byRatingPage) {
        try {
            Document document = Jsoup.connect(Constants.URL_BASHORG_TOP + String.valueOf(byRatingPage)).get();
            return parseDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int parsePage(final String pageNumber) {
        try {
            Document document = Jsoup.connect(Constants.URL_BASHORG_PAGE + pageNumber).get();
            return parseDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int parseRandomPage() {
        try {
            Document document = Jsoup.connect(Constants.URL_BASHORG_RANDOM_PAGE + "?" + String.valueOf(System.currentTimeMillis())).get();
            return parseDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    private static int parseDocument(Document document) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        DB db = DB.getInstance();
        int pn = -1;
        try {
            long[] ids = getIds(document);
            long[] ratings = getRatings(document);
            String[] texts = getTexts(document);
            String[] dates = getDates(document);
            pn = getPageNumber(document);
            int size = ids.length;
            Quote[] quotes = new Quote[size];
            for (int i = 0; i < quotes.length; i++) {
                Quote quote = Quote.newInstance();
                quote.setDate(dates[i]);
                quote.setId(ids[i]);
                quote.setText(texts[i]);
                quote.setRating(ratings[i]);
                quote.setPage(pn);
                if (lastPage != pn) {
                    size--;
                    quote.setIndexOnPage(size);
                }
                quotes[i] = quote;
            }
            db.persist(quotes);
            for (int i = 0, size_ = quotes.length; i < size_; i++) {
                quotes[i].recycle();
                quotes[i] = null;
            }
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
                String maxPage = element.attr("max");
                Prefs.setLastPageNumber(Long.parseLong(maxPage));
                lastPage = Integer.parseInt(maxPage);
                return Integer.parseInt(currentPage);
            }
        }
        return -1;
    }

    public static long[] getIds(Document document) {
        long[] array = new long[Constants.DEFAULT_PAGE_SIZE];
        int counter = 0;
        Elements numberElements = document.select("a");
        for (Element numberElement : numberElements) {
            if (numberElement.attr("class").equals("id")) {
                String text = numberElement.html();
                text = text.substring(1, text.length());
                array[counter] = Long.parseLong(text);
                counter++;
            }
        }
        return array;
    }

    public static String[] getTexts(Document document) {
        String[] array = new String[Constants.DEFAULT_PAGE_SIZE];
        int counter = 0;
        Elements divs = document.select("div");
        for (Element element : divs) {
            if (element.attr("class").equals("text")) {
                String text = element.html();
                text = text.replace("<br>", "");
                text = StringEscapeUtils.unescapeHtml4(text);
                array[counter] = text;
                counter++;
            }
        }
        return array;
    }

    public static String[] getDates(Document document) {
        String[] array = new String[Constants.DEFAULT_PAGE_SIZE];
        int counter = 0;
        Elements divs = document.select("span");
        for (Element element : divs) {
            if (element.attr("class").equals("date")) {
                String text = element.html();
                array[counter] = text;
                counter++;
            }
        }
        return array;
    }

    public static long[] getRatings(Document document) {
        long[] array = new long[Constants.DEFAULT_PAGE_SIZE];
        Elements spans = document.select("span");
        int counter = 0;
        for (Element span : spans) {
            if (span.attr("class").equals("rating")) {
                if (span.html().equals("...")) {
                    array[counter] = 0l;
                    continue;
                }
                try {
                    String rating = span.html();
                    array[counter] = Long.parseLong(rating);
                } catch (NumberFormatException e) {
                    array[counter] = 0l;
                    e.printStackTrace();
                }
                counter++;
            }
        }
        return array;
    }
}
