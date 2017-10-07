package com.parkerdan.splashscreen;

import android.os.Handler;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

class SplashScreenModule extends ReactContextBaseJavaModule {

   public SplashScreenModule(ReactApplicationContext reactContext) {
       super(reactContext);
   }

   @Override
   public String getName() {
       return "SplashScreen";
   }

   @ReactMethod
    public void close(final String animationType, final int duration, final int delay) {

      final Handler handler = new Handler();

      handler.postDelayed(new Runnable() {
          public void run() {
              SplashScreen.remove(getCurrentActivity(), animationType, duration);
          }
      }, delay);

    }

}
