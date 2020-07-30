package com.example.teampick

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.vo.Team
import com.example.teampick.vo.TeamVo
import com.example.teampick.database.AppDataBase
import com.example.teampick.pick.activity.TeamPickActivity
import com.example.teampick.setting.TeamSettingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        val onClick = initBtn()
        var teamPickBtn: Button = findViewById(R.id.toTeamPick)
        teamPickBtn.setOnClickListener(onClick)
        var teamSetBtn: Button = findViewById(R.id.toTeamSetting)
        teamSetBtn.setOnClickListener(onClick)

        //처음 앱 실행 할 경우 기존에 정햇던 96개 팀으로 셋팅
        Thread(Runnable {
            var database = AppDataBase.getInstance(this)
            var teamList: List<Team>? = database?.teamDao()?.getAllTeam()

            if (teamList != null) {
                if(teamList.isEmpty()){initTeam(database)}
            }else{
                initTeam(database)
            }
        }).start()




    }

    fun initTeam(database: AppDataBase?){
        var teamList: List<Team> = TeamVo().convertToTeam()
        database?.teamDao()?.insertTeam(*teamList.toTypedArray())
    }


    fun initBtn(): View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.toTeamPick -> {
                        val toIntent = Intent(applicationContext, TeamPickActivity::class.java)
                        startActivity(toIntent)
                    }
                    R.id.toTeamSetting ->{
                        val toIntent = Intent(applicationContext, TeamSettingActivity::class.java)
                        startActivity(toIntent)
                    }
                }
            }

}