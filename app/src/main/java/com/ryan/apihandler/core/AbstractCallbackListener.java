package com.ryan.apihandler.core;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ryan on 2015/8/11.
 */

public abstract class AbstractCallbackListener{
    abstract void onPrepare();
    abstract void onSuccess(String result);
    abstract void onSuccess(JSONObject jsonObject);
    abstract void onSuccess(JSONArray jsonArray);
    abstract void onFailure();
}

