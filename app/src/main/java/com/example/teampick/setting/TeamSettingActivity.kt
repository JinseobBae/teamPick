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
import com.example.teampick.custom.dialog.TeamAddDialog
import com.example.teampick.database.DBService


class TeamSettingActivity : AppCompatActivity() {

    val db = DBService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_setting)

        val onClick = initBtn()
        findViewById<Button>(R.id.teamList).setOnClickListener(onClick)
        findViewById<Button>(R.id.clearTeam).setOnClickListener(onClick)
        findViewById<Button>(R.id.addTeam).setOnClickListener(onClick)
        findViewById<Button>(R.id.delTeam).setOnClickListener(onClick)
    }


    fun initBtn() : View.OnClickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.addTeam -> {
                        var addDialog = TeamAddDialog(this)
                        addDialog.show()
                    }

                    R.id.delTeam -> {
                        val toIntent = Intent(applicationContext, TeamRemoveActivity::class.java)
                        toIntent.putExtra("isDel", true)
                        startActivity(toIntent)
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