package com.aek.kotlinmvvm.util.extentions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String?) {
    Glide.with(context).load(url.ignoreNull()).into(this)
}
