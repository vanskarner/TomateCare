# TOMATE CARE APP: Leaf Disease Detection and Classification with TensorFlow Lite
Just point and take a picture of the tomato plant to check its condition.

Both the android application and the deep learning models have been developed by me, you can visit my profile in [kaggle](https://www.kaggle.com/luisolazo).

<p align="center">
  <img src="https://github.com/vanskarner/TomateCare/assets/39975255/98fb3c13-340e-45d0-93c2-466f0282a16c" alt="Leaf Disease Detection" style="display: block; margin: auto;" width="300" height="500">
</p>

## Development based on Clean Architecture
For the development of the application, I have used the practices described in [my other repository](https://github.com/vanskarner/CleanMovie), which addresses the updated clean architecture:

<p align="center">
  <img src="https://github.com/vanskarner/CleanMovie/assets/39975255/7d7c53a6-7c85-4456-a725-99814d3b1eb5" alt="CleanArchitectureCover_2017" style="display: block; margin: auto;" width="500" height="750">
</p>

## Deep Learning Models Used
- For leaf detection: [YoloV8n](https://www.kaggle.com/code/luisolazo/leaf-detection-w-ultralytics-yolov8-and-tflite), thanks to [Ultralytics YoloV8](https://docs.ultralytics.com/) for facilitating the creation of the model and its subsequent conversion
- for leaf classification:
  - [SqueezeNet-WithMish](https://www.kaggle.com/code/luisolazo/tomato-disease-prediction-squeezenet-mish-97-3)
  - [MobileNetV3Large](https://www.kaggle.com/code/luisolazo/tomato-disease-prediction-mobilenetv3large-97-1)
  - [MobileNetV3Small](https://www.kaggle.com/code/luisolazo/tomato-disease-prediction-mobilenetv3small-96-6)
  - [MobileNetV2](https://www.kaggle.com/code/luisolazo/tomato-disease-prediction-mobilenetv2-93-8)
  - [NASNetMobile](https://www.kaggle.com/code/luisolazo/tomato-disease-prediction-nasnetmobile-90-1)

## Prototype | Final design
- Prototype
![Prototype_TomateCare](https://github.com/vanskarner/TomateCare/assets/39975255/63f4c512-d11e-4acb-a7d2-81e3aaac44ea)
- Final design
![FinalDesign_TomateCare](https://github.com/vanskarner/TomateCare/assets/39975255/fdd72c38-6cda-4b3b-9711-5602f1624532)

## Tests
The analysis and diseases components have their tests, however for the UI component only manual tests have been performed.

| Component: Analysis | Component: Diseases |
| --- | --- |
| ![test_in_analysis](https://github.com/vanskarner/TomateCare/assets/39975255/3242e9af-f67e-44cc-a57d-ca681c86f0ee) | ![diseases](https://github.com/vanskarner/TomateCare/assets/39975255/23f8c8d2-1e4e-4960-80fb-c06003d9071f) |

## Discussions
Refer to the discussions section [HERE](https://github.com/vanskarner/TomateCare/discussions)
