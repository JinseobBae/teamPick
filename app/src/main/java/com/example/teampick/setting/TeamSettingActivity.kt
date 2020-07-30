package com.example.teampick.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.teampick.R

class TeamSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_setting)

        val onClick = initBtn()

        var teamListBtn: Button = findViewById(R.id.teamList)
        teamListBtn.setOnClickListener(onClick)
    }


    fun initBtn() : View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.teamList -> {
                        val toIntent = Intent(applicationContext, TeamListActivity::class.java)
                        startActivity(toIntent)
                    }
                }
            }
}