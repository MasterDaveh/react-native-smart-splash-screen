package com.parkerdan.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen {
   private static final int UIAnimationNone = 0;
   private static final int UIAnimationFade = 1;
   private static final int UIAnimationScale = 2;

   private static Dialog dialog;
   private static ImageView imageView;


   private static int getImageId(final Activity activity) {
       int drawableId = activity.getResources().getIdentifier("splash", "drawable", activity.getClass().getPackage().getName());
       if (drawableId == 0) {
           drawableId = activity.getResources().getIdentifier("splash", "drawable", activity.getPackageName());
       }
       return drawableId;
   }

   private static int getStatusBarHeight(final Activity activity) {
       int height = 0;
       Resources resources = activity.getResources();
       int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
       if (resourceId > 0) {
           height = resources.getDimensionPixelSize(resourceId);
       }
       return height;
   }


   private static Bitmap getClipBitmap(final int drawableId, final Activity activity) {

       Resources resources = activity.getResources();

       Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), drawableId);

       if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
               != WindowManager.LayoutParams.FLAG_FULLSCREEN) {

           DisplayMetrics dm = resources.getDisplayMetrics();
           int screenWidth = dm.widthPixels;
           int screenHeight = dm.heightPixels;

           int navigationBarHeight = 0;    //getNavigationBarHeight();
           int statusBarHeight = 0;   //getStatusBarHeight();

           statusBarHeight = getStatusBarHeight(activity);

           int width = bitmap.getWidth();
           int height = bitmap.getHeight();
           int newWidth = screenWidth;
           int newHeight = screenHeight + navigationBarHeight;

           float scaleWidth = ((float) newWidth) / width;
           float scaleHeight = ((float) newHeight) / height;
           Matrix matrix = new Matrix();
           matrix.postScale(scaleWidth, scaleHeight);
           Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                   height, matrix, true);

           int y = statusBarHeight;
           int x = 0;

           bitmap = Bitmap.createBitmap(resizedBitmap, x, y, resizedBitmap.getWidth(),
                   resizedBitmap.getHeight() - y - navigationBarHeight, null, false);
           resizedBitmap.recycle();
       }

       return bitmap;
   }



   public static void show(final Activity activity) {


     final int drawableId = getImageId(activity);
     if ((dialog != null && dialog.isShowing())||(drawableId == 0)) {
         return;
     }
     activity.runOnUiThread(new Runnable() {
         public void run() {
             Context context = activity;

             imageView = new ImageView(context);
             imageView.setImageBitmap(getClipBitmap(drawableId,activity));
             LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
             imageView.setLayoutParams(layoutParams);

             imageView.setBackgroundColor(Color.BLACK);
             imageView.setScaleType(ImageView.ScaleType.FIT_XY);

             dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
             if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                     == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                 dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);
             }
             dialog.setContentView(imageView);
             dialog.setCancelable(false);
             dialog.show();
         }
     });
   }

   // @ReactMethod
   public static void close(final String animationType, final int duration, final int delay, final Activity activity) {
       final Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           public void run() {
               removeSplashScreen(animationType, duration, activity);
           }
       }, delay);
   }

   private static void removeSplashScreen(final String animationType,final int duration, final Activity activity) {
       activity.runOnUiThread(new Runnable() {
           public void run() {
               if (dialog != null && dialog.isShowing()) {
                   AnimationSet animationSet = new AnimationSet(true);
                   if(animationType.equals("scale")) {
                       AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                       fadeOut.setDuration(duration);
                       animationSet.addAnimation(fadeOut);

                       ScaleAnimation scale = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.65f);
                       scale.setDuration(duration);
                       animationSet.addAnimation(scale);
                   }
                   else if(animationType.equals("fade")) {

                       AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                       fadeOut.setDuration(duration);
                       animationSet.addAnimation(fadeOut);
                   }
                   else {

                       AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
                       fadeOut.setDuration(0);
                       animationSet.addAnimation(fadeOut);
                   }

                   View view = ((ViewGroup)dialog.getWindow().getDecorView()).getChildAt(0);
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
                           dialog.dismiss();
                       }
                   });
               }
           }
       });
   }
}
