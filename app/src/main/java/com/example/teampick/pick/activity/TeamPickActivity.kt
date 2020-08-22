package com.example.teampick.pick.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.R
import java.util.*

class TeamPickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_pick)
        val receiveIntent = intent
        var userList: ArrayList<String?>? = ArrayList()

        if(receiveIntent != null){
            if (receiveIntent.hasExtra("numOfUser")) {
                val numUser = findViewById<View>(R.id.numofpeople) as EditText
                numUser.setText(receiveIntent.getIntExtra("numOfUser", 0).toString())
                userList = receiveIntent.getSerializableExtra("userList") as ArrayList<String?>?
            }

            if (receiveIntent.hasExtra("roundCount")){
                val roundCount = findViewById<View>(R.id.roundNum) as EditText
                roundCount.setText(receiveIntent.getIntExtra("roundCount", 0).toString())
            }
        }

        val onClick = initBtn(userList)
        val pickTeamBtn = findViewById<View>(R.id.pickTeam) as Button
        pickTeamBtn.setOnClickListener(onClick)
        val userBtn = findViewById<View>(R.id.setUser) as Button
        userBtn.setOnClickListener(onClick)
    }

    fun initBtn(userList: ArrayList<String?>?): View.OnClickListener =
         View.OnClickListener { view ->
            val numberOfPeople = findViewById<View>(R.id.numofpeople) as EditText
            val roundCount = findViewById<View>(R.id.roundNum) as EditText
            when (view.id) {
                R.id.pickTeam ->
                    if (userList != null && userList.size > 0 && roundCount.text.toString() != "") {
                        val intentToResult = Intent(applicationContext, PickResultActivity::class.java)
                        intentToResult.putExtra("userList", userList)
                        intentToResult.putExtra("roundCount", roundCount.text.toString().toInt())
                        startActivity(intentToResult)
                    } else if(userList == null || userList.size == 0) {
                        Toast.makeText(applicationContext, "사용자 입력을 해주세요.", Toast.LENGTH_LONG).show()
                    } else if(roundCount.text.toString() == ""){
                        Toast.makeText(applicationContext, "라운드를 입력해주세요.", Toast.LENGTH_LONG).show()
                    } else{
                        Toast.makeText(applicationContext, "뭔가 설정안된 항목이 있습니다.", Toast.LENGTH_LONG).show()
                    }
                R.id.setUser -> {
                    var numOf = 0
                    try {
                        numOf = numberOfPeople.text.toString().toInt()
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                        Toast.makeText(this@TeamPickActivity, "인원수를 입력하세요.", Toast.LENGTH_LONG).show()
                    }
                    if (numOf > 0) {
                        val intent = Intent(applicationContext, SetUserActivity::class.java)
                        var round : String? = roundCount.text?.toString()
                        if(round != null && round != ""){
                            intent.putExtra("roundCount", roundCount.text.toString().toInt())
                        }
                        intent.putExtra("numOfPeople", numOf)
                        intent.putExtra("userList", userList)
                        startActivity(intent)
                    }
                }
            }
        }


    fun confirmDialog(msg: String?): Boolean {
        val result = false
        AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes) { dialog, whichButton -> // 확인시 처리 로직
                    finish()
                }
                .setNegativeButton(android.R.string.no) { dialog, whichButton -> // 취소시 처리 로직
                    Toast.makeText(this@TeamPickActivity, "취소하였습니다.", Toast.LENGTH_SHORT).show()
                }
                .show()
        return result
    }


}