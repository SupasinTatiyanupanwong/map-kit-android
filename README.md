# Map Kit

Abstraction wrapper that encapsulates maps APIs of supported platforms for Android, allowing access to multiple maps APIs while maintaining your's app single codebase.

Map Kit is currently provides support for Google Maps and Huawei Maps.

## Usage

This project contains 3 artifacts; `maps-core`, `maps-google`, and `maps-huawei`.

`maps-core` artifact provides abstraction interface to interact with Maps APIs.

`maps-google` artifact provides Google Maps integration.

`maps-huawei` artifact provides Huawei Maps integration.

### Migration from existing Maps APIs

| Google Name                      | Map Kit Name                                                 |
|:-------------------------------- |:------------------------------------------------------------ |
| ``com.google.android.gms.maps*`` | ``me.tatiyanupanwong.supasin.android.libraries.kits.maps.*`` |
| ``GoogleMap``                    | ``Map``                                                      |
| ``new *Options()``               | ``MapKit.getFactory().new*Options()``                        |
| ``new LatLng()``                 | ``MapKit.getFactory().newLatLng()``                          |
| ``*Factory.*()``                 | ``MapKit.getFactory().get*Factory().*()``                    |
| ``Tile.NO_TILE``                 | ``MapKit.getFactory().noTile()``                             |
| ``*.builder()``                  | ``MapKit.getFactory().new*Builder()``                        |

### Limitations

1. Consults with official documentation for their own limitations.
2. Models are currently not `Parcelable`.
3. `*MapOptions` is currently not supported.
4. Google's `StreetView` is currently not supported.

## Download

Add the following to your Gradle build file:

```groovy
android {
    ...
    flavorDimensions 'vendor'
    productFlavors {
        google
        huawei
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
    google 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-google:1.0.0'
    huawei 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-huawei:1.0.0'
}
```

`maps-core` may be used to provides Maps APIs interaction if needed, e.g. to pass `LatLng` object to different module.

However, make sure to have one of integration artifacts included in your final build, otherwise an exception will be thrown.

```groovy
dependencies {
    implementation 'me.tatiyanupanwong.supasin.android.libraries.kits.maps:maps-core:1.0.0'
}  
```


## License

```
Copyright (C) 2020 Supasin Tatiyanupanwong

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
