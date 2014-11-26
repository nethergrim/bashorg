package com.nethergrim.bashorg.web;

import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;

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

    public static void parseNewPage(String page){
        Document document = Jsoup.parse(page);

        List<String> numbers = new ArrayList<String>();
        List<String> texts = new ArrayList<String>();
        Elements divs = document.select("div");
        for (Element element : divs) {
            if (element.attr("class").equals("text")){
                String text = element.html();
                texts.add(text);
            }
        }

        Elements numberElements = document.select("a");
        for (Element numberElement : numberElements) {
            if (numberElement.attr("class").equals("id")){
                String text = numberElement.html();
                numbers.add(text);
            }
        }

        Log.e("parsed", "page");
    }
}
