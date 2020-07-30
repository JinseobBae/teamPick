package com.example.teampick.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.teampick.R

class TeamListViewAdapter(val context: Context, val teamList: ArrayList<String>): BaseAdapter() {



    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.team_list_item, null)
        val team = view.findViewById<TextView>(R.id.teamName)
        team.text = teamList[position]
        return view

    }

    override fun getItem(p0: Int): Any = teamList[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = teamList.size

}