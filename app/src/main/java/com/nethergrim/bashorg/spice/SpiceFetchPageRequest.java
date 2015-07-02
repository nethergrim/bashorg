package com.nethergrim.bashorg.spice;

import android.util.Log;
import com.nethergrim.bashorg.web.BashorgParser;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 *         All rights reserved.
 */
public class SpiceFetchPageRequest extends SpiceRequest<Integer> {

    private int mPageToLoad;

    public SpiceFetchPageRequest(Integer pageToLoad) {
        super(Integer.class);
        this.mPageToLoad = pageToLoad;
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {
        int result = BashorgParser.getPage(mPageToLoad);
        Log.e("TAG", "loaded page: " + result + " from request: " + mPageToLoad);
        return result;
    }
}
