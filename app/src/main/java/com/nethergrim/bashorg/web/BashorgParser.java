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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nethergrim on 26.11.2014.
 */
public class BashorgParser {

    private static int lastPage = -1;

    public static int parsePage(final String pageNumber) {
        DB db = DB.getInstance();
        int pn = -1;
        try {
            Document document = Jsoup.connect(Constants.URL_BASHORG_PAGE + pageNumber).get();
            List<Long> numbers = getIds(document);
            List<Long> ratings = getRatings(document);
            List<String> texts = getTexts(document);
            List<String> dates = getDates(document);
            pn = getPageNumber(document);
            int size = texts.size();
            List<Quote> quotes = new ArrayList<>(texts.size() + 1);
            for (int i = 0; i < texts.size(); i++) {
                Quote quote = new Quote();
                quote.setDate(dates.get(i));
                quote.setId(numbers.get(i));
                quote.setText(texts.get(i));
                quote.setRating(ratings.get(i));
                quote.setPage(pn);
                if (lastPage != pn) {
                    size--;
                    quote.setIndexOnPage(size);
                }
                quotes.add(quote);
            }
            db.persist(quotes);
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
//                Log.e("PARSE", "current page: " + currentPage + " max page: " + maxPage);
                lastPage = Integer.parseInt(maxPage);
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

    public static List<Long> getRatings(Document document){
        List<Long> ratings = new ArrayList<>();
        Elements spans = document.select("span");
        for (Element span : spans) {
            if (span.attr("class").equals("rating")){
                if (span.html().equals("...")) {
                    ratings.add(0l);
                    continue;
                }
                try {
                    String rating = span.html();
                    ratings.add(Long.parseLong(rating));
                } catch (NumberFormatException e) {
                    ratings.add(0l);
                    e.printStackTrace();
                }
            }
        }
        return ratings;
    }
}
