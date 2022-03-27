package com.thesomeshkumar.pixelvideos.util

import android.content.Context
import com.thesomeshkumar.pixelvideos.R
import com.thesomeshkumar.pixelvideos.data.common.RemoteSourceException
import okhttp3.ResponseBody

fun RemoteSourceException.getError(context: Context): String {
    return when (messageResource) {
        is Int -> return context.getString(messageResource)
        is ResponseBody? -> return messageResource!!.string()
        is String -> return messageResource
        else -> context.getString(R.string.error_unexpected_message)
    }
}
