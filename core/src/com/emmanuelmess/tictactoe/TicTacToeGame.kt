package com.emmanuelmess.tictactoe

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.I18NBundle
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport


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

    private lateinit var assetManager: AssetManager

    private lateinit var ticTacToeText: TicTacToeText
    private lateinit var ticTacToeRenderer: TicTacToeRenderer
    private lateinit var xoText: XOText

    private lateinit var viewport: FillViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var textViewport: FitViewport
    private lateinit var stage: Stage

    override fun create() {
        assetManager = AssetManager().apply {
            InternalFileHandleResolver().also {
                setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(it))
                setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(it))
            }


            load("i18n/TicTacToe", I18NBundle::class.java)
            load("Roboto-120.ttf", BitmapFont::class.java, FreeTypeFontLoaderParameter().apply {
                fontFileName = "fonts/Roboto-Medium.ttf"
                fontParameters.size = 120
            })
            load("Roboto-100.ttf", BitmapFont::class.java, FreeTypeFontLoaderParameter().apply {
                fontFileName = "fonts/Roboto-Medium.ttf"
                fontParameters.size = 100
            })
            load("Roboto-80.ttf", BitmapFont::class.java, FreeTypeFontLoaderParameter().apply {
                fontFileName = "fonts/Roboto-Medium.ttf"
                fontParameters.size = 80
            })
            load("images/restart_game.png", Texture::class.java)

            finishLoading()
        }

        textViewport = FitViewport(720f, 1280f)
        stage = Stage(textViewport)

        ticTacToeText = TicTacToeText(stage, assetManager)
        xoText = XOText(stage, assetManager)

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
        assetManager.dispose()
        ticTacToeText.dispose()
        ticTacToeRenderer.dispose()
        shapeRenderer.dispose()
        stage.dispose()
        xoText.dispose()
    }
}