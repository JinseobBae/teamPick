package com.example.teampick.utils

import java.util.*

class PickUtils {
    fun getIndex(maxSize: Int, random: Random, seqSet: List<Int?>): Int {
        var index = -1
        try {
            var isValid = false
            while (!isValid) {
                index = random.nextInt(maxSize)
                if (checkIndexValid(index, seqSet)) {
                    isValid = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return index
    }

    fun checkIndexValid(index: Int, seqSet: List<Int?>): Boolean {
        var valid = true
        if (seqSet.contains(index)) {
            valid = false
        }
        return valid
    }

    fun getTeamLevelRate(level : String) : Int{
        var rate = 0
        when (level) {
            "Z" -> rate = 5
            "S" -> rate = 5
            "A" -> rate = 4
            "B" -> rate = 3
            "C" -> rate = 2
            "D" -> rate = 1
        }
        return rate
    }
}