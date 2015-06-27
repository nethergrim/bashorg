package com.nethergrim.bashorg.purchases;

import android.os.RemoteException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author andrej on 27.06.15.
 */
public class GetSkuDetailsThread extends Thread {

    @Override
    public void run() {

        try {
            List<JSONObject> skuList = PurchasesUtils.getSkuDetails();
            for (JSONObject jsonObject : skuList) {
                Log.e("TAG", "sku: " + jsonObject.toString());
            }
        } catch (RemoteException | JSONException e) {
            e.printStackTrace();
        }
    }
}
