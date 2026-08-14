package dev.mjpark.krtv.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Global {
    val gson = Gson()

    val typeTvList = object : TypeToken<List<TV>>() {}.type

    val typeSourceList = object : TypeToken<List<Source>>() {}.type

    val typeEPGMap = object : TypeToken<Map<String, List<EPG>>>() {}.type
}