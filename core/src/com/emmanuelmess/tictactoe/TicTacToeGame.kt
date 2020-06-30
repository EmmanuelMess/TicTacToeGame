package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

class TicTacToeGame : ApplicationAdapter() {
    private lateinit var ticTacToeText: TicTacToeText
    private lateinit var ticTacToeRenderer: TicTacToeRenderer
    private lateinit var xoText: XOText

    override fun create() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        ticTacToeText = TicTacToeText(generator)
        xoText = XOText(generator)
        generator.dispose()

        ticTacToeRenderer = TicTacToeRenderer()

        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
    }

    override fun resize(width: Int, height: Int) {
        ticTacToeText.update(width, height)
        ticTacToeRenderer.update(width, height)
        xoText.update(width, height)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        ticTacToeText.draw()
        ticTacToeRenderer.draw()
        xoText.draw()
    }

    override fun dispose() {
        ticTacToeText.dispose()
        ticTacToeRenderer.dispose()
        xoText.dispose()
    }
}