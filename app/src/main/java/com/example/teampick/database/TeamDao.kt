package com.example.teampick.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.teampick.vo.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM team order by rate desc, teamName")
    fun getAllTeam(): List<Team>

    @Query("SELECT * FROM team WHERE teamId = :teamId")
    fun getTeamById(teamId: Long): List<Team>

    @Insert
    fun insertTeam(vararg team: Team)

    @Query("DELETE FROM team")
    fun deleteTeamAll()

    @Query("DELETE FROM team WHERE teamId = :teamId")
    fun deleteTeamById(teamId: Long)


}