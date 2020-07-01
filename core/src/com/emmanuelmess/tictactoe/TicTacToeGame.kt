package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FillViewport

const val DEBUG = true

class TicTacToeGame : ApplicationAdapter() {
    object Size {
        const val WIDTH = 100f
        const val HEIGHT = 16 * WIDTH / 9
    }

    object Grid {
        const val STROKE_WIDTH = 2f
        const val TOP = 45f
        const val HEIGHT = 90f
        const val LEFT = 15f
        const val WIDTH = 70f
    }

    private lateinit var ticTacToeText: TicTacToeText
    private lateinit var ticTacToeRenderer: TicTacToeRenderer
    private lateinit var xoText: XOText

    private lateinit var viewport: FillViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer

    override fun create() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"))
        ticTacToeText = TicTacToeText(generator)
        xoText = XOText(generator)
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
    }

    override fun resize(width: Int, height: Int) {
        ticTacToeText.update(width, height)
        viewport.update(width, height, true)
        xoText.update(width, height)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.projectionMatrix = viewport.camera.combined

        ticTacToeText.draw()
        ticTacToeRenderer.draw(shapeRenderer)
        xoText.draw()

        if(DEBUG) {
            ticTacToeText.drawDebug()
            ticTacToeRenderer.drawDebug(shapeRenderer)
            xoText.drawDebug()
        }
    }

    override fun dispose() {
        ticTacToeText.dispose()
        ticTacToeRenderer.dispose()
        shapeRenderer.dispose()
        xoText.dispose()
    }
}