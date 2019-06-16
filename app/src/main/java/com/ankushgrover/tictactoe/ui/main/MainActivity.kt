package com.ankushgrover.tictactoe.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment
import com.ankushgrover.tictactoe.ui.end.EndGameFragment
import com.ankushgrover.tictactoe.ui.game.GameFragment
import com.ankushgrover.tictactoe.ui.game.State
import com.ankushgrover.tictactoe.ui.start.StartFragment

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, GameFragment.GameListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var currentFragment: BaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        replaceFragment(StartFragment(), false)


    }

    private fun init() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel.winnerData.observe(this, Observer {
            if (it == State.O) {
                Toast.makeText(this, "O won", Toast.LENGTH_SHORT).show()
            } else if (it == State.X)
                Toast.makeText(this, "X won", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()



            replaceFragment(EndGameFragment(), true)
        })
    }

    override fun startGame() {
        replaceFragment(GameFragment(), true)
    }

    private fun replaceFragment(fragment: BaseFragment, addTransitions: Boolean) {
        replaceFragment(fragment, addTransitions, false)
    }

    private fun replaceFragment(fragment: BaseFragment, addTransitions: Boolean, addToBackStack: Boolean) {
        currentFragment = fragment
        val ft = supportFragmentManager.beginTransaction()
        if (addTransitions)
            ft.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
        ft.replace(R.id.container, fragment)
        if (addToBackStack)
            ft.addToBackStack(null)
        ft.commit()
    }
}
