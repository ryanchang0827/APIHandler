package com.ryan.apihandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.ryan.apihandler.core.APIHandler;
import com.ryan.apihandler.core.OnApiCallbackListener;
import com.ryan.apihandler.core.Setting;
import com.ryan.apihandler.task.MultiTaskManager;
import com.ryan.apihandler.task.OnMultiTaskManagerCallbackListener;
import com.ryan.apihandler.task.TaskThread;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements OnMultiTaskManagerCallbackListener {

    Activity activity;

    TaskThread taskThread;

    String API_PATH = "https://www.google.com";
    Map<String,Object> params = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        callapi();
        //runSomeTask();
        //runSomeTaskLoop();
    }

    public void callapi() {

        Map<String,Object> params = new HashMap<>();
        params.put("Apple", "1");
        params.put("Orange", "3");
        params.put("Banana", "5");

        String API_PATH = "https://www.google.com";

        APIHandler a = new APIHandler(null, new OnApiCallbackListener(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("API Result", result);

            }

            @Override
            public void onFailure() {
                super.onFailure();
            }
        });

        //POST
        //a.APIPath(API_PATH).HttpMethod(Method.POST).Params(params).execute();

        //GET
        a.APIPath(API_PATH).Params(params).execute();
    }

    public void runSomeTask(){
        //get Data
        MultiTaskManager taskManager = new MultiTaskManager(this);

        taskManager.setAllTaskFinishCallback(true);

        Setting setting1 = new Setting().APIPath(API_PATH).CallbackListener(someapi1);
        taskManager.addTask(setting1);

        Setting setting2 = new Setting().APIPath(API_PATH).Params(params).CallbackListener(someapi2);
        taskManager.addTask(setting2);

        taskManager.runAllTask();
    }


    public void runSomeTaskLoop(){
        //get Data
        MultiTaskManager taskManager = new MultiTaskManager(this);

        Setting setting1 = new Setting()
                .APIPath(API_PATH)
                .CallbackListener(someapi1)
                .RefreshSecond(30000);
        taskManager.addTask(setting1);

        Setting setting2 = new Setting()
                .APIPath(API_PATH)
                .CallbackListener(someapi2)
                .RefreshSecond(20000);
        taskManager.addTask(setting2);

        // Get Data Thread
        taskThread = new TaskThread(this, taskManager);
        taskThread.start();
    }

    OnApiCallbackListener someapi1 = new OnApiCallbackListener(){
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            // Save Data
        }
    };

    OnApiCallbackListener someapi2 = new OnApiCallbackListener(){
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);

        }
    };


    @Override
    public void onAllTaskFinish() {
        //Callback When All Task Finish
        //No Support TaskThread
    }


    @Override
    protected void onStop() {
        super.onStop();
//        try {
//            taskThread.setStop(true);
//            taskThread.join();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
    }
}
