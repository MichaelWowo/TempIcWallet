package com.android.identity.wallet.util

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object FileUtil {

    fun readFileFromAssets(appContext: Context, fileName: String): String {
        return appContext.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
    }

}