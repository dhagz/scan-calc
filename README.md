# ScanCalc

## Topics covered by this assignment:
- Support of multiple app variants.
- Controlling the behavior of the app at compile time.
- Handling different themes.
- Integration with a 3rd party library.
- Integration with the system (file picker, camera).
- Permission handling (file picker, camera).

## Libraries Used
- ML Kit Text Recognition - https://developers.google.com/ml-kit/vision/text-recognition/android
- CameraX - https://developer.android.com/training/camerax
- Hilt - https://developer.android.com/training/dependency-injection/hilt-android
- Jetpack Compose - https://developer.android.com/jetpack/compose
- Room - https://developer.android.com/jetpack/androidx/releases/room

## Known Issue
List of results are delayed and I am not sure why. 

In `ScanListScreen` I am expecting:
`val pagingItemsList = scannedListViewModel.getScanData().observeAsState()` to be updated once a new record is added to the database, and would recompose the UI. 

Unfortunately it's not performing as expected.
