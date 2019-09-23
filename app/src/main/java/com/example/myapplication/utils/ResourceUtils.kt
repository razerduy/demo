package com.example.myapplication.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build

object ResourceUtils {
    fun getDrawable(context: Context, id: Int): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(id)
        } else {
            context.resources.getDrawable(id)
        }
    }
}