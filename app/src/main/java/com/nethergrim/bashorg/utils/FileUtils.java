package com.nethergrim.bashorg.utils;

import android.content.Context;
import android.util.Log;
import com.nethergrim.bashorg.App;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class FileUtils {

    public static final String BASHORG_JSON_FILE_NAME = "bashorg.json";
    public static final String ZIP_FILE_POSTFIX = ".zip";

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

    public static String readFileAsString(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(new File(filePath)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static String unpackAssetFileAndUnzip(String fileAssetsName) {
        InputStream is = getAssetFileInputStream(fileAssetsName);
        ZipInputStream zis;
        Context context = App.getInstance().getApplicationContext();
        String corePagePath = context.getFilesDir().getAbsolutePath() + "/";
        try {
            String filename = null;
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                if (ze.isDirectory()) {
                    File fmd = new File(corePagePath + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(corePagePath + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
            return corePagePath + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getAssetFileInputStream(String assetFileName) {
        Context context = App.getInstance().getApplicationContext();
        InputStream fIn;
        try {
            fIn = context.getResources().getAssets().open(assetFileName, Context.MODE_WORLD_READABLE);
            return fIn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJsonArrayFromDisk(String pathToFile) {
        JSONArray result = null;
        String json = readFileAsString(pathToFile);
        try {
            result = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean unpackZip(String path, String zipname) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(path + zipname);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String readFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}
