package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.I18NBundle
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import java.util.*


const val DEBUG = false

class TicTacToeGame : ApplicationAdapter() {
    object Size {
        const val WIDTH = 1000f
        const val HEIGHT = 16 * WIDTH / 9

        const val C = 10f
    }

    object Grid {
        const val STROKE_WIDTH = 2f * Size.C
        const val TOP = 45f * Size.C
        const val HEIGHT = 90f * Size.C
        const val LEFT = 15f * Size.C
        const val WIDTH = 70f * Size.C
    }

    private lateinit var ticTacToeText: TicTacToeText
    private lateinit var ticTacToeRenderer: TicTacToeRenderer
    private lateinit var xoText: XOText

    private lateinit var viewport: FillViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var textViewport: FitViewport
    private lateinit var stage: Stage

    override fun create() {
        val baseFileHandle = Gdx.files.internal("i18n/TicTacToe")
        val locale = Locale("", "es")
        val translationBundle: I18NBundle = I18NBundle.createBundle(baseFileHandle, locale)

        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        textViewport = FitViewport(720f, 1280f)
        stage = Stage(textViewport)

        ticTacToeText = TicTacToeText(stage, generator, translationBundle)
        xoText = XOText(stage, generator)

        generator.dispose()

        camera = OrthographicCamera().apply {
            setToOrtho(true, Size.WIDTH, Size.HEIGHT)
        }
        viewport = FillViewport(Size.WIDTH, Size.HEIGHT, camera)

        shapeRenderer = ShapeRenderer()

        ticTacToeRenderer = TicTacToeRenderer(
                Grid.STROKE_WIDTH,
                viewport,
                Grid.LEFT,
                Grid.TOP,
                Grid.WIDTH,
                Grid.HEIGHT
        )


        Gdx.graphics.isContinuousRendering = false
        Gdx.graphics.requestRendering()
        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT
                or if(Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)

        shapeRenderer.projectionMatrix = viewport.camera.combined

        ticTacToeText.update()
        xoText.update()

        ticTacToeRenderer.draw(shapeRenderer)
        stage.draw()

        if(DEBUG) {
            ticTacToeRenderer.drawDebug(shapeRenderer)
        }
    }

    override fun dispose() {
        ticTacToeText.dispose()
        ticTacToeRenderer.dispose()
        shapeRenderer.dispose()
        stage.dispose()
        xoText.dispose()
    }
}