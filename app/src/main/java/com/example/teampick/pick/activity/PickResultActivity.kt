package com.example.teampick.pick.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.utils.HtmlTag
import com.example.teampick.matching.MatchingActivity
import com.example.teampick.R
import com.example.teampick.vo.TeamVo
import com.example.teampick.pick.service.TeamPickService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class PickResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pick_result)
//        val intent = intent
        val userList: ArrayList<String> = intent.getSerializableExtra("userList") as ArrayList<String>
        val teamVo = TeamVo()
        teamVo.initTeamLevelList()
        if (userList != null) {
            val pickService = TeamPickService()
            val roundCnt = intent.getIntExtra("roundCount", 0)
            val teamPickResult = pickService.pickTeam(userList, roundCnt)
            val ratingMap: MutableMap<Double, String?> = HashMap()
            val ratingList: MutableList<Double> = ArrayList()
            for ((key, value) in teamPickResult) {
                var rating = makeRating(value, teamVo)
                if (ratingList.contains(rating)) {
                    rating += 0.001
                }
                ratingMap[rating] = key
                ratingList.add(rating)
            }

            ratingList.sortWith(Comparator { o1, o2 -> o2.compareTo(o1) })
            makeTitle()
            for (seq in ratingList) {
                val key = ratingMap[seq]
                val teamValues: List<String>? = teamPickResult[key]
                if (teamValues != null && teamValues.size > 0) {
                    makeTextArea(key, teamValues, teamVo, seq)
                }
            }
            val fb = findViewById<View>(R.id.fab) as FloatingActionButton
            val fbListener = fbListener(teamPickResult, userList, roundCnt)
            fb.setOnClickListener(fbListener)
        }
    }

    fun fbListener(teamPickResult: HashMap<String, ArrayList<String>>, userList: ArrayList<String>, roundCnt: Int): View.OnClickListener =
         View.OnClickListener {
            val toIntent = Intent(applicationContext, MatchingActivity::class.java)
            toIntent.putExtra("teamResult", teamPickResult)
            toIntent.putExtra("userList", userList)
            toIntent.putExtra("roundCnt", roundCnt)
            startActivity(toIntent)
        }


    fun makeTitle() {
        val layout = findViewById<View>(R.id.resultArea) as LinearLayout
        val layoutParams = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val textView = TextView(this@PickResultActivity)
        val id = View.generateViewId()
        textView.id = id
        textView.layoutParams = layoutParams
        textView.textSize = 70f
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.text = "<추첨 결과>"
        layout.addView(textView)
    }

    fun makeTextArea(userName: String?, teamList: List<String>, teamVo: TeamVo, rating: Double) {
        val layout = findViewById<View>(R.id.resultArea) as LinearLayout
        val layoutParams = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = 40
        val resultMsg = StringBuffer()
        resultMsg.append("<font color=\"#5F00FF\" size=\"15px\"> [$userName] ")
                .append(getRatingScore(rating))
                .append("</font>")
        resultMsg.append(HtmlTag.enter)
        var cnt = 1
        for (i in teamList.indices) {
            val teamName = msgWithColor(teamList[i], teamVo)
            if (cnt % 4 == 0) {
                resultMsg.append("&nbsp;&nbsp;").append(teamName).append(HtmlTag.enter)
            } else {
                resultMsg.append("&nbsp;&nbsp;").append(teamName)
            }
            cnt++
        }
        val textView = TextView(this@PickResultActivity)
        val id = View.generateViewId()
        textView.id = id
        textView.layoutParams = layoutParams
        textView.setPadding(50, 50, 20, 20)
        textView.left = 100
        textView.textSize = 14f
        textView.text = Html.fromHtml(resultMsg.toString(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        layout.addView(textView)
        val sharedPreferences = getSharedPreferences("A", Context.MODE_PRIVATE)
    }

    fun msgWithColor(msg: String, teamVo: TeamVo): String =
        when (teamVo.getTeamLevel(msg)) {
            "S" -> "<font color=\"blue\">$msg</font>"
            "Z" -> "<font color=\"blue\">$msg</font>"
            "D" -> "<font color=\"red\">$msg</font>"
            else -> msg
        }


    fun makeRating(userTeam: List<String>, teamVo: TeamVo): Double {
        var rating = 0.0
        for (team in userTeam) {
            rating += when (teamVo.getTeamLevel(team)) {
                "S" -> 5.0
                "A" -> 4.0
                "B" -> 3.0
                "C" -> 2.0
                "D" -> 1.0
                "Z" -> 5.0
                else -> 0.0
            }
        }
        if (rating > 0) {
            rating = rating / userTeam.size
        }
        return Math.round(rating * 100000) / 100000.0
    }

    fun getRatingScore(rating: Double): String {
        val result = StringBuffer()
        val ratingRound = Math.round(rating * 100) / 100.0
        if (ratingRound >= 5) {
            for (i in 0..4) {
                result.append(HtmlTag.fullStarHtml)
            }
        } else if (ratingRound >= 4.5) {
            for (i in 0..3) {
                result.append(HtmlTag.fullStarHtml)
            }
            result.append(HtmlTag.halfStarHtml)
        } else if (ratingRound >= 4) {
            for (i in 0..3) {
                result.append(HtmlTag.fullStarHtml)
            }
        } else if (ratingRound >= 3.5) {
            for (i in 0..2) {
                result.append(HtmlTag.fullStarHtml)
            }
            result.append(HtmlTag.halfStarHtml)
        } else if (ratingRound >= 3) {
            for (i in 0..2) {
                result.append(HtmlTag.fullStarHtml)
            }
        } else if (ratingRound >= 2.5) {
            for (i in 0..1) {
                result.append(HtmlTag.fullStarHtml)
            }
            result.append(HtmlTag.halfStarHtml)
        } else if (ratingRound >= 2) {
            for (i in 0..1) {
                result.append(HtmlTag.fullStarHtml)
            }
        } else if (ratingRound >= 1.5) {
            result.append(HtmlTag.fullStarHtml)
            result.append(HtmlTag.halfStarHtml)
        } else if (ratingRound >= 1) {
            result.append(HtmlTag.fullStarHtml)
        }
        result.append("(").append(ratingRound).append(")")
        return result.toString()
    }
}