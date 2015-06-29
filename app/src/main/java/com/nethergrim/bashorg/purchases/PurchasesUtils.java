package com.nethergrim.bashorg.purchases;

import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.WorkerThread;

import com.android.vending.billing.IInAppBillingService;
import com.nethergrim.bashorg.App;
import com.nethergrim.bashorg.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrej on 24.06.15.
 */
public class PurchasesUtils {

    public static final String ID_DARK_THEME = "dark_theme";

    @WorkerThread
    public static List<JSONObject> getSkuDetails() throws RemoteException, JSONException {
        if (InAppBillingServiceHolder.isConnected()) {
            IInAppBillingService service = InAppBillingServiceHolder.getService();
            ArrayList<String> skuList = new ArrayList<String>();
            skuList.add(ID_DARK_THEME);
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
            Bundle skuDetails = service.getSkuDetails(3, App.getInstance().getApplicationContext().getPackageName(), "inapp", querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) { // successful
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                List<JSONObject> result = new ArrayList<>();
                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    result.add(object);
                }
                return result;
            }
        }
        return null;
    }

    public static IntentSender getBuyIntentSender(String skuId) {
        if (InAppBillingServiceHolder.isConnected()) {
            IInAppBillingService service = InAppBillingServiceHolder.getService();
            Bundle buyIntentBundle = null;
            try {
                buyIntentBundle = service.getBuyIntent(3, App.getInstance().getPackageName(), skuId, "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                return pendingIntent.getIntentSender();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void persistBoughtSkus() {
        if (InAppBillingServiceHolder.isConnected()) {
            IInAppBillingService service = InAppBillingServiceHolder.getService();
            try {
                Bundle ownedItems = service.getPurchases(3, App.getInstance().getPackageName(), "inapp", null);
                int response = ownedItems.getInt("RESPONSE_CODE");
                if (response == 0) {
                    ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                    ownedItems.getString("INAPP_CONTINUATION_TOKEN");
                    InAppBillingServiceHolder.mBoughtSkus = ownedSkus;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean debugPurchasesMode() {
        return BuildConfig.DEBUG;
    }
}
