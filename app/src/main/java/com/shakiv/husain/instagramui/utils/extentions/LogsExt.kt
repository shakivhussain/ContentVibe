package com.shakiv.husain.instagramui.utils.extentions

import timber.log.Timber


fun logd(message: String, tag: String = "CurrentTag") {
    Timber.tag(tag).d(message)
}

fun loge(throwable: Throwable, tag: String = "CurrentTag") {
    Timber.tag(tag).e(throwable)
}

fun logi(message: String, tag: String = "CurrentTag") {
    Timber.tag(tag).e(message)
}