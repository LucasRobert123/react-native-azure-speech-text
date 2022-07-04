package com.reactnativeazurespeechtext.service

import android.util.Log
import com.microsoft.cognitiveservices.speech.*
import com.reactnativeazurespeechtext.AzureSpeechTextModule

class TextToSpeechService{

  private var synthesizer: SpeechSynthesizer? = null

  fun say(subKey : String, region: String, text: String, listener: AzureSpeechTextModule.OnSpeechListner){

    if(synthesizer == null){
      val speechConfig = SpeechConfig.fromSubscription(subKey, region)
      synthesizer = SpeechSynthesizer(speechConfig)
    }

    try {
      // Note: this will block the UI thread, so eventually, you want to register for the event
      val result: SpeechSynthesisResult = synthesizer!!.SpeakText(text)!!
      if (result.reason == ResultReason.SynthesizingAudioCompleted) {
        listener.onSpeechDone()
      } else if (result.reason == ResultReason.Canceled) {
        val cancellationDetails = SpeechSynthesisCancellationDetails.fromResult(result).toString()
        Log.e("Error", cancellationDetails);
      }
      result.close()
    } catch (ex: Exception) {
      Log.e("SpeechSDKDemo", "unexpected " + ex.message)
    }
  }
}
