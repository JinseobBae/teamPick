package com.example.teampick.custom.swipe

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.baoyz.swipemenulistview.SwipeMenu
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.example.teampick.R

class SwipeCreator(var context : Context ) : SwipeMenuCreator {


    override fun create(menu: SwipeMenu) {
        // create "Close" item
        val deleteItem = SwipeMenuItem(context);
        // set item background
        deleteItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25));
        // set item width
        deleteItem.width = 150;
        // set a icon
        deleteItem.setIcon(R.drawable.ic_delete);
        // add to menu
        menu.addMenuItem(deleteItem);
    }
}