package com.example.adminnetflix.utils;
import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;


public class HideKeyBoard {
    public static void hide(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
