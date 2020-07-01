package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable

interface DrawableEntity: Disposable {
    fun draw()

    fun drawDebug() {

    }

    fun update(width: Int, height: Int)
}