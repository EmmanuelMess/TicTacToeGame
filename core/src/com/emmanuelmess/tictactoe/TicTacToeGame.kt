package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport

const val WIDTH = 100f
const val HEIGHT = 16 * WIDTH / 9

class TicTacToeGame : ApplicationAdapter() {
    private lateinit var font: BitmapFont
    private lateinit var skin: Skin
    private lateinit var stage: Stage
    private lateinit var viewport: FillViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer

    private fun createFont() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 120
        font = generator.generateFont(parameter)
        generator.dispose()
    }

    private fun createText() {
        createFont()

        skin = Skin().apply {
            add("default", Label.LabelStyle(font, Color.BLACK))
        }

        stage = Stage(ScreenViewport())

        val nameLabel = Label("Tic Tac Toe", skin).apply {
            setAlignment(Align.topLeft)
        }

        val container = Table().apply {
            add(nameLabel).padTop(100f).expand().top()
            setFillParent(true)
        }

        stage.addActor(container)
    }

    override fun create() {
        createText()

        camera = OrthographicCamera().apply {
            setToOrtho(true, WIDTH, HEIGHT)
        }
        viewport = FillViewport(WIDTH, HEIGHT, camera)
        shapeRenderer = ShapeRenderer()

        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        viewport.update(width, height, true)
    }

    object Grid {
        const val STROKE_WIDTH = 5f
        const val TOP = 45f
        const val HEIGHT = 75f
        const val LEFT = 15f
        const val WIDTH = 70f
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()

        shapeRenderer.apply {
            projectionMatrix = viewport.camera.combined
            color = Color.BLACK

            begin(ShapeRenderer.ShapeType.Filled)

            rect(Grid.LEFT + (Grid.WIDTH / 3 - Grid.STROKE_WIDTH/4) - Grid.STROKE_WIDTH/2, Grid.TOP, Grid.STROKE_WIDTH, Grid.HEIGHT)
            rect(Grid.LEFT + (Grid.WIDTH * 2 / 3 + Grid.STROKE_WIDTH/4) - Grid.STROKE_WIDTH/2, Grid.TOP, Grid.STROKE_WIDTH, Grid.HEIGHT)
            rect(Grid.LEFT, Grid.TOP + (Grid.HEIGHT / 3 - Grid.STROKE_WIDTH/4) - Grid.STROKE_WIDTH/2, Grid.WIDTH, Grid.STROKE_WIDTH)
            rect(Grid.LEFT, Grid.TOP + (Grid.HEIGHT * 2 / 3 + Grid.STROKE_WIDTH/4) - Grid.STROKE_WIDTH/2, Grid.WIDTH, Grid.STROKE_WIDTH)

            end()
        }
    }

    override fun dispose() {
        font.dispose()
        skin.dispose()
        stage.dispose()
        shapeRenderer.dispose()
    }
}