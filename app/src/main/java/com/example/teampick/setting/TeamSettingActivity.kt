package com.example.teampick.setting

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.teampick.R
import com.example.teampick.custom.TeamAddDialog
import com.example.teampick.database.AppDataBase
import com.example.teampick.database.DBService


class TeamSettingActivity : AppCompatActivity() {

    val db = DBService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_setting)

        val onClick = initBtn()

        var teamListBtn = findViewById<Button>(R.id.teamList)
        teamListBtn.setOnClickListener(onClick)

        var clearBtn = findViewById<Button>(R.id.clearTeam)
        clearBtn.setOnClickListener(onClick)

        var addBtn = findViewById<Button>(R.id.addTeam)
        addBtn.setOnClickListener(onClick)
    }


    fun initBtn() : View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.addTeam -> {
                        var addDialog = TeamAddDialog(this)
                        addDialog.show()
                    }

                    R.id.teamList -> {
                        val toIntent = Intent(applicationContext, TeamListActivity::class.java)
                        startActivity(toIntent)
                    }

                    R.id.clearTeam -> {
                        var dialog = AlertDialog.Builder(this)

                        dialog.setMessage("모든 팀 정보가 사라집니다.\n계속하시겠습니까?")
                        dialog.setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                            db.deleteAllTeamData()
                            Toast.makeText(applicationContext, "팀 정보가 초기화 되었습니다.", Toast.LENGTH_SHORT).show()
                        })

                        dialog.setNegativeButton("아니오", DialogInterface.OnClickListener{dialog, id ->})
                        dialog.create().show()

                    }
                }
            }
}