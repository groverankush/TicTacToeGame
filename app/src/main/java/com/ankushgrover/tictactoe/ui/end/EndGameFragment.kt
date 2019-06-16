package com.ankushgrover.tictactoe.ui.end


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment
import com.ankushgrover.tictactoe.ui.game.State
import com.ankushgrover.tictactoe.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_end_game.*

/**
 * Fragment displaying the final results
 *
 */
class EndGameFragment : BaseFragment() {

    private lateinit var mListener: EndGameListener
    private lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is EndGameListener)
            mListener = context
        else
            throw RuntimeException("The passed context doesn't implements EndGameListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        btnStart.setOnClickListener {
            mainViewModel.endGame()
            mListener.playAgain()
        }
    }

    private fun initObservers() {


        message.setText(
            when (mainViewModel.winnerData.value!!) {
                State.O -> R.string.o_won
                State.X -> R.string.x_won
                else -> R.string.draw
            }
        )

    }

    interface EndGameListener {
        fun playAgain()
    }

}
