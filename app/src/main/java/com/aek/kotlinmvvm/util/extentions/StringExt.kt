package com.aek.kotlinmvvm.util.extentions

fun String?.ignoreNull(default: String = ""): String = this ?: default
