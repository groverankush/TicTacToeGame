package com.ankushgrover.tictactoe.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var isXTurn = true
    private val recentMoves = arrayListOf<Point>()
    private var matrix: Array<Array<State>> = arrayOf(
        arrayOf(State.EMPTY, State.EMPTY, State.EMPTY),
        arrayOf(State.EMPTY, State.EMPTY, State.EMPTY),
        arrayOf(State.EMPTY, State.EMPTY, State.EMPTY)
    )

    val winnerData = MutableLiveData<State>()
    val undoMove = MutableLiveData<Point>()
    val errorData = MutableLiveData<String>()

    /**
     * Method to make a turn
     *
     * @param x: X axis of array
     * @param y: Y axis of array
     * @param id: Id is the view id which is clicked. Though it is view id but treated as a normal unique integer
     */
    fun turn(x: Int, y: Int, id: Int) {

        val p = Point(x, y, id, if (isXTurn) State.X else State.O)

        matrix[x][y] = p.state

        recentMoves.add(p)

        isXTurn = !isXTurn

        checkSomeoneWon()

    }

    /**
     * Method to undo last move made by user.
     */
    fun undo() {
        if (recentMoves.isNullOrEmpty())
            errorData.value = "No moves made"
        else {
            val point = recentMoves.last()
            // Remove from matrix
            matrix[point.x][point.y] = State.EMPTY
            // Remove from recentMoves
            recentMoves.remove(point)
            // undo last turn
            isXTurn = !isXTurn
            // post removed value
            undoMove.value = point
        }
    }

    /**
     * Method to check if some one won by checking the rows, columns and diagonals.
     */
    private fun checkSomeoneWon() {
        var result = checkRows()

        if (result != State.EMPTY) {
            winnerData.value = result
            return
        }

        result = checkCols()
        if (result != State.EMPTY) {
            winnerData.value = result
            return
        }

        result = checkDiagonals()
        if (result != State.EMPTY) {
            winnerData.value = result
            return
        }

        if (recentMoves.size == 9)
            winnerData.value = State.EMPTY
    }

    private fun checkRows(): State {
        for (i in 0..2) {
            if (matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2]) {
                return matrix[i][0]
            }
        }
        return State.EMPTY
    }

    private fun checkCols(): State {
        for (i in 0..2) {
            if (matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i]) {
                return matrix[0][i]
            }
        }
        return State.EMPTY
    }

    private fun checkDiagonals(): State {
        if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2]) {
            return matrix[0][0]
        }
        if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0]) {
            return matrix[0][2]
        }
        return State.EMPTY
    }

}