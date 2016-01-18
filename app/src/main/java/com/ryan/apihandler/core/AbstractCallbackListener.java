package com.ryan.apihandler.core;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ryan on 2015/8/11.
 */

public abstract class AbstractCallbackListener{
    public abstract void onPrepare();
    public abstract void onSuccess(String result);
    public abstract void onSuccess(JSONObject jsonObject);
    public abstract void onSuccess(JSONArray jsonArray);
    public abstract void onFailure();
}

