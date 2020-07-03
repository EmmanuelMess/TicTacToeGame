package com.emmanuelmess.tictactoe

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport

class TicTacToeRenderer(
        val strokeWidth: Float,
        viewport: Viewport,
        x: Float, y: Float,
        width: Float, height: Float
): DrawableShape(viewport, x, y, width, height) {
    object XAndO {
        const val MARGIN_O = 2f * TicTacToeGame.Size.C
        const val MARGIN_X = 5f * TicTacToeGame.Size.C
    }

    enum class Piece {
        X, O
    }

    val touchRects = listOf(
            Rectangle(x, y, width / 3, height / 3),
            Rectangle(x + width / 3, y, width / 3, height / 3),
            Rectangle(x + width * 2 / 3, y, width / 3, height / 3),
            Rectangle(x, y + height / 3, width / 3, height / 3),
            Rectangle(x + width / 3, y + height / 3, width / 3, height / 3),
            Rectangle(x + width * 2 / 3, y + height / 3, width / 3, height / 3),
            Rectangle(x, y + height * 2 / 3, width / 3, height / 3),
            Rectangle(x + width / 3, y + height * 2 / 3, width / 3, height / 3),
            Rectangle(x + width * 2 / 3, y + height * 2 / 3, width / 3, height / 3)
    )
    val linePoints = listOf(
            touchRects[0].getCenter(Vector2()),
            touchRects[1].getCenter(Vector2()),
            touchRects[2].getCenter(Vector2()),
            touchRects[3].getCenter(Vector2()),
            touchRects[4].getCenter(Vector2()),
            touchRects[5].getCenter(Vector2()),
            touchRects[6].getCenter(Vector2()),
            touchRects[7].getCenter(Vector2()),
            touchRects[8].getCenter(Vector2())
    )
    var touchPoint = Vector3()

    private fun setWinner() {
        val eq: (Piece?, Piece?, Piece?) -> Boolean = { x, y, z ->
            x == y && y == z && x != null
        }

        val pieceEq: (Int, Int, Int) -> Boolean = { i, j, k ->
            eq(GameData.occupied[i], GameData.occupied[j], GameData.occupied[k])
        }

        val genPair: (Int, Int) -> Pair<Vector2, Vector2> = { i, j ->
            linePoints[i] to linePoints[j]
        }

        val won: Piece?

        if (pieceEq(0, 1, 2)) {
            GameData.toRestart = genPair(0, 2)
            won = GameData.occupied[0]
        }
        else if (pieceEq(3, 4, 5)) {
            GameData.toRestart = genPair(3, 5)
            won = GameData.occupied[3]
        }
        else if (pieceEq(6, 7, 8)) {
            GameData.toRestart = genPair(6, 8)
            won = GameData.occupied[6]
        }

        else if (pieceEq(0, 3, 6)) {
            GameData.toRestart = genPair(0, 6)
            won = GameData.occupied[0]
        }
        else if (pieceEq(1, 4, 7)) {
            GameData.toRestart = genPair(1, 7)
            won = GameData.occupied[1]
        }
        else if (pieceEq(2, 5, 8)) {
            GameData.toRestart = genPair(2, 8)
            won = GameData.occupied[2]
        }

        else if (pieceEq(0, 4, 8)) {
            GameData.toRestart = genPair(0, 8)
            won = GameData.occupied[0]
        }
        else if (pieceEq(2, 4, 6)) {
            GameData.toRestart = genPair(2, 6)
            won = GameData.occupied[2]
        }

        else {
            GameData.toRestart = null
            won = null
        }

        when(won) {
            Piece.O -> GameData.pointsO++
            Piece.X -> GameData.pointsX++
        }

        if(Math.max(GameData.pointsO, GameData.pointsX) > 100) {
            GameData.resetPoints()
        }
    }

    private fun isStalemate(): Boolean {
        return !GameData.occupied.contains(null)
    }

    override fun draw(shapeRenderer: ShapeRenderer) {
        if(Gdx.input.justTouched()) {
            if(GameData.ended) {
                GameData.restart()
            } else {
                viewport.camera.unproject(touchPoint.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))

                val index = touchRects.indexOfFirst {
                    it.contains(touchPoint.x, touchPoint.y)
                }

                if (index != -1 && GameData.occupied[index] == null) {
                    val rectangle = touchRects[index]

                    if (GameData.drawXNext) {
                        GameData.data.add(XActor(
                                viewport,
                                rectangle.x + XAndO.MARGIN_X,
                                rectangle.y + XAndO.MARGIN_X,
                                rectangle.width - XAndO.MARGIN_X * 2,
                                rectangle.height - XAndO.MARGIN_X * 2
                        ))
                    } else {
                        GameData.data.add(OActor(
                                viewport,
                                rectangle.x + XAndO.MARGIN_O,
                                rectangle.y + XAndO.MARGIN_O,
                                rectangle.width - XAndO.MARGIN_O * 2,
                                rectangle.height - XAndO.MARGIN_O * 2
                        ))
                    }

                    GameData.occupied[index] = if (GameData.drawXNext) Piece.X else Piece.O
                    GameData.drawXNext = !GameData.drawXNext
                }
            }
        }

        shapeRenderer.apply {
            color = Color.BLACK

            begin(ShapeRenderer.ShapeType.Filled)

            rect(x + (width / 3 - strokeWidth / 4) - strokeWidth / 2, y, strokeWidth, height)
            rect(x + (width * 2 / 3 + strokeWidth / 4) - strokeWidth / 2, y, strokeWidth, height)
            rect(x, y + (height / 3 - strokeWidth / 4) - strokeWidth / 2, width, strokeWidth)
            rect(x, y + (height * 2 / 3 + strokeWidth / 4) - strokeWidth / 2, width, strokeWidth)

            end()

            for (shape in GameData.data) {
                shape.draw(shapeRenderer)
            }
        }

        if(!GameData.ended) {
            setWinner()
            GameData.ended = GameData.toRestart != null || isStalemate()
        }

        GameData.toRestart?.let { (start, end) ->
            shapeRenderer.apply {
                color = Color.RED

                begin(ShapeRenderer.ShapeType.Filled)
                rectLine(start, end, 2f * TicTacToeGame.Size.C)
                end()
            }
        }
    }

    override fun drawDebug(shapeRenderer: ShapeRenderer) {
        shapeRenderer.apply {
            color = Color.RED

            begin(ShapeRenderer.ShapeType.Filled)

            /*for (rectangle in touchRects) {
                rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
                color.g += 20
            }*/

            end()
        }
    }
}