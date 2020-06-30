package com.emmanuelmess.tictactoe

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FillViewport

class TicTacToeRenderer: Disposable {
    object Size {
        const val WIDTH = 100f
        const val HEIGHT = 16 * WIDTH / 9
    }

    object Grid {
        const val STROKE_WIDTH = 5f
        const val TOP = 45f
        const val HEIGHT = 75f
        const val LEFT = 15f
        const val WIDTH = 70f
    }

    private val viewport: FillViewport
    private val camera = OrthographicCamera().apply {
        setToOrtho(true, Size.WIDTH, Size.HEIGHT)
    }
    init {
        viewport = FillViewport(Size.WIDTH, Size.HEIGHT, camera)
    }

    private val shapeRenderer = ShapeRenderer()

    fun update(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    fun draw() {
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
        shapeRenderer.dispose()
    }
}