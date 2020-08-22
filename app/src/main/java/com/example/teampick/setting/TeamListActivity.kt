package com.example.teampick.setting

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.R
import com.example.teampick.adapter.TeamListViewAdapter
import com.example.teampick.database.AppDataBase
import com.example.teampick.database.DBService
import com.example.teampick.vo.Team


class TeamListActivity : AppCompatActivity() {

    val db = DBService(this)
    lateinit var listView : ListView
    lateinit var mAdapter: TeamListViewAdapter
    lateinit var teamList: ArrayList<Team>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_team_list)
        initView()
    }

    fun initView() {
        val header = findViewById<TextView>(R.id.list_header)
        listView = findViewById<ListView>(R.id.list_team)
        header.text = "팀 목록"
        teamList = getAllTeamList()
        mAdapter = TeamListViewAdapter(this, teamList)
        listView.setAdapter(mAdapter)
    }

    private fun getAllTeamList() : ArrayList<Team>{
        var result = ArrayList<Team>()
        var teamList : List<Team>
        Thread(Runnable {
            teamList = AppDataBase.getInstance(this)?.teamDao()?.getAllTeam()!!
            for(team in teamList){
                result.add(team)
            }
        }).start()
        return result
    }
}