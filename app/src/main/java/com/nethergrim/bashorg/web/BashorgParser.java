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
import java.util.Random;

/**
 * @author nethergrim on 26.11.2014.
 */
public class BashorgParser {

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
    private static final Random _random = new Random();
    private static int lastPage = -1;

    public static int getPage(int pageNumber) {
        if (pageNumber == 0) {
            return 0;
        }
        DB db = DB.getInstance();
        if (!db.isPageSaved(String.valueOf(pageNumber))) {
            int page = BashorgParser.parsePage(String.valueOf(pageNumber));
            if (page > 0) {
                Prefs.setSmallestLoadedPage(page);

            }
            return page;
        }
        return pageNumber;
    }

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
            Document document = Jsoup.connect(Constants.URL_BASHORG_RANDOM_PAGE + QUESTION + _random.nextLong()).get();
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
            for (int i = 0, quotes_size = quotes.length; i < quotes_size; i++) {
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
        Elements elements = document.select(INPUT);
        for (int i = 0, size = elements.size(); i < size; i++) {
            Element element = elements.get(i);
            if (element.attr(CLASS).equals(PAGE)) {
                String currentPage = element.attr(VALUE);
                String maxPage = element.attr(MAX);
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
        Elements numberElements = document.select(A);
        for (int i = 0, size = numberElements.size(); i < size; i++) {
            Element numberElement = numberElements.get(i);
            if (numberElement.attr(CLASS).equals(ID)) {
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
        Elements divs = document.select(DIV);

        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(CLASS).equals(TEXT)) {
                String text = element.html();
                text = text.replace(BR, EMPTY_STRING);
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
        Elements divs = document.select(SPAN);

        for (int i = 0, size = divs.size(); i < size; i++) {
            Element element = divs.get(i);
            if (element.attr(CLASS).equals(DATE)) {
                String text = element.html();
                array[counter] = text;
                counter++;
            }
        }
        return array;
    }

    public static long[] getRatings(Document document) {
        long[] array = new long[Constants.DEFAULT_PAGE_SIZE];
        Elements spans = document.select(SPAN);
        int counter = 0;

        for (int i = 0, size = spans.size(); i < size; i++) {
            Element span = spans.get(i);
            if (span.attr(CLASS).equals(RATING)) {
                if (span.html().equals(THREE_DOTS)) {
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
