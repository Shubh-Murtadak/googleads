package com.example.video_ad_sample_project

import com.example.video_ad_sample_project.FullPageNativeAdFactory
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.googlemobileads.GoogleMobileAdsPlugin

class MainActivity: FlutterActivity() {

     override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
         super.configureFlutterEngine(flutterEngine)

         // TODO: Register the FullPageNativeAdFactory
         GoogleMobileAdsPlugin.registerNativeAdFactory(flutterEngine, "fullPage",
             FullPageNativeAdFactory(context)
         )

     }

     override fun cleanUpFlutterEngine(flutterEngine: FlutterEngine) {
         super.cleanUpFlutterEngine(flutterEngine)

         // TODO: Unregister the FullPageNativeAdFactory
         GoogleMobileAdsPlugin.unregisterNativeAdFactory(flutterEngine, "fullPage")

     }
}