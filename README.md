# react-native-smart-splash-screen

I forked this repo and made it actually work on both platforms.

## I've updated to read me accordingly.



## Installation
- Add this to your package.json

```js
"react-native-smart-splash-screen": "https://github.com/parkerdan/react-native-smart-splash-screen/tarball/master",
```

-`npm install`

## Installation (iOS)

* Drag RCTSplashScreen.xcodeproj to your project on Xcode.

* Click on your main project file (the one that represents the .xcodeproj) select Build Phases and drag libRCTSplashScreen.a from the Products folder inside the RCTSplashScreen.xcodeproj.

* Look for Header Search Paths and make sure it contains `$(SRCROOT)/../../../react-native/React` as recursive.

* In your project, Look for Header Search Paths and make sure it contains `$(SRCROOT)/../node_modules/react-native-smart-splash-screen/ios/RCTSplashScreen/RCTSplashScreen` as recursive

* delete your project's LaunchScreen.xib

##### This uses a Launch Image set.  You must have a Launch Image set created for this to work

##### In AppDelegate.m add two lines of code, remove one

```c
// right below other imports
#import "RCTSplashScreen.h"

// remove this
// rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];


// replace with this
[RCTSplashScreen open:rootView];


```


## Installation (Android)

* In `android/settings.gradle`

```
...
include ':react-native-smart-splashscreen'
project(':react-native-smart-splashscreen').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-smart-splash-screen/android')
```

* In `android/app/build.gradle`

```
...
dependencies {
    ...
    // From node_modules
    compile project(':react-native-smart-splashscreen')
}
```

* Create a drawable folder to put your splash image in.  The path should be `android/app/src/main/res/drawable`

* Make the image title `splash.png` full path `res/drawable/splash.png`


#### MainActivity.java

```java
// import these FOUR things
import com.parkerdan.splashscreen.SplashScreen;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.ReactInstanceManager;
import android.os.Bundle;

// Add this method to the MainActivity class
  @Override
    protected void onCreate(Bundle savedInstanceState) {
      SplashScreen.show(this);
      super.onCreate(savedInstanceState);
   }


```

#### MainApplication.java

```java
// import the package
import com.parkerdan.splashscreen.SplashScreenPackage;


// Add to packages
...
    new MainReactPackage(),
    new SplashScreenPackage()
...

```



## Usage

```js
...
import SplashScreen from 'react-native-smart-splash-screen'
...
componentDidMount () {
     SplashScreen.close("scale", 850, 500)
}
...

```

## Method

* close(animationType, duration, delay)
  close splash screen with custom animation

  * animationType: one of ("scale","fade","none")
  * duration: determine the duration of animation
  * delay: determine the delay of animation
