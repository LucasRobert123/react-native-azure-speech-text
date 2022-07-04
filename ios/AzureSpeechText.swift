@objc(AzureSpeechText)
class AzureSpeechText: NSObject {

  var region = "";
  var sub = "";

  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }


    @objc(config:)
    func config(params: [String: Any]) {
        region = (params["region"] as! NSString) as String;
        sub = (params["subscription"] as! NSString) as String;
    }

    @objc func toTextUseMic(_ resolve: RCTPromiseResolveBlock,rejecter reject: RCTPromiseRejectBlock) -> Void {
              var speechConfig: SPXSpeechConfiguration?
              do {
                  try speechConfig = SPXSpeechConfiguration(subscription: sub, region: region)
                  speechConfig?.speechRecognitionLanguage = "pt-BR"
              } catch {
                  print("error \(error) happened")
                  speechConfig = nil
              }
              speechConfig?.speechRecognitionLanguage = "en-US"

              let audioConfig = SPXAudioConfiguration()

              let reco = try! SPXSpeechRecognizer(speechConfiguration: speechConfig!, audioConfiguration: audioConfig)

              reco.addRecognizingEventHandler() {reco, evt in
                  print("intermediate recognition result: \(evt.result.text ?? "(no result)")")
              }


              print("Listening...")

              let result = try! reco.recognizeOnce()
              print("recognition result: \(result.text ?? "(no result)")")
             resolve(result.text)
       }

    @objc(toSpeech:withResolver:withRejecter:)
    func toSpeech(text: NSString, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        var speechConfig: SPXSpeechConfiguration?
        do {
            try speechConfig = SPXSpeechConfiguration(subscription: sub, region: region)
        } catch {
            print("error \(error) happened")
            speechConfig = nil
        }

        let inputText: String = text as String

        let synthesizer = try! SPXSpeechSynthesizer(speechConfig!)
        if inputText.isEmpty {
            return
        }
        let result = try! synthesizer.speakText(inputText)
        if result.reason == SPXResultReason.canceled
        {
            let cancellationDetails = try! SPXSpeechSynthesisCancellationDetails(fromCanceledSynthesisResult: result)
            print("cancelled, detail: \(cancellationDetails.errorDetails!) ")
        }
      }
}
