package com.example.checkers


interface PlayPiece {
    fun pieceAt(square: Square): CheckerPiece? //null check
    fun movePiece(from: Square, to: Square)

}