#import <React/RCTBridgeModule.h>
#import <React/RCTRootView.h>

@interface RCTSplashScreen : NSObject <RCTBridgeModule>

+ (void)open:(RCTRootView *)v;

@end
