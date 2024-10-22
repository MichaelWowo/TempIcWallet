package com.android.identity.wallet.util

import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.databind.ObjectMapper


object JsonUtil {
    fun decodeJsonLd(jsonLdString: String): Map<Any, Any> {
        return ObjectMapper().readValue<MutableMap<Any, Any>>(jsonLdString)
    }
}
