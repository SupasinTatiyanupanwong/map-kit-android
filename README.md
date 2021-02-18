# Map Kit

[![Download](https://api.bintray.com/packages/supasintatiyanupanwong/android.libraries.kits.maps/maps-core/images/download.svg)](https://bintray.com/supasintatiyanupanwong/android.libraries.kits.maps/maps-core/_latestVersion)
[![javadoc](https://javadoc.io/badge2/me.tatiyanupanwong.supasin.android.libraries.kits.maps/maps-core/javadoc.svg)](https://javadoc.io/doc/me.tatiyanupanwong.supasin.android.libraries.kits.maps/maps-core)
[![license](https://img.shields.io/github/license/SupasinTatiyanupanwong/map-kit-android.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Abstraction wrapper that encapsulates Maps APIs of supported platforms for Android, allowing access to multiple Maps APIs while maintaining your application single codebase.

Map Kit is currently providing support for Google Maps and Huawei Maps.

## Usage

This project contains 3 artifacts; `maps-core`, `maps-google`, and `maps-huawei`.

`maps-core` artifact provides abstraction interface to interact with Maps APIs.

`maps-google` artifact provides Google Maps integration.

`maps-huawei` artifact provides Huawei Maps integration.

### Migration from existing Maps APIs

| Google Name                       | Huawei Name               | Map Kit Name                                                 |
|:--------------------------------- |:------------------------- |:------------------------------------------------------------ |
| ``com.google.android.gms.maps.*`` | ``com.huawei.hms.maps.*`` | ``me.tatiyanupanwong.supasin.android.libraries.kits.maps.*`` |
| ``GoogleMap``                     | ``HuaweiMap``             | ``MapClient``                                                |
| ``new LatLng()``                  | ``new LatLng()``          | ``MapKit.newLatLng()``                                       |
| ``new *Options()``                | ``new *Options()``        | ``MapKit.new*Options()``                                     |
| ``*.builder()``                   | ``*.builder()``           | ``MapKit.new*Builder()``                                     |
| ``*Factory.*()``                  | ``*Factory.*()``          | ``MapKit.get*Factory().*()``                                 |
| ``Tile.NO_TILE``                  | ``Tile.NO_TILE``          | ``MapKit.noTile()``                                          |

### Limitations

1. Models are currently not `Parcelable`.
2. `*MapOptions` is currently not supported.
3. Google's `StreetView` is currently not supported.

### Additional documentation

* [Maps SDK for Android - Google Developer](https://developers.google.com/maps/documentation/android-sdk/intro)
* [Map Kit - HMS Core - HUAWEI Developer](https://developer.huawei.com/consumer/en/hms/huawei-MapKit)

## Download

Add the following to your Gradle build file:

```groovy
dependencies {
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-google:1.2.2'
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-huawei:1.2.2'
}
```

If both of integration artifacts are included in your final build, the implementation will be selected based on API availability upon application startup.

However, it is recommended to separate builds between them as next:

```groovy
android {
    ...
    flavorDimensions 'vendor'
    productFlavors {
        google
        huawei { applicationIdSuffix '.huawei' }
    }
}

configurations {
    google
    huawei

    googleImplementation.extendsFrom(google)
    googleCompileOnly.extendsFrom(huawei)

    huaweiImplementation.extendsFrom(huawei)
    huaweiCompileOnly.extendsFrom(google)
}

dependencies {
    google 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-google:1.2.2'
    huawei 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-huawei:1.2.2'
}
```

`maps-core` may be used to provides Maps APIs interaction if needed, e.g. to pass `LatLng` object to different module.

```groovy
dependencies {
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-core:1.2.1'
}  
```

However, make sure to have one of integration artifacts included in your final build, otherwise an exception will be thrown.

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
