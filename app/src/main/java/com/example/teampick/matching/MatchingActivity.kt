package com.example.teampick.matching

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.teampick.R
import com.example.teampick.utils.PickUtils
import java.util.*
import kotlin.collections.ArrayList

class MatchingActivity : Activity() {
    private val pickUtils = PickUtils()
    private val btnIdxList: MutableList<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matching_result)
        val fromIntent = intent
        val teamResult: HashMap<String, ArrayList<String>> = fromIntent.getSerializableExtra("teamResult") as HashMap<String, ArrayList<String>>
        val userList: ArrayList<String> = fromIntent.getSerializableExtra("userList") as ArrayList<String>
        val userEachIdx: MutableMap<String, MutableList<Int>> = HashMap()
        val resultList: MutableMap<String, Map<String, String>> = HashMap()
        val random = Random()
        random.setSeed(System.currentTimeMillis())
        val matchCnt = fromIntent.getIntExtra("roundCnt", 0)

        for (user in userList) {
            userEachIdx[user] = ArrayList()
        }

        for (i in 1..matchCnt) {
            val roundPick: MutableMap<String, String> = HashMap()
            for ((userName, value) in teamResult) {
                val userIndex = userEachIdx[userName]!! // 개인의 인덱스항목 가져오기
                val individualIndex = pickUtils.getIndex(matchCnt, random, userIndex) // 인덱스 가져오기
                roundPick[userName] = value[individualIndex] // 이번 라운드 팀 넣기
                userIndex.add(individualIndex) // 개인 인덱스에 추가
                userEachIdx[userName] = userIndex // 총 인덱스 관리 map에 추가
            }
            resultList[i.toString()] = roundPick
        }
        val resultMsgMap = makeMessages(resultList)
        makeTitle()
        makeButton(matchCnt, resultMsgMap)
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "뒤로가면 매칭정보 없어져서 일단 막아놨음. 다시 하려면 앱재실행", Toast.LENGTH_LONG).show()
    }

    fun makeMessages(teamResult: Map<String, Map<String, String>>): Map<String, String> {
        val messageMap: MutableMap<String, String> = HashMap()
        for ((key, value) in teamResult) {
            val msg = StringBuffer()
            for ((key1, value1) in value) {
                msg.append(key1).append(" : ").append(value1).append("\n")
            }
            messageMap[key] = msg.toString()
        }
        return messageMap
    }

    fun initBtnListener(idx: Int, msgMap: Map<String, String>): View.OnClickListener {
        return View.OnClickListener {
            val alert = AlertDialog.Builder(this@MatchingActivity)
            alert.setTitle("[$idx 라운드] 팀목록")
            alert.setMessage(msgMap[idx.toString()])
            alert.setPositiveButton("확인", null)
            alert.create().show()
        }
    }

    fun makeTitle() {
        val layout = findViewById<View>(R.id.matchingArea) as LinearLayout
        val layoutParams = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val textView = TextView(this@MatchingActivity)
        val id = View.generateViewId()
        textView.id = id
        textView.layoutParams = layoutParams
        textView.textSize = 70f
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.text = "<매칭 결과>"
        layout.addView(textView)
    }

    @SuppressLint("SetTextI18n")
    fun makeButton(cnt: Int, msgMap: Map<String, String>) {
        val dm = resources.displayMetrics
        val size = Math.round(20 * dm.density)
        val layout = findViewById<View>(R.id.matchingArea) as LinearLayout
        val layoutParams = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.topMargin = size
        for (idx in 1..cnt) {
            val btnView = Button(this@MatchingActivity)
            val id = View.generateViewId()
            btnView.id = id
            btnIdxList.add(id)
            btnView.layoutParams = layoutParams
            btnView.textSize = 20f
            btnView.gravity = Gravity.CENTER_HORIZONTAL
            btnView.isClickable = true
            btnView.setBackgroundColor(Color.rgb(228, 240, 237))
            btnView.text = "$idx 라운드"
            btnView.setOnClickListener(initBtnListener(idx, msgMap))
            layout.addView(btnView)
        }
    }
}