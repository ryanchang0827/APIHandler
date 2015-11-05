package com.ryan.apihandler.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.ryan.apihandler.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Created by ryan on 2015/11/5.
 */
public class APIHandler extends AsyncTask<Void, Integer, String> {

    private Activity activity;
    private ProgressDialog dialog;
    private AbstractCallbackListener listener;
    private String apiPath = "";
    private Map<String,Object> p = null;
    private Method method = Method.GET;

    public APIHandler(Activity activity, AbstractCallbackListener listener) {
        this.activity = activity;
        this.listener = listener;
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Please Waiting...");
        dialog.setCancelable(false);

    }

    public APIHandler APIPath(String path){
        this.apiPath = path;
        return this;
    }

    public APIHandler httpMethod(Method method){
        this.method = method;
        return this;
    }

    public APIHandler setParams(Map<String,Object> p){
        this.p = p;
        return this;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";

        try{
            if (this.method == Method.GET && this.p != null) {
                this.apiPath += encodeGET(this.p);
            }

            URL url = new URL(this.apiPath);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(8000);
            conn.setConnectTimeout(8000);

            //conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(this.method.name());

            if (this.method == Method.POST) {
                byte[] postDataBytes = encodePOST(this.p);
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.getOutputStream().write(postDataBytes);
            }

            Log.e("path",this.apiPath);

            conn.connect();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            while ((line=br.readLine()) != null) {
                result += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(Utils.isNetworkOK(activity)){
            dialog.show();
            listener.onPrepare();
        }else{
            Utils.showNoNetDialog(activity);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) dialog.dismiss();

        if (Utils.isEmpty(result)) {
            listener.onFailure();
            Utils.showSorry2Wait(activity);
        } else {
            try {
                if (result.trim().substring(0, 1).equals("{")) {
                    JSONObject jo = new JSONObject(result);
                    listener.onSuccess(jo);

                } else if (result.trim().substring(0, 1).equals("[")) {
                    JSONArray ja = new JSONArray(result);
                    listener.onSuccess(ja);

                } else {
                    listener.onSuccess(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (dialog.isShowing()) dialog.dismiss();
        listener.onFailure();
        Utils.showSorry2Wait(this.activity);
    }

    private String gzipCompress(String input){
        try{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipStream = new GZIPOutputStream(outStream);
            gzipStream.write(input.getBytes("UTF-8"));
            gzipStream.close();

            return Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT);

        }catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }

    private String gzipDecompress(String input){
        try{
            byte[] source = Base64.decode(input, android.util.Base64.DEFAULT);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(source);
            GZIPInputStream gzipStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while((n = gzipStream.read(buffer))>=0){
                out.write(buffer,0,n);
            }

            return out.toString();

        }catch(IOException e){
            e.printStackTrace();
        }

        return "";
    }

    private String encodeGET(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            String separator = postData.length() == 0 ? "?" : "&";
            postData.append(separator);
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString();
    }


    private byte[] encodePOST(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString().getBytes("UTF-8");
    }
}
