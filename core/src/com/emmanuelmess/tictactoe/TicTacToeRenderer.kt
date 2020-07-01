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
        const val MARGIN_O = 4f
        const val MARGIN_X = 5f
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
    var touchPoint = Vector3()

    private fun hasWinner(): Pair<Vector2, Vector2>? {
        val eq: (Piece?, Piece?, Piece?) -> Boolean = { x, y, z ->
            x == y && y == z && x != null
        }

        val pieceEq: (Int, Int, Int) -> Boolean = { i, j, k ->
            eq(GameData.occupied[i], GameData.occupied[j], GameData.occupied[k])
        }

        val genPair: (Int, Int) -> Pair<Vector2, Vector2> = { i, j ->
            touchRects[i].getCenter(Vector2()) to touchRects[j].getCenter(Vector2())
        }

        if (pieceEq(0, 1, 2)) return genPair(0, 2)
        if (pieceEq(3, 4, 5)) return genPair(3, 5)
        if (pieceEq(6, 7, 8)) return genPair(6, 8)

        if (pieceEq(0, 3, 6)) return genPair(0, 6)
        if (pieceEq(1, 4, 7)) return genPair(1, 7)
        if (pieceEq(2, 5, 8)) return genPair(2, 8)

        if (pieceEq(0, 4, 8)) return genPair(0, 8)
        if (pieceEq(2, 4, 6)) return genPair(2, 6)
        return null
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

                if (index != -1) {
                    val rectangle = touchRects[index]

                    if (GameData.drawXNext) {
                        GameData.data.add(XActor(viewport,
                                rectangle.x + XAndO.MARGIN_X,
                                rectangle.y + XAndO.MARGIN_X,
                                rectangle.width - XAndO.MARGIN_X * 2,
                                rectangle.height - XAndO.MARGIN_X * 2
                        ))
                    } else {
                        GameData.data.add(OActor(viewport,
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
            GameData.toRestart = hasWinner()
            GameData.ended = GameData.toRestart != null || isStalemate()
        }

        GameData.toRestart?.let { (start, end) ->
            shapeRenderer.apply {
                color = Color.RED

                begin(ShapeRenderer.ShapeType.Filled)
                rectLine(start, end, 5f)
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