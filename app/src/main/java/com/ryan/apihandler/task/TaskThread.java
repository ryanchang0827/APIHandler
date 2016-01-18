package com.ryan.apihandler.task;

import android.app.Activity;
import android.util.Log;

import com.ryan.apihandler.core.Setting;

import java.util.ListIterator;

/**
 * Created by Ryan on 2016/1/12.
 */
public class TaskThread extends Thread {

    private Activity activity;
    private boolean isStop;
    private MultiTaskManager taskManager;
    private static long totalSecond;

    public TaskThread(Activity act, MultiTaskManager dm) {
        this.activity = act;
        this.taskManager = dm;
        this.totalSecond = 0;
        this.isStop = false;

    }

    public void setStop(boolean isStop){
        this.isStop = isStop;
    }

    public void run() {
        while(!isStop){
            try {
                Thread.sleep(500);
                totalSecond += 500;
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            ListIterator iter = taskManager.settingList.listIterator();
            while (iter.hasNext()) {

                Setting setting = (Setting)iter.next();
                final int second = setting.refreshSecond;
                final int index = iter.nextIndex() - 1;
                int nowTimes = taskManager.timesList.get(index);

                //Log.e("Total Second", String.valueOf(totalSecond));
                //Log.e("Now Times", String.valueOf(totalSecond / second));
                //Log.e("Count Times", String.valueOf(nowTimes));

                if(totalSecond / second > nowTimes){
                    taskManager.timesList.set(index, ++nowTimes);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            taskManager.runTaskWithIndex(index);
                        }
                    });
                }

            }

        }

    }

}
