package com.ankushgrover.tictactoe.ui.start


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.ankushgrover.tictactoe.R
import com.ankushgrover.tictactoe.base.BaseFragment
import com.ankushgrover.tictactoe.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_start.*


/**
 * The start screen where user enter their details.
 *
 */
class StartFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mListener: StartFragmentListener


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is StartFragmentListener) {
            mListener = context
        } else
            throw RuntimeException("The passed context doesn't implements StartFragmentListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        btnStart.setOnClickListener {
            mListener.startGame()
        }
    }

    interface StartFragmentListener {
        fun startGame()
    }

}
