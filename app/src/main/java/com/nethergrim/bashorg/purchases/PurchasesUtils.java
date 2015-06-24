package com.nethergrim.bashorg.purchases;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;
import com.nethergrim.bashorg.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author andrej on 24.06.15.
 */
public class PurchasesUtils {

    public static final String ID_DARK_THEME = "dark_theme";

    @WorkerThread
    public static JSONObject getSkuDetails(String id) throws RemoteException, JSONException {
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

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    Log.e("TAG", "details list: " + object.toString());
                    if (sku.equals(id)) {
                        return object;
                    }
                }
            }
        }
        return null;
    }
}
