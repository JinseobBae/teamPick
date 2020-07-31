package com.example.teampick.pick.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.teampick.R
import java.util.*

class SetUserActivity : AppCompatActivity() {
    var numOfUserGlobal = 0
    var idList: MutableList<Int> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        idList.clear()
        val onClickListener = initBtn()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_set)
        val intent = intent
        var numOfUser = 0
        if (intent != null) {
            numOfUser = intent.getIntExtra("numOfPeople", 0)
        }
        if (numOfUser > 0) {
            numOfUserGlobal = numOfUser
            val layout = findViewById<View>(R.id.userNameArea) as LinearLayout
            val layoutParams = FrameLayout.LayoutParams(600, LinearLayout.LayoutParams.WRAP_CONTENT)
            val textViewLayoutParams = FrameLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.leftMargin = 100
            textViewLayoutParams.topMargin = 15
            textViewLayoutParams.leftMargin = 50
            for (i in 0 until numOfUser) {
                val textView = TextView(this@SetUserActivity)
                textView.layoutParams = textViewLayoutParams
                textView.textSize = 20f
                textView.text = Html.fromHtml("<font color=\"teal\">플레이어" + (i + 1) + "</font>", Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                layout.addView(textView)
                val editText = EditText(this@SetUserActivity)
                val id = View.generateViewId()
                idList.add(id)
                editText.id = id
                editText.layoutParams = layoutParams
                editText.left = 100
                editText.textSize = 18f
                editText.hint = "이름을 입력해주세요."
                editText.setBackgroundResource(R.drawable.custom_edittext)
                layout.addView(editText)
            }
            val submit = findViewById<View>(R.id.userSubmit) as Button
            submit.setOnClickListener(onClickListener)
        }
    }

    fun initBtn(): View.OnClickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.userSubmit -> {
                    val userList = ArrayList<String>()
                    val intent = Intent(applicationContext, TeamPickActivity::class.java)
                    for (id in idList) {
                        val user = findViewById<View>(id) as EditText
                        val userName = user.text.toString()
                        if (userName != "") {
                            userList.add(userName)
                        } else {
                            break
                        }
                    }
                    if (userList.size == numOfUserGlobal) {
                        intent.putExtra("userList", userList)
                        intent.putExtra("numOfUser", numOfUserGlobal)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "모든 사용자를 입력해주세요,", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

}