package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport

class XOText(generator: FreeTypeFontGenerator): DrawableEntity {
    private val fontBig: BitmapFont
    private val fontSmall: BitmapFont
    private val skinBig: Skin
    private val skinSmall: Skin
    private val stage: Stage
    private val nameLabel: Label


    init {
        fontBig = generator.generateFont(FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 180
        })

        skinBig = Skin().apply {
            add("default", Label.LabelStyle(fontBig, Color.BLACK))
        }

        fontSmall = generator.generateFont(FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 80
        })

        skinSmall = Skin().apply {
            add("default", Label.LabelStyle(fontSmall, Color.BLACK))
        }
        
        stage = Stage(ScreenViewport())

        nameLabel = Label("- | -", skinBig).apply {
            setAlignment(Align.topLeft)
        }

        stage.addActor(Table().apply {
            add(nameLabel).padBottom(50f).expand().bottom()
            setFillParent(true)
        })

        stage.addActor(Table().apply {
            add(Label("X", this@XOText.skinSmall)).padLeft(50f).expand().bottom().left()
            add(Label("O", this@XOText.skinSmall)).padRight(50f).expand().bottom().right()
            setFillParent(true)
        })
    }

    override fun update(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun draw() {
        nameLabel.setText("${GameData.pointsX} | ${GameData.pointsO}")

        stage.draw()
    }

    override fun dispose() {
        fontBig.dispose()
        fontSmall.dispose()
        skinBig.dispose()
        skinSmall.dispose()
        stage.dispose()
    }
}