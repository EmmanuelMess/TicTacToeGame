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


class XOText(stage: Stage, generator: FreeTypeFontGenerator): Coreographer {
    private val fontBig: BitmapFont
    private val fontSmall: BitmapFont
    private val skinBig: Skin
    private val skinSmall: Skin
    private val restartTexture: Texture
    private val nameLabel: Label
    private val restartButton: Button

    init {
        fontBig = generator.generateFont(FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 100
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
    }

    override fun update() {
        nameLabel.setText("${GameData.pointsX} | ${GameData.pointsO}")
    }

    override fun dispose() {
        fontBig.dispose()
        fontSmall.dispose()
        skinBig.dispose()
        skinSmall.dispose()
        restartTexture.dispose()
    }
}