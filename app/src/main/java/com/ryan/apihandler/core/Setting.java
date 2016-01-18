package com.ryan.apihandler.core;

import java.util.Map;

/**
 * Created by Ryan on 2016/1/15.
 */
public class Setting {

    AbstractCallbackListener listener;

    public String apiPath = "";
    public Method method = Method.GET;
    public Map<String,Object> p = null;

    public boolean isShowProgress = false;
    public boolean isShowDialog = false;

    public int refreshSecond;


    public Setting CallbackListener(AbstractCallbackListener listener){
        this.listener = listener;
        return this;
    }

    public Setting APIPath(String path){
        this.apiPath = path;
        return this;
    }

    public Setting HttpMethod(Method method){
        this.method = method;
        return this;
    }

    public Setting Params(Map<String,Object> p){
        this.p = p;
        return this;
    }

    public Setting ShowProgress(boolean isShowProgress){
        this.isShowProgress = isShowProgress;
        return this;
    }

    public Setting ShowDialog(boolean isShowDialog){
        this.isShowDialog = isShowDialog;
        return this;
    }

    public Setting RefreshSecond(int millisecond){
        this.refreshSecond = millisecond;
        return this;
    }

}
