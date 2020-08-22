package com.example.teampick.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.teampick.R
import com.example.teampick.adapter.TeamListViewAdapter
import com.example.teampick.custom.swipe.SwipeCreator
import com.example.teampick.custom.swipe.SwipeListener
import com.example.teampick.database.AppDataBase
import com.example.teampick.database.DBService
import com.example.teampick.vo.Team

class TeamRemoveActivity : AppCompatActivity() {
    val db = DBService(this)
    lateinit var listView : SwipeMenuListView
    lateinit var mAdapter: TeamListViewAdapter
    lateinit var teamList: ArrayList<Team>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_team_list)
        initView()

    }
    fun initView() {

        val header = findViewById<TextView>(R.id.list_header)
        listView = findViewById<SwipeMenuListView>(R.id.list_team_swipe)
        listView.setMenuCreator(SwipeCreator(applicationContext))
        listView.setOnSwipeListener(SwipeListener(listView))
        listView.setOnMenuItemClickListener(initClickListener())
        header.text = "삭제 하실 팀을 스와이프 해주세요."
        teamList = getAllTeamList()
        mAdapter = TeamListViewAdapter(this, teamList)
        listView.adapter = mAdapter

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

    fun initClickListener():  SwipeMenuListView.OnMenuItemClickListener =
            SwipeMenuListView.OnMenuItemClickListener{ position: Int, menu: SwipeMenu, index: Int ->
                when (index) {
                    0 ->{
                        val id : Long = teamList[position].teamId ?: 0
                        db.deleteTeamByTeamId(id)
                        Toast.makeText(applicationContext, teamList[position].teamName + "이/가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        val toIntent = Intent(applicationContext, TeamSettingActivity::class.java)
                        startActivity(toIntent)
                    }
                }
                // false : close the menu; true : not close the menu
                return@OnMenuItemClickListener false;
            }
}