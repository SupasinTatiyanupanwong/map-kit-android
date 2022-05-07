# Map Kit

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.supasintatiyanupanwong.libraries.android.kits.maps/maps-core/badge.svg)](https://search.maven.org/search?q=g:dev.supasintatiyanupanwong.libraries.android.kits.maps)
[![javadoc](https://javadoc.io/badge2/dev.supasintatiyanupanwong.libraries.android.kits.maps/maps-core/javadoc.svg)](https://javadoc.io/doc/dev.supasintatiyanupanwong.libraries.android.kits.maps/maps-core)
[![license](https://img.shields.io/github/license/SupasinTatiyanupanwong/map-kit-android.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Map Kit is an abstraction wrapper that encapsulates Android's Maps APIs of Google Maps SDK for Android, Amazon Maps SDK, and HUAWEI Map Kit.

## Architecture

The library consists of 4 artifacts; `maps-core`, `maps-google`, `maps-amazon`, and `maps-huawei`.

`maps-core` artifact provides an abstraction interface to interact with Maps APIs.

`maps-google` artifact provides the Google Maps SDK for Android integration to Map Kit.

`maps-amazon` artifact provides the Amazon Maps SDK integration to Map Kit.

`maps-huawei` artifact provides the HUAWEI Map Kit integration to Map Kit.

## Usage

### Migrating from the existing APIs

| Google Maps SDK          | Amazon Maps SDK          | HUAWEI Map Kit           | Map Kit                      |
|:------------------------ |:------------------------ |:------------------------ |:---------------------------- |
| ``SupportMapFragment``   | ``SupportMapFragment``   | ``SupportMapFragment``   | ``MapFragment``              |
| ``GoogleMap``            | ``AmazonMap``            | ``HuaweiMap``            | ``MapClient``                |
| ``new LatLng()``         | ``new LatLng()``         | ``new LatLng()``         | ``MapKit.newLatLng()``       |
| ``new *Options()``       | ``new *Options()``       | ``new *Options()``       | ``MapKit.new*Options()``     |
| ``*.builder()``          | ``*.builder()``          | ``*.builder()``          | ``MapKit.new*Builder()``     |
| ``*Factory.*()``         | ``*Factory.*()``         | ``*Factory.*()``         | ``MapKit.get*Factory().*()`` |
| ``TileProvider.NO_TILE`` | ``TileProvider.NO_TILE`` | ``TileProvider.NO_TILE`` | ``MapKit.noTile()``          |

### Limitation

* Models are not `Parcelable`.
* `MapOptions` is currently not supported.
* `StreetView` is currently not supported.

## Declaring dependencies

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

```groovy
dependencies {
    // Optional - To grant access to the Map Kit API without platform specific implementation
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-core:2.1.0'

    // To use the Google Maps SDK for Android via Map Kit
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-google:2.1.0'

    // To use the Amazon Maps SDK via Map Kit
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-amazon:2.1.0-alpha01'

    // To use the HUAWEI Map Kit via Map Kit
    implementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-huawei:2.1.0'
}
```

If both of integration artifacts are included in your final build, the implementation will be selected based on API availability upon application startup.

However, it is recommended to separate builds between them as next:

```groovy
android {
    // ...
    flavorDimensions 'vendor'
    productFlavors {
        google
        amazon { applicationIdSuffix '.amazon' }
        huawei { applicationIdSuffix '.huawei' }
    }
}

dependencies {
    googleImplementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-google:2.1.0'
    amazonImplementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-amazon:2.1.0-alpha01'
    huaweiImplementation 'dev.supasintatiyanupanwong.libraries.android.kits.maps:maps-huawei:2.1.0'
}
```

But, make sure to have one of the integration artifacts included in your final build, otherwise an exception will be thrown at runtime.

For more information about dependencies, see [Add build dependencies](https://developer.android.com/studio/build/dependencies).

## Additional documentation

* [Maps SDK for Android - Google Developer](https://developers.google.com/maps/documentation/android-sdk/intro)
* [Amazon Maps SDK - Amazon Appstore Developer](https://developer.amazon.com/docs/maps/understand.html)
* [Map Kit - HMS Core - HUAWEI Developer](https://developer.huawei.com/consumer/en/hms/huawei-MapKit)

## Feedback

Your feedback helps make Map Kit better. Let us know if you discover new issues or have ideas for improving this library.
Please take a look at the [existing issues](https://github.com/SupasinTatiyanupanwong/map-kit-android/issues) or the [existing discussions](https://github.com/SupasinTatiyanupanwong/map-kit-android/discussions) in this library before you create a new one.

## License

```
Copyright 2020 Supasin Tatiyanupanwong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
