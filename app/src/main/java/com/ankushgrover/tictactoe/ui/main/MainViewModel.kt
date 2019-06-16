package com.ankushgrover.tictactoe.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ankushgrover.tictactoe.ui.game.State


class MainViewModel : ViewModel() {

    val OScoreData = MutableLiveData<Int>()
    val XScoreData = MutableLiveData<Int>()
    val winnerData = MutableLiveData<State>()


    init {
        OScoreData.value = 0
        XScoreData.value = 0
    }

    fun updateScore(state: State) {
        winnerData.value = state

        if (state == State.O)
            OScoreData.value = OScoreData.value?.plus(1)

        if (state == State.X)
            XScoreData.value = XScoreData.value?.plus(1)
    }

}