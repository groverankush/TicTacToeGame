package com.ankushgrover.tictactoe.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment
import com.ankushgrover.tictactoe.ui.end.EndGameFragment
import com.ankushgrover.tictactoe.ui.game.GameFragment
import com.ankushgrover.tictactoe.ui.game.State
import com.ankushgrover.tictactoe.ui.start.StartFragment

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, GameFragment.GameListener,
    EndGameFragment.EndGameListener {


    private val KEY_CURRENT_FRAGMENT = "keyCurrentFragment"

    private lateinit var mainViewModel: MainViewModel
    private lateinit var currentFragment: BaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (savedInstanceState != null) {
            currentFragment =
                supportFragmentManager.getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT) as BaseFragment
            replaceFragment(currentFragment, false)
        } else
            replaceFragment(StartFragment(), false)

        init()


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.let {
            supportFragmentManager.putFragment(outState, KEY_CURRENT_FRAGMENT, currentFragment)
        }
    }

    private fun init() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel.winnerData.observe(this, Observer {
            if (it != State.EMPTY)
                replaceFragment(EndGameFragment(), true)
        })
    }

    override fun startGame() {
        replaceFragment(GameFragment(), true)
    }

    override fun playAgain() {
        replaceFragment(StartFragment(), true)
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
