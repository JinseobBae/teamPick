package com.example.teampick.custom.dialog

import com.example.teampick.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.teampick.database.DBService
import com.example.teampick.utils.PickUtils
import com.example.teampick.vo.Team


class TeamAddDialog(context: Context) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    private val utils = PickUtils()
    private val db = DBService(context)


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow

        var onClick = initBtn()
        setContentView(R.layout.team_add_dialog)

        var teamLevelSpinner = findViewById<Spinner>(R.id.teamLevelCombo)
        var adapter = ArrayAdapter.createFromResource(context, R.array.team_level_set, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        teamLevelSpinner.adapter = adapter

        findViewById<Button>(R.id.teamSave).setOnClickListener(onClick)
        findViewById<Button>(R.id.teamCan).setOnClickListener(onClick)

    }

    fun initBtn() : View.OnClickListener =
            View.OnClickListener {
                when(it.id){
                    R.id.teamSave -> {
                        var teamName = findViewById<EditText>(R.id.newTeamName)?.text
                        var teamLevel = findViewById<Spinner>(R.id.teamLevelCombo)?.selectedItem
                        db.insertTeamDate(Team(teamId = null, teamName = teamName.toString(), level = teamLevel.toString(), rate = utils.getTeamLevelRate(teamLevel.toString())))
                        Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_LONG).show()
                        this.dismiss()
                    }
                    R.id.teamCan -> {
                        this.dismiss()
                    }
                }
            }



}