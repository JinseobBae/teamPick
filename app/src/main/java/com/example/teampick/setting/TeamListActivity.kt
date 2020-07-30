package com.example.teampick.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.teampick.R
import com.example.teampick.adapter.TeamListViewAdapter
import com.example.teampick.vo.Team
import com.example.teampick.database.AppDataBase

class TeamListActivity : AppCompatActivity() {

    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_team_list)

        listView = findViewById(R.id.list_team)
        var teamList = getTeamList()
        val adapter = TeamListViewAdapter(this, teamList)
        listView.adapter = adapter
    }

    private fun getTeamList() : ArrayList<String>{
        var result = ArrayList<String>()
        var teamList : List<Team>
        Thread(Runnable {
            teamList = AppDataBase.getInstance(this)?.teamDao()?.getAllTeam()!!
            for(team in teamList){
                result.add(team.teamName + "(" + team.level + ")")
            }
        }).start()

        return result
    }
}