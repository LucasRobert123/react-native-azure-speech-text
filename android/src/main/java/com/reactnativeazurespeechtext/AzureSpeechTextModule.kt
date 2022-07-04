package com.reactnativeazurespeechtext

import com.facebook.react.bridge.*
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.reactnativeazurespeechtext.service.SpeechToTextService
import com.reactnativeazurespeechtext.service.TextToSpeechService


class AzureSpeechTextModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  var speechSubscriptionKey = ""
  var speechRegion = ""

  var textToSpeechService = TextToSpeechService()
  var speechToTextService = SpeechToTextService();

  override fun getName(): String {
    return "AzureSpeechText"
  }

  interface OnListeningSpeech {
    fun onRegconize(text: String)
  }

  interface OnSpeechListner{
    fun onSpeechDone()
  }

  @ReactMethod
  fun toTextUseMic(promise: Promise) {
    speechToTextService.onListen(object : OnListeningSpeech {
      override fun onRegconize(text: String) {
        speechToTextService.onStop();
        promise.resolve(text)
      }
    }, speechSubscriptionKey, speechRegion);
  }

  @ReactMethod
  fun onStop(promise: Promise) {
    speechToTextService.onStop();
  }

  @ReactMethod
  fun toSpeech(text:String, promise: Promise){
    textToSpeechService.say(speechSubscriptionKey, speechRegion, text, object: OnSpeechListner{
      override fun onSpeechDone() {
        promise.resolve("done")
      }

    })
  }

  @ReactMethod
  fun config(params: ReadableMap) {
    speechRegion = params.getString("region").toString()
    speechSubscriptionKey = params.getString("subscription").toString()
  }

  fun sendEvent(reactContext: ReactContext,
                eventName: String,
                @Nullable params: WritableMap) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

}
