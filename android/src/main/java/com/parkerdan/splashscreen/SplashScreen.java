package com.parkerdan.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

// import android.util.Log;


public class SplashScreen {
   private static final int UIAnimationNone = 0;
   private static final int UIAnimationFade = 1;
   private static final int UIAnimationScale = 2;

   private static Dialog dialog;
   private static ImageView imageView;

  //  private static final String TAG = "MyActivity";

    private static int getImageId(final Activity activity) {
        int drawableId = activity.getResources().getIdentifier("splash", "drawable", activity.getClass().getPackage().getName());
        if (drawableId == 0) {
            drawableId = activity.getResources().getIdentifier("splash", "drawable", activity.getPackageName());
        }
        return drawableId;
    }


    public static void show(final Activity activity) {

        if (activity == null) return;

        final int drawableId = getImageId(activity);

        if ((dialog != null && dialog.isShowing())||(drawableId == 0)) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            public void run() {

                Context context = activity;
                imageView = new ImageView(context);

                imageView.setImageResource(drawableId);

                LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);

                imageView.setImageResource(drawableId);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

                dialog.setContentView(imageView);
                dialog.setCancelable(false);
                dialog.show();

            }
        });
    }

    public static void remove(Activity activity, final String animationType,final int duration) {
        if (activity == null) {
            if(activity == null) return;
        }
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    AnimationSet animationSet = new AnimationSet(true);

                    // Log.i(TAG,"I am here");
                    // Log.i(TAG,"animation type =  " + animationType);
                    // Log.i(TAG,"duration =   " + duration);
                    if(animationType.equals("scale")) {
                        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setDuration(duration);
                        animationSet.addAnimation(fadeOut);

                        ScaleAnimation scale = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.65f);
                        scale.setDuration(duration);
                        animationSet.addAnimation(scale);
                    } else if(animationType.equals("fade")) {

                        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setDuration(duration);
                        animationSet.addAnimation(fadeOut);
                    } else {
                        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setDuration(0);
                        animationSet.addAnimation(fadeOut);
                    }

                    final View view = ((ViewGroup)dialog.getWindow().getDecorView()).getChildAt(0);
                    view.startAnimation(animationSet);

                    animationSet.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    dialog = null;
                                    imageView = null;
                                }
                            });
                        }
                    });
                }
            }
        });
    }

}
