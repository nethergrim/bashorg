package com.nethergrim.bashorg.web;

import android.support.annotation.NonNull;
import android.util.Log;
import com.nethergrim.bashorg.model.Abyss;
import io.realm.Realm;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class AbyssParser {

    public static final String URL_ABYSS_BEST = "http://bash.im/abyssbest/";

    public static boolean parsePage(@NonNull String pageDate) {
        String finalUrl = URL_ABYSS_BEST + pageDate;
        try {
            Document d = Jsoup.connect(finalUrl).get();
            List<Abyss> data = parseDocument(d);
            persistAbyssToDB(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "error during parsing abyss page: " + pageDate + "\n" + e.getMessage());
        }
        return false;
    }

    private static List<Abyss> parseDocument(@NonNull Document d) {
        return null;
    }

    private static void persistAbyssToDB(@NonNull final List<Abyss> data) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
                realm.close();
            }
        });
    }
}
