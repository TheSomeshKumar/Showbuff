# TMDB-MVVM-GUIDE
Plaground project based on [Recommended app architecture](https://developer.android.com/jetpack/guide)

Powered by

<img width="260" alt="TMDB Logo" src="https://user-images.githubusercontent.com/13759258/213716452-837c217d-49ac-442c-b7eb-5f1a76dff614.png">


## Features 🕹
- 100% Kotlin
- Following [Recommended MVVM Architecture](https://developer.android.com/jetpack/guide)
- [Coroutines](https://developer.android.com/kotlin/coroutines) - Coroutines provide us an easy way to do synchronous and asynchronous programming.
- [Flow](https://developer.android.com/kotlin/flow) & [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - Flow is a type of coroutine that emits multiple values sequentially.
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection framework
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - View Binding
- [Retrofit](https://github.com/square/retrofit) - Network client
- [Material You](https://m3.material.io) & - [Material Motion](https://m2.material.io/develop/android/theming/motion)  - Material Design



## Work In Progress 🚧
- ~~Material Motion~~ [Done](https://github.com/TheSomeshKumar/TMDB-MVVM-ARCH/commit/c177f92968f341bd7a0592b83215fe2d753b561d) ✅
- ~~Use Material Theme 3 (MaterialYou)~~  [Done](https://github.com/TheSomeshKumar/TMDB-MVVM-ARCH/commit/7e2314fe575e400f7390fb4d15ad43e1ef0039ff) ✅
- Tests
- Jetpack Compose


## Screenshot 📱
<img width="310" alt="M3 Dark Home Screen" src="https://user-images.githubusercontent.com/13759258/217895345-e633e053-0a20-49e2-a247-dfc575a0dcc1.png"><img width="310" alt="M3 Light Home Screen" src="https://user-images.githubusercontent.com/13759258/217895353-369ad9b1-ec82-4f13-b9a9-b97ff1d12c90.png">


### How to build on your environment
Create an API key on [The Movie DB](https://www.themoviedb.org)'s and add in your local `local.properties` file like this
```
TMDB_KEY=<REPLACE_WITH_YOUR_API_KEY>
```