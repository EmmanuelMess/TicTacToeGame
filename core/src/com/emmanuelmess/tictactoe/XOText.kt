package com.emmanuelmess.tictactoe

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport


class XOText(generator: FreeTypeFontGenerator): DrawableEntity {
    private val fontBig: BitmapFont
    private val fontSmall: BitmapFont
    private val skinBig: Skin
    private val skinSmall: Skin
    private val restartTexture: Texture
    private val nameLabel: Label
    private val restartButton: Button
    private val stage: Stage


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

        nameLabel = Label("- | -", skinBig).apply {
            setAlignment(Align.topLeft)
        }

        restartTexture = Texture(Gdx.files.internal("images/restart_game.png"))

        val restartDrawable = TextureRegionDrawable(restartTexture)

        restartButton = Button(restartDrawable, restartDrawable).apply {
            setSize(16f, 16f)

            addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    GameData.resetPoints()
                }
            })
        }

        stage = Stage(ScreenViewport())

        stage.addActor(Table().apply {
            add(VerticalGroup().apply {
                addActor(nameLabel)
                addActor(restartButton)
            }).padBottom(50f).expand().bottom()
            setFillParent(true)
        })

        stage.addActor(Table().apply {
            add(Label("X", this@XOText.skinSmall)).padLeft(50f).expand().bottom().left()
            add(Label("O", this@XOText.skinSmall)).padRight(50f).expand().bottom().right()
            setFillParent(true)
        })

        Gdx.input.inputProcessor = stage
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
        restartTexture.dispose()
        stage.dispose()
    }
}