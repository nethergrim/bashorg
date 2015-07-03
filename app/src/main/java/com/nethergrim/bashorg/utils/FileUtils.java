package com.nethergrim.bashorg.utils;

import android.content.Context;
import android.util.Log;
import com.nethergrim.bashorg.App;

import java.io.*;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class FileUtils {


    public static void writeStringAsFile(final String fileContents, String fileName) {
        Context context = App.getInstance().getApplicationContext();
        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
        }
    }

    public static String readFileAsString(String fileName) {
        Context context = App.getInstance().getApplicationContext();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
        }

        return stringBuilder.toString();
    }

}
