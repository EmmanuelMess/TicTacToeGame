package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

class OActor(
        viewport: Viewport,
        x: Float, y: Float,
        width: Float, height: Float
): DrawableShape(viewport, x, y, width, height) {
    companion object {
        const val WIDTH = 4f * TicTacToeGame.Size.C
    }

    override fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.apply {
            begin(ShapeRenderer.ShapeType.Filled)

            color = Color.BLACK
            circle(x + width/2, y + height/2, width/2)

            color = Color.WHITE
            circle(x + width/2, y + height/2, width/2 - WIDTH)

            end()
        }
    }
}