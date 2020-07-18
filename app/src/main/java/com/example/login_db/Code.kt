package com.example.login_db

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Code {
    companion object {
        val CHARS = listOf(
            '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )

        val DEFAULT_CODE_LENGTH = 4
        val DEFAULT_FONT_SIZE = 25
        val DEFAULT_LINE_NUMBER = 5
        val BASE_PADDING_LEFT = 10
        val RANGE_PADDING_LEFT = 15
        val BASE_PADDING_TOP = 15
        val RANGE_PADDING_TOP = 20
        val DEFAULT_WIDTH = 100
        val DEFAULT_HEIGHT = 40
    }

    val width = DEFAULT_WIDTH
    val height = DEFAULT_HEIGHT

    val base_padding_left = BASE_PADDING_LEFT
    val range_padding_left = RANGE_PADDING_LEFT
    val base_padding_top = BASE_PADDING_TOP
    val range_padding_top = RANGE_PADDING_TOP

    val codeLength = DEFAULT_CODE_LENGTH
    val line_number = DEFAULT_LINE_NUMBER
    val font_size = DEFAULT_FONT_SIZE
}