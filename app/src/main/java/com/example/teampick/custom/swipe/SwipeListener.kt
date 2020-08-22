package com.example.teampick.custom.swipe

import com.baoyz.swipemenulistview.SwipeMenuListView

class SwipeListener(var listView : SwipeMenuListView) : SwipeMenuListView.OnSwipeListener  {

    override fun onSwipeEnd(position: Int) {
        listView.smoothOpenMenu(position);
    }

    override fun onSwipeStart(position: Int) {
        listView.smoothOpenMenu(position);
    }
}