package com.emmanuelmess.tictactoe

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align


class XOText(stage: Stage, assetManager: AssetManager): Coreographer {
    private val skinBig: Skin
    private val skinSmall: Skin
    private val nameLabel: Label
    private val restartButton: Button

    init {
        skinBig = Skin().apply {
            add("default", Label.LabelStyle(assetManager.get<BitmapFont>("Roboto-100.ttf"), Color.BLACK))
        }

        skinSmall = Skin().apply {
            add("default", Label.LabelStyle(assetManager.get<BitmapFont>("Roboto-80.ttf"), Color.BLACK))
        }

        nameLabel = Label("- | -", skinBig).apply {
            setAlignment(Align.topLeft)
        }

        val restartDrawable = TextureRegionDrawable(assetManager.get<Texture>("images/restart_game.png"))

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
        skinBig.dispose()
        skinSmall.dispose()
    }
}