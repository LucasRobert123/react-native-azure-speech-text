#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(AzureSpeechText, NSObject)

RCT_EXTERN_METHOD(toTextUseMic: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(config:(NSDictionary *)params)

RCT_EXTERN_METHOD(toSpeech:(NSString)text 
                withResolver:(RCTPromiseResolveBlock)resolve
                withRejecter:(RCTPromiseRejectBlock)reject)

@end
