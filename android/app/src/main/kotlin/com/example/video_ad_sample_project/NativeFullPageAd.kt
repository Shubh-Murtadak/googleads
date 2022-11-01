package com.example.video_ad_sample_project

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.flutter.plugins.googlemobileads.GoogleMobileAdsPlugin


class FullPageNativeAdFactory(private val appContext: Context) :
    GoogleMobileAdsPlugin.NativeAdFactory {

    override fun createNativeAd(
        nativeAd: NativeAd,
        customOptions: MutableMap<String, Any>?,
    ): NativeAdView {



            val nativeAdView = LayoutInflater.from(appContext)
                .inflate(R.layout.full_page_xml, null) as NativeAdView

            with(nativeAdView) {




                setMainImage(this, nativeAd)
                val adHeadingTV = findViewById<TextView>(R.id.tv_ad_heading)
                adHeadingTV.text = nativeAd.headline
                nativeAdView.headlineView = adHeadingTV

                val adBodyTV = findViewById<TextView>(R.id.tv_ad_body)

                if (nativeAd.callToAction == null)
                    adBodyTV.text = nativeAd.body
                else {
                    val wordToSpan: Spannable =
                        SpannableString(nativeAd.body!! + "  " + nativeAd.callToAction)

                    val ctaColor =
                         Color.parseColor("#ff004FC4")


                    wordToSpan.setSpan(ForegroundColorSpan(ctaColor),
                        nativeAd.body!!.length,
                        nativeAd.body!!.length + nativeAd.callToAction!!.length + 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    wordToSpan.setSpan(RelativeSizeSpan(1.12f),
                        nativeAd.body!!.length,
                        nativeAd.body!!.length + nativeAd.callToAction!!.length + 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


                    adBodyTV.text = wordToSpan
                }
                nativeAdView.bodyView = adBodyTV


                val adCTATV = findViewById<TextView>(R.id.tv_ad_cta)
                if (nativeAd.callToAction != null) {
                    adCTATV.visibility = VISIBLE
                    adCTATV.text = nativeAd.callToAction
                    nativeAdView.callToActionView = adCTATV
                } else {
                    adCTATV.visibility = GONE
                }


                val iconIV = findViewById<ImageView>(R.id.iv_ad_icon)
                setAdIcon(nativeAd, iconIV, nativeAdView)

            }

            nativeAdView.setNativeAd(nativeAd)
            return nativeAdView

    }

     private fun setAdIcon(nativeAd: NativeAd, iconIV: ImageView, nativeAdView: NativeAdView) {
         val drawable = nativeAd.icon?.drawable
         if (drawable != null) {
             iconIV.visibility = VISIBLE
             iconIV.setImageDrawable(drawable)
         } else {
             iconIV.visibility = GONE
             iconIV.setImageResource(0)
         }
         nativeAdView.iconView = iconIV
     }


     private fun setMainImage(nativeAdView: NativeAdView, nativeAd: NativeAd) = with(nativeAdView) {
         nativeAdView.mediaView = nativeAdView.findViewById(R.id.ad_media) as MediaView
         if (nativeAd.mediaContent != null)
             nativeAdView.mediaView!!.setMediaContent(nativeAd.mediaContent!!)

     }

}
