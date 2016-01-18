package com.ryan.apihandler.task;

import android.app.Activity;

import com.ryan.apihandler.core.APIHandler;
import com.ryan.apihandler.core.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Ryan on 2016/1/05.
 */
public class MultiTaskManager {

    private Activity activity;
    private List<APIHandler> handlerList;
    public List<Setting> settingList;
    public List<Integer> timesList;

    private boolean isAllTaskFinishCallback = false;


    public MultiTaskManager(Activity act){
        this.activity = act;
        settingList = new ArrayList<>();
        handlerList = new ArrayList<>();
        timesList = new ArrayList<>();
    }

    public void setAllTaskFinishCallback(boolean isCallback){
        this.isAllTaskFinishCallback = isCallback;
    }

    public void addTask(Setting setting){
        settingList.add(setting);
        timesList.add(0);
    }

    public void runAllTask(){
        this.handlerList.clear();
        ListIterator iter = this.settingList.listIterator();
        while (iter.hasNext()) {
            Setting setting = (Setting)iter.next();
            this.handlerList.add(new APIHandler(activity, this).Setting(setting).execute());

        }
    }

    public void runTaskWithIndex(int i){
        Setting setting = this.settingList.get(i);
        this.handlerList.add(i, new APIHandler(activity, this).Setting(setting).execute());
    }

    public void checkAllFinish(){
        if(isAllTaskFinishCallback == false)return;

        ListIterator iter = this.handlerList.listIterator();
        while (iter.hasNext()) {
            APIHandler task = (APIHandler)iter.next();
            if(task.isFinish() == false){
                return;
            }

        }
        ((OnMultiTaskManagerCallbackListener)activity).onAllTaskFinish();

    }


}
