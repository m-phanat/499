package com.example.m.mproject499.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


class Words(var word: String, var meaning: String,var desc_eng:String,var desc_th:String)

@IgnoreExtraProperties
data class WordFireBase(
    var word: String = "",
    var meaning: String = "",
    var desc_eng:String = "",
    var desc_th:String ="",
    var day:Int = 0,
    var position:Int = 0,
    var choice: MutableList<String>
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "word" to word,
            "meaning" to meaning,
            "desc_eng" to desc_eng,
            "desc_th" to desc_th,
            "day" to day,
            "position" to position,
            "choice" to choice
        )
    }
}

data class Salad(
    val name: String = "",
    val description: String = "",
    var uuid: String = "")