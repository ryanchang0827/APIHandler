package com.ryan.apihandler.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ryan.apihandler.R;

/**
 * Created by ryan on 2015/8/11.
 */
public class Utils {
    public static boolean isNULL(Object obj) {
        return (null == obj) || ("null".equals(obj.toString().toLowerCase().trim()));
    }

    public static boolean isEmpty(Object obj) {
        return (null == obj) || ("null".equals(obj.toString().toLowerCase().trim())) || "".equals(obj.toString().trim());
    }

    public static boolean isNetworkOK(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null)
                return ni.isConnected();

        }
        return false;
    }

    public static void showNoNetDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_title_no_net)
                .setMessage(R.string.dialog_msg_no_net)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .show();
    }

    public static void showSorry2Wait(final Activity activity){
        new AlertDialog.Builder(activity)
                .setTitle(R.string.dialog_title_error)
                .setMessage(R.string.dialog_msg_error)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
