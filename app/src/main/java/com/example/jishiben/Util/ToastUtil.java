package com.example.jishiben.Util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void toastshort(Context context,String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }
    public static void toastlong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
