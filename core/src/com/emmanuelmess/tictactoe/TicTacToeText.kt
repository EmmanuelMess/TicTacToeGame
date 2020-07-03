package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.I18NBundle

class TicTacToeText(
        stage: Stage,
        generator: FreeTypeFontGenerator,
        translationBundle: I18NBundle
): Coreographer {
    private val font: BitmapFont
    private val skin: Skin

    init {
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 120
        font = generator.generateFont(parameter)

        skin = Skin().apply {
            add("default", Label.LabelStyle(font, Color.BLACK))
        }

        val nameLabel = Label(translationBundle["game"], skin).apply {
            setAlignment(Align.topLeft)
        }

        val container = Table().apply {
            add(nameLabel).padTop(100f).expand().top()
            setFillParent(true)
        }

        stage.addActor(container)
    }

    override fun update() {}

    override fun dispose() {
        font.dispose()
        skin.dispose()
    }
}