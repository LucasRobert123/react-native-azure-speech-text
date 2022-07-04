package com.reactnativeazurespeechtext.service;//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//

import android.util.Log;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.reactnativeazurespeechtext.AzureSpeechTextModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SpeechToTextService {

  private SpeechRecognizer reco = null;
  private AudioConfig audioInput = null;
  private MicrophoneStream microphoneStream;

  private MicrophoneStream createMicrophoneStream() {
    if (microphoneStream != null) {
      microphoneStream.close();
      microphoneStream = null;
    }

    microphoneStream = new MicrophoneStream();
    return microphoneStream;
  }

  boolean continuousListeningStarted = false;

  public void onListen(AzureSpeechTextModule.OnListeningSpeech listener, String subkey, String region) {

    final SpeechConfig speechConfig = SpeechConfig.fromSubscription(subkey, region);
    speechConfig.setSpeechRecognitionLanguage("pt-BR");
    if (continuousListeningStarted) {
      return;
    }

    continuousListeningStarted = true;

    try {
      audioInput = AudioConfig.fromStreamInput(createMicrophoneStream());
      reco = new SpeechRecognizer(speechConfig, audioInput);

      reco.recognizing.addEventListener((o, speechRecognitionResultEventArgs) -> {
        final String s = speechRecognitionResultEventArgs.getResult().getText();
        Log.d("recognizing", s);
      });

      reco.recognized.addEventListener((o, speechRecognitionResultEventArgs) -> {
        final String s = speechRecognitionResultEventArgs.getResult().getText();
        listener.onRegconize(s);
        Log.d("recognized", s);
      });

      final Future<Void> task = reco.startContinuousRecognitionAsync();
      setOnTaskCompletedListener(task, result -> {
        continuousListeningStarted = true;
      });
    } catch (Exception ex) {
      Log.d("ExceptionLog", ex.toString());
    }
  }

  public void onStop(){
    if (reco != null) {
      final Future<Void> task = reco.stopContinuousRecognitionAsync();
      setOnTaskCompletedListener(task, result -> {
        continuousListeningStarted = false;
      });
    } else {
      continuousListeningStarted = false;
    }
  }


  private <T> void setOnTaskCompletedListener(Future<T> task, OnTaskCompletedListener<T> listener) {
    s_executorService.submit(() -> {
      T result = task.get();
      listener.onCompleted(result);
      return null;
    });
  }

  private interface OnTaskCompletedListener<T> {
    void onCompleted(T taskResult);
  }

  private static ExecutorService s_executorService;

  static {
    s_executorService = Executors.newCachedThreadPool();
  }
}
