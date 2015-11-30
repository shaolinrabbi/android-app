package com.perrchick.someapplication.utilities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.perrchick.someapplication.StorageActivity;

/**
 * Created by perrchick on 10/23/15.
 */
public class PerrFuncs {
    private static PerrFuncs _perrFuncsInstance;
    private Activity _topActivity;
    private DisplayMetrics _metrics;
    private Context _applicationContext;

    public interface Callback {
        void callbackCall(Object callbackObject);
    }

    private static PerrFuncs getInstance() {
        if (_perrFuncsInstance == null) {
            _perrFuncsInstance = new PerrFuncs();
        }

        return _perrFuncsInstance;
    }

    public static void toast(String toastMessage) {
        PerrFuncs.toast(toastMessage, true);
    }

    public static void toast(final String toastMessage, final boolean shortDelay) {
        PerrFuncs.getInstance().getTopActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), toastMessage, shortDelay ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
            }
        });
    }

    private static Context getApplicationContext() {
        return getInstance()._applicationContext;
    }

    public static void setApplicationContext(Context applicationContext){
        getInstance()._applicationContext= applicationContext;
    }

    public static void callNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + phoneNumber));
        getInstance().getTopActivity().startActivity(intent);
    }

    public static void showDialog(final String dialogTitle, final String dialogMessage) {
        PerrFuncs.getInstance().getTopActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(PerrFuncs.getInstance().getTopActivity())
                        .setTitle(dialogTitle)
                        .setMessage(dialogMessage)
                /*
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                */
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public static void setTopActivity(Activity topActivity) {
        PerrFuncs.getInstance()._topActivity = topActivity;
    }

    public static void getTextFromUser(Activity inActivity, String title, final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(inActivity);
        builder.setTitle(title);

        // Set up the input control
        final EditText inputText = new EditText(inActivity);

        // Specify the type of input expected; this, for example, add "| InputType.TYPE_TEXT_VARIATION_PASSWORD" and will mask the text
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(inputText);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = inputText.getText().toString();
                callback.callbackCall(result);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public Activity getTopActivity() {
        return PerrFuncs.getInstance()._topActivity;
    }

    public static int screenWidthPixels() {
        return PerrFuncs.getInstance().getMetrics().widthPixels;
    }

    public static int screenHeightPixels() {
        return PerrFuncs.getInstance().getMetrics().heightPixels;
    }

    private DisplayMetrics getMetrics() {
        if (_metrics == null) {
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            _metrics = new DisplayMetrics();
            display.getMetrics(_metrics);
        }

        return _metrics;
    }

    public static int getIndexOfItemInArray(Object item, Object[] arr) {
        int index = -1;
        int length = arr.length;

        for (int i =0; i < length; i++) {
            if (item == arr[i]) {
                index = i;
            }
        }

        return index;
    }

    public static void sayNo(final View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 20f);
        animator1.setRepeatCount(0);
        animator1.setDuration(50);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationX", -20f);
        animator2.setRepeatCount(0);
        animator2.setDuration(50);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "translationX", 5f);
        animator3.setRepeatCount(0);
        animator3.setDuration(50);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator2, animator3);
        set.start();
    }
}