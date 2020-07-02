package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport

abstract class DrawableShape(
    protected val viewport: Viewport,
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float
) {
    abstract fun draw(shapeRenderer: ShapeRenderer)

    open fun drawDebug(shapeRenderer: ShapeRenderer) {

    }

    open fun dispose() {
    }
}