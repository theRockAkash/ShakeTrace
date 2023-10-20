# ShakeTrace
Published on JitPack :    [![](https://jitpack.io/v/theRockAkash/ShakeTrace.svg)](https://jitpack.io/#theRockAkash/ShakeTrace)


# Add dependencies in your project

#Settings.gradle file or Project level build.gradle file
```
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }  // add this line
		}
	}
```

#build.gradle file (app level)
```
dependencies {
	        implementation 'com.github.theRockAkash:ShakeTrace:latestVersion'  // replace with latest version e.g. 1.1.0
	}
```


#add PrettyLoggingInterceptor in your http client
```

 if (BuildConfig.DEBUG) {
            val prettyInterceptor = PrettyLoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .setCashDir(context.cacheDir)   //set casheDir to see logs in app on shake event 
                .log(VERBOSE)                  //or pass  File("/data/user/0/{your app packge name}/cache") in setCashDir function
            httpClient.addNetworkInterceptor(prettyInterceptor.build())   // add prettyInterceptor in http client
        }
```


#Add in application class
```
  override fun onCreate() {
        super.onCreate()

        ShakeTrace.init(this) // add this line in onCreate function of application class
    }
```
