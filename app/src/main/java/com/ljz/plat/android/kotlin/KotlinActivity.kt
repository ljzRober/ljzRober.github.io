package com.ljz.plat.android.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ljz.plat.android.R

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val firstKotlin =  FirstKotlin(this)
        firstKotlin.deprecatedFun()
    }
}