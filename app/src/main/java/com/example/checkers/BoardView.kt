package com.example.checkers

import android.content.Context
import android.graphics.*
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.checkers.CheckersPlay.canKingEat
import com.example.checkers.CheckersPlay.didKingAte
import com.example.checkers.CheckersPlay.kingCords
import com.example.checkers.CheckersPlay.moveKing
import kotlin.math.min


class BoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var originX = 20f
    private var originY = 200f
    private var radius = 0f
    private var initialPlacement = 65f
    private var shift = (initialPlacement * 2)
    private val scaleFactor = 1.0f

    private var fromCol: Int = -1
    private var fromRow: Int = -1
    private var movingPieceX = -1f
    private var movingPieceY = -1f


    private val paint = Paint()

    private val darkSquares = HashSet<DarkGreenSquares>()


    private val imageId = setOf(
        R.drawable.black,
        R.drawable.white,
        R.drawable.white_king,
        R.drawable.black_king,


        )


    var playPiece: PlayPiece? = null // placing a piece
    private var movingPiece: CheckerPiece? = null //moving a piece
    private var movingPieceBitmap: Bitmap? = null


    private val bitmaps = mutableMapOf<Int, Bitmap>()

    init {
        populateMap()

    }

    private fun populateMap() {
        imageId.forEach { imageId ->
            bitmaps[imageId] = BitmapFactory.decodeResource(resources, imageId)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)
    }


    override fun onDraw(canvas: Canvas?) {

        canvas ?: return
        var boardSize = min(width, height) * scaleFactor
        shift = boardSize / 8f
        originX = (width - boardSize) / 2f
        originY = (height - boardSize) / 2f
        radius = min(width - (shift * 5), height - (shift * 5)).toFloat() / 8f

        super.onDraw(canvas)
        drawCheckerboard(canvas)
        // drawStartPieces(canvas)
        drawWhilePlaying(canvas)
        CheckersPlay.checkToKing()


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                fromCol = ((event.x - originX) / shift).toInt()
                fromRow = 7 - ((event.y - originY) / shift).toInt()
                if (isBlackSquare(fromCol, fromRow)) {
                    playPiece?.pieceAt(Square(fromCol, fromRow))?.let {
                        movingPiece = it
                        movingPieceBitmap = bitmaps[it.imageId]
                    }
                }

            }
            MotionEvent.ACTION_MOVE -> {
                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                val col = ((event.x - originX) / shift).toInt()
                val row = 7 - ((event.y - originY) / shift).toInt()
                if (fromCol != col || fromRow != row) {
                    if (isBlackSquare(col, row)) {
                        playPiece?.movePiece(Square(fromCol, fromRow), Square(col, row))

                    }
                }
                movingPiece = null
                movingPieceBitmap = null
                invalidate()
            }
        }
        return true
    }


    private fun drawCheckerboard(canvas: Canvas) {
        for (row in 0..7) {
            for (col in 0..7) {
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1)
            }
        }
    }

    private fun drawWhilePlaying(canvas: Canvas) {

        for (row in 0 until 8)
            for (col in 0 until 8)
                playPiece?.pieceAt(Square(col, row))?.let { piece ->
                    if (piece != movingPiece) {

                        drawPieceAt(canvas, col, row, piece.imageId)
                    }
                }

        movingPieceBitmap?.let {
            canvas.drawBitmap(
                it,
                null,
                RectF(
                    movingPieceX - shift / 2,
                    movingPieceY - shift / 2,
                    movingPieceX + shift / 2,
                    movingPieceY + shift / 2
                ),
                paint
            )
        }
    }


    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, imageId: Int) {
        canvas.drawBitmap(
            bitmaps[imageId]!!,
            null,
            RectF(
                originX + col * shift,
                originY + (7 - row) * shift,
                originX + (col + 1) * shift,
                originY + ((7 - row) + 1) * shift
            ),
            paint
        )
    }


    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, isDark: Boolean) {
        paint.color = if (isDark) {
            Color.rgb(78, 139, 112)

        } else {
            Color.rgb(102, 155, 132)
        }
        canvas.drawRect(
            originX + col * shift,
            originY + row * shift,
            originX + (col + 1) * shift,
            originY + (row + 1) * shift,
            paint
        )
        if (!isDark) {
            var darkSquare = DarkGreenSquares(col, row)
            darkSquares.add(darkSquare)
        }
    }

    // check if the square the user wants to move to is a valid dark square:
    private fun isBlackSquare(col: Int, row: Int): Boolean {

        for (square in darkSquares) {
            if (square.col == col && square.row == row) {
                return true
            }
        }
        return false
    }
}
