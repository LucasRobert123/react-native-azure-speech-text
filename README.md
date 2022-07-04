
# React Native Azure Speech Text

[![npm version](https://badge.fury.io/js/react-native-azure-speech-text.svg)](https://badge.fury.io/js/react-native-azure-speech-text)
[![npm](https://img.shields.io/npm/dt/react-native-azure-speech-text.svg)](https://npmcharts.com/compare/react-native-azure-speech-text?minimal=true)
![MIT](https://img.shields.io/dub/l/vibe-d.svg)
![Platform - Android and iOS](https://img.shields.io/badge/platform-Android%20%7C%20iOS-yellow.svg)

## Installation

```sh
yarn add react-native-azure-speech-text
```

Or with npm

```sh
npm i react-native-azure-speech-text --save
```


  ```sh
  cd ios && pod install
  ```

## Usage

```js
import AzureSpeechText from "react-native-azure-speech-text";

AzureSpeechText.config({
  subscription: <YOUR_KEY>,
  region: <YOUR_REGION>
})

const result = await AzureSpeechText.toTextUseMic();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
