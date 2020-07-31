package com.example.teampick.database

import android.content.Context
import com.example.teampick.vo.Team

class DBService(var context: Context) {

    public fun insertTeamDate(team: Team){
        Thread(Runnable {
            AppDataBase.getInstance(context)?.teamDao()?.insertTeam(team)
            AppDataBase.destroyInstance()
        }).start()
    }

    public fun deleteAllTeamData(){
        Thread(Runnable {
            AppDataBase.getInstance(context)?.teamDao()?.deleteTeamAll()
            AppDataBase.destroyInstance()
        }).start()
    }
}