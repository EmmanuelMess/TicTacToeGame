package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport

class XOText(generator: FreeTypeFontGenerator): Disposable {
    private val font: BitmapFont
    private val skin: Skin
    private val stage: Stage

    init {
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 180
        font = generator.generateFont(parameter)

        skin = Skin().apply {
            add("default", Label.LabelStyle(font, Color.BLACK))
        }

        stage = Stage(ScreenViewport())

        val nameLabel = Label("X    O", skin).apply {
            setAlignment(Align.topLeft)
        }

        val container = Table().apply {
            add(nameLabel).padBottom(50f).expand().bottom()
            setFillParent(true)
        }

        stage.addActor(container)
    }

    fun update(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun draw() {
        stage.draw()
    }

    override fun dispose() {
        font.dispose()
        skin.dispose()
        stage.dispose()
    }
}