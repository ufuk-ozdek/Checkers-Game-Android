package com.example.checkers

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.checkers.CheckersPlay.reset
import java.io.PrintWriter


const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity(), PlayPiece {
    private var printWriter: PrintWriter? = null
    private lateinit var boardView: BoardView
    private lateinit var scoreWhite: TextView
    private lateinit var scoreBlack: TextView
    private lateinit var turnImage: ImageView
    private var blackScoreCount = 0
    private var whiteScoreCount = 0

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        boardView = findViewById(R.id.board_view)
        scoreWhite = findViewById(R.id.score_text_white)
        scoreBlack = findViewById(R.id.score_text_black)
        turnImage = findViewById(R.id.turn_view)
        scoreBlack.text = getString(R.string.black_points, blackScoreCount)
        scoreWhite.text = getString(R.string.white_points, whiteScoreCount)

        boardView.playPiece = this


        // create app bar and back button
        var actionBar = getSupportActionBar()
        // showing the back button in action bar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon)
            reset()
        }
    }



    override fun pieceAt(square: Square): CheckerPiece? = CheckersPlay.pieceAt(square)

    @SuppressLint("SetTextI18n", "StringFormatMatches")
    override fun movePiece(from: Square, to: Square) {
        CheckersPlay.movePiece(from, to)

        printWriter?.let {
            val moveStr = "${from.col},${from.row},${to.col},${to.row}"
            Log.d(TAG,moveStr)

        }
        // update score if game is finished and reset
        if(CheckersPlay.checkForWinner() == -1){
            Log.d(TAG, "Black has won a game")
            blackScoreCount++
            scoreBlack.text = getString(R.string.black_points, blackScoreCount)
            reset()
        }
        if(CheckersPlay.checkForWinner() == 1){
            Log.d(TAG, "White has won a game")
            whiteScoreCount++
            scoreWhite.text = getString(R.string.white_points, whiteScoreCount)
            reset()

        }
        // update the color according to turn
        if(CheckersPlay.whiteTurn){
            turnImage.setImageResource(R.drawable.turn_red_bg)
        }else{
            turnImage.setImageResource(R.drawable.turn_blue_bg)
        }
        boardView.invalidate()
    }

}