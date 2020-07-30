package com.example.teampick.pick.service

import com.example.teampick.vo.TeamVo
import java.util.*

class TeamPickService {
    fun pickTeam(userList: List<String>, roundCnt: Int): HashMap<String, ArrayList<String>> {
        val result = HashMap<String, ArrayList<String>>()
        val team = TeamVo()
        val teamList = team.teamList
        val seqSet: MutableList<Int> = ArrayList()
        val random = Random()
        val loopTimes = if (roundCnt > 0) roundCnt else teamList.size / userList.size
        val maxSize = teamList.size
        random.setSeed(System.currentTimeMillis())
        for (i in userList.indices) {
            val userTeam = ArrayList<String>()
            for (y in 0 until loopTimes) {
                val idx = getIndex(maxSize, random, seqSet)
                seqSet.add(idx)
                userTeam.add(teamList[idx])
            }
            result[userList[i]] = userTeam
        }
        return result
    }

    private fun getIndex(maxSize: Int, random: Random, seqSet: List<Int>): Int {
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

    private fun checkIndexValid(index: Int, seqSet: List<Int>): Boolean {
        var valid = true
        if (seqSet.contains(index)) {
            valid = false
        }
        return valid
    }
}