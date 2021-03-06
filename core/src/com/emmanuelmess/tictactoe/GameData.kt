package com.emmanuelmess.tictactoe

import com.badlogic.gdx.math.Vector2

object GameData {
    var pointsX = 0
    var pointsO = 0

    val occupied = arrayOfNulls<TicTacToeRenderer.Piece>(9)
    val data = mutableListOf<DrawableShape>()
    var drawXNext: Boolean = true
    var toRestart: Pair<Vector2, Vector2>? = null
    var ended: Boolean = false

    fun restart() {
        occupied.fill(null)
        data.clear()
        drawXNext = true
        toRestart = null
        ended = false
    }

    fun resetPoints() {
        restart()
        pointsX = 0
        pointsO = 0
    }
}