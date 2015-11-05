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
