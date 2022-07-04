import { NativeModules } from 'react-native';

const { AzureSpeechText } = NativeModules;

export default class RNAzureSpeechText {

  static config(params) {
    AzureSpeechText.config(params);
  }

  static async toTextUseMic() {
    return await AzureSpeechText.toTextUseMic();
  }

  static async toSpeech(text){
    return await AzureSpeechText.toSpeech(text);
  }
}
