package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.viewport.Viewport

class XActor(viewport: Viewport, x: Float, y: Float, width: Float, height: Float): DrawableShape(viewport, x, y, width, height) {
    override fun draw(shapeRenderer: ShapeRenderer) {
        shapeRenderer.apply {
            color = Color.BLACK

            begin(ShapeRenderer.ShapeType.Filled)

            rectLine(x, y, x + width, y + height, 5f)
            rectLine(x, y + height, x + width, y, 5f)

            end()
        }
    }
}