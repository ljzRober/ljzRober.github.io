package com.ljz.plat.android.kotlin

import android.content.Context
import android.widget.Toast

class FirstKotlin(val context: Context) {

    @Deprecated("please replace a new function",
        replaceWith = ReplaceWith("newFun(string)",
                "androidx.fragment.app.FragmentActivity"),
        level = DeprecationLevel.WARNING)
    fun deprecatedFun() {
        Toast.makeText(context, "this is a Deprecated function", Toast.LENGTH_SHORT).show()
    }

    fun newFun(string: String) {
        Toast.makeText(context, "this is a new function$string", Toast.LENGTH_SHORT).show()
    }
}