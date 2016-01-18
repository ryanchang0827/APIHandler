# APIHandler
For Android Networking

## Requirements

- android SDK 8+


## Usage

###with Parameters
```java
        Map<String,Object> params = new HashMap<>();
        params.put("Apple", "1");
        params.put("Orange", "3");
        params.put("Banana", "5");

        String API_PATH = "https://www.google.com";
        
        APIHandler a = new APIHandler(activity, new OnApiCallbackListener(){
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
        a.APIPath(API_PATH).httpMethod(Method.POST).setParams(params).execute();
```

###without Parameters
```java
        String API_PATH = "https://www.google.com";
        
        APIHandler a = new APIHandler(activity, new OnApiCallbackListener(){
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
        a.APIPath(API_PATH).execute();
```

###Multiple task
```java
        MultiTaskManager taskManager = new MultiTaskManager(this);

        taskManager.setAllTaskFinishCallback(true);

        Setting setting1 = new Setting().APIPath(API_PATH).CallbackListener(someapi1);
        taskManager.addTask(setting1);

        Setting setting2 = new Setting().APIPath(API_PATH).Params(params).CallbackListener(someapi2);
        taskManager.addTask(setting2);

        taskManager.runAllTask();
```

####activity implements OnMultiTaskManagerCallbackListener
```java
        @Override
        public void onAllTaskFinish() {
                //Callback When All Task Finish
                //No Support TaskThread
        }
```

###Multiple task with loop
```java
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
```

