# NSExceptionKt for Bugsnag

> Status: **experimental** 🚧  
> The basic scenarios have been tested, but the implementation hasn't been battle-tested just yet.

## Installation

First make sure you have [set up](https://docs.bugsnag.com/platforms/ios/#installation) Bugsnag (v6.20.0 or above).  
After that add the following dependency to your `iosMain` or `appleMain` source set.

```kotlin
implementation("com.rickclephas.kmp:nsexception-kt-bugsnag:<version>")
```

Make sure you have cinterop commonization enabled in your project-level `gradle.properties` otherwise you won't be able to resolve `BugsnagConfiguration`:

```
kotlin.mpp.enableCInteropCommonization=true
```

and create the `updateBugsnagConfig` and `setupBugsnag` functions (e.g. in your `AppInit.kt` file):

```kotlin
import com.rickclephas.kmp.nsexceptionkt.bugsnag.cinterop.BugsnagConfiguration

fun updateBugsnagConfig(config: BugsnagConfiguration) {
    configureBugsnag(config)
}

fun setupBugsnag() {
    setBugsnagUnhandledExceptionHook()
}
```

> Note: `configureBugsnag` and `setBugsnagUnhandledExceptionHook` are only available for Apple targets,
> so you can't create `updateBugsnagConfig` or `setupBugsnag` in `commonMain`.

Now go to your Xcode project and update your `AppDelegate`:

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        let config = BugsnagConfiguration.loadConfig()
        AppInitKt.updateBugsnagConfig(config: config)
        Bugsnag.start(with: config)
        AppInitKt.setupBugsnag() // should be called after configuring Bugsnag
        return true
    }
}
```

That's all, now go and crash that app!

## Caused by exceptions

Bugsnag has built-in support for caused by errors, so no additional configuration is required.

## Filtering the Kotlin termination

Bugsnag stores multiple fatal exceptions, so make sure to update your `BugsnagConfiguration` with the `configureBugsnag`
function mentioned above.

> Internally NSExceptionKt sets a feature flag after logging the unhandled Kotlin exception.  
> This feature flag is used to stop the fatal Kotlin termination from being sent to Bugsnag.
