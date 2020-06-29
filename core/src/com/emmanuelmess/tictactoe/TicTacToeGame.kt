package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport


class TicTacToeGame : ApplicationAdapter() {
    private lateinit var font: BitmapFont
    private lateinit var skin: Skin
    private lateinit var stage: Stage

    override fun create() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 120
        font = generator.generateFont(parameter)

        generator.dispose()

        skin = Skin().apply {
            add("default", Label.LabelStyle(font, Color.BLACK))
        }

        stage = Stage(ScreenViewport())

        val nameLabel = Label("TicTacToe", skin).apply {
            setAlignment(Align.topLeft)
        }

        val container = Table().apply {
            add(nameLabel).padTop(100f).expand().top()
        }
        container.debug()
        container.setFillParent(true)

        stage.addActor(container)

        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()

        
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        font.dispose()
        skin.dispose()
        stage.dispose()
    }
}