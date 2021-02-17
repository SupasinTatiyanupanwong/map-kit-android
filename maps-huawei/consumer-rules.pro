-keep class me.tatiyanupanwong.supasin.android.libraries.kits.maps.internal.huawei.model.HuaweiMapFactory {
    public static *** buildIfSupported(android.content.Context);
}

# https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/configuration-obfuscation-script
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
