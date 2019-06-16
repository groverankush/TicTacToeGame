package com.ankushgrover.tictactoe.ui.end


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment

/**
 * Fragment displaying the final results
 *
 */
class EndGameFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_game, container, false)
    }


}
