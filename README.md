# Android | Jetpack WorkManager | Demo

[![Kotlin Version](https://img.shields.io/badge/kotlin-1.6.10-blue.svg?style=for-the-badge)](http://kotlinlang.org/)
[![Gradle](https://img.shields.io/badge/gradle-7.2-blue.svg?style=for-the-badge)](https://lv.binarybabel.org/catalog/gradle/latest)
[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=for-the-badge)](https://android-arsenal.com/api?level=23)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=for-the-badge)](http://www.apache.org/licenses/LICENSE-2.0)

[![CodeFactor](https://www.codefactor.io/repository/github/mobiledevpro/android-workmanager-demo/badge)](https://www.codefactor.io/repository/github/mobiledevpro/android-workmanager-demo)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mobiledevpro_Android-WorkManager-Demo&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mobiledevpro_Android-WorkManager-Demo)

![GitHub last commit](https://img.shields.io/github/last-commit/mobiledevpro/Android-WorkManager-Demo?color=red&style=for-the-badge)

##

_WorkManager is the recommended solution for persistent work. Work is persistent when it remains scheduled through app restarts and system reboots. Because most background processing is best accomplished through persistent work, WorkManager is the primary recommended API for background processing._ [Read more in official docs](https://developer.android.com/topic/libraries/architecture/workmanager)

##

## 3 Steps to run periodic tasks in the background even the app is closed:

### #1 Init Jetpack WorkManager 

```kotlin
WorkManager.getInstance(applicationContext)
```

### #2 Setup Worker 

```kotlin
class PriceAlerterWorker(
    appContext: Context,
    params: WorkerParameters,
    private val interactor: PriceAlerterInteractor
) : RxWorker(appContext, params) {

    override fun createWork(): Single<Result> =
        interactor
            .createDemoAlert()
            .map { result ->
                when (result) {
                    is RxResult.Success -> {
                        //Do something on success
                        Result.success()
                    }
                    is RxResult.Failure -> {
                        //Do something on fail
                        Result.retry()
                    }
                }
            }
}

```

### #3 Build request and run work

```kotlin
        PeriodicWorkRequestBuilder<PriceAlerterWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(WORKER_PRICE_ALERT_TAG)
            .build()
            .let { request ->
                workManager.runUniqueWork(
                    request,
                    "${WORKER_PRICE_ALERT_TAG}_periodic"
                )
            }
```


## More about WorkManager: 

### [WorkManager basics (article)](https://medium.com/androiddevelopers/workmanager-basics-beba51e94048)

### [WorkManager custom configuration and WorkerFactory (article)](https://medium.com/androiddevelopers/customizing-workmanager-fundamentals-fdaa17c46dd2)

### [Koin 3 + WorkManager (article)](https://medium.com/koin-developers/whats-next-with-koin-2-2-3-0-releases-6c5464ae5e3d)

## Notes:

+ The minimal interval for periodic tasks is 15 minutes, even if you set 1 min.
+ Retry with Backoff policy supports the minimum 10 sec and the maximum 5 hours interval (30 sec by
  default).

## Authors:

<a href="https://www.instagram.com/mobiledevpro/" target="_blank">
  <img src="https://s.gravatar.com/avatar/72c649d298a8f0f088fd0850e19b9147?s=400" width="70" align="left">
</a>

**Dmitri Chernysh**

[![Instagram](https://img.shields.io/badge/-instagram-E4405F?logo=instagram&message=Behind+the+scenes+in+Storiesn&style=for-the-badge&logoColor=white)](https://www.instagram.com/mobiledevpro/)
[![Youtube](https://img.shields.io/badge/-youtube-red?logo=youtube&message=Youtube&style=for-the-badge)](https://www.youtube.com/@mobiledevpro?sub_confirmation=1)
[![Twitter](https://img.shields.io/badge/-twitter-1DA1F2?logo=twitter&style=for-the-badge&logoColor=white)](https://twitter.com/mobiledev_pro)
[![Linkedin](https://img.shields.io/badge/-linkedin-0A66C2?logo=linkedin&style=for-the-badge&logoColor=white)](https://www.linkedin.com/in/dmitriychernysh/)
[![Upwork](https://img.shields.io/badge/-upwork-brightgreen?logo=upwork&message=Upwork&style=for-the-badge)](https://www.upwork.com/freelancers/~01fb21586ed544f07b?s=996364627857502209)

## License:

   Copyright 2021 Dmitriy Chernysh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
