package com.ankushgrover.tictactoe.ui.game


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment
import com.ankushgrover.tictactoe.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * The main TicTacToe game fragment
 *
 */
class GameFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mListener: GameListener
    private lateinit var mainViewModel: MainViewModel
    private lateinit var gameViewModel: GameViewModel
    private var coordinateIdMap = HashMap<Int, Pair<Int, Int>>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is GameListener)
            mListener = context
        else
            throw RuntimeException("The passed context doesn't implements GameListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViewModels()
        makeCoordinateIdMap()
        initListeners(view)
        if (savedInstanceState != null)
            initViews(view)

    }

    private fun initViewModels() {
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    private fun initViews(view: View) {
        gameViewModel.recentMoves.forEach {
            setButtonText(it)
        }
    }

    private fun setButtonText(point: Point) {
        val btn = view?.findViewById<Button>(point.id)
        btn?.text = if (point.state == State.X) "X" else if (point.state == State.O) "O" else ""
        activity?.let {
            btn?.setTextColor(
                ContextCompat.getColor(
                    it,
                    if (point.state == State.O) R.color.oColor else R.color.xColor
                )
            )
        }
    }

    private fun initListeners(view: View) {
        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)

        fabUndo.setOnClickListener { gameViewModel.undo() }

        gameViewModel.errorData.observe(this, Observer { Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() })
        gameViewModel.undoMove.observe(this, Observer { setButtonText(it) })
        gameViewModel.winnerData.observe(this, Observer {
            mainViewModel.updateScore(it) })
        gameViewModel.moveData.observe(this, Observer {
            setButtonText(it) })

    }

    private fun makeCoordinateIdMap() {
        coordinateIdMap.put(R.id.btn0, Pair(0, 0))
        coordinateIdMap.put(R.id.btn1, Pair(0, 1))
        coordinateIdMap.put(R.id.btn2, Pair(0, 2))
        coordinateIdMap.put(R.id.btn3, Pair(1, 0))
        coordinateIdMap.put(R.id.btn4, Pair(1, 1))
        coordinateIdMap.put(R.id.btn5, Pair(1, 2))
        coordinateIdMap.put(R.id.btn6, Pair(2, 0))
        coordinateIdMap.put(R.id.btn7, Pair(2, 1))
        coordinateIdMap.put(R.id.btn8, Pair(2, 2))
    }


    override fun onClick(v: View?) {
        val button = v as Button

        if (button.text.isNullOrEmpty()) {
            val pair = coordinateIdMap.get(button.id)
            gameViewModel.turn(pair!!.first, pair.second, button.id)
        }
    }

    interface GameListener {
    }
}
