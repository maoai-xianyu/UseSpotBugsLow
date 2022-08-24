package com.coding.events.bugs

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class CustomTextView2 : TextView {
    var text: String? = null
    var a = 1.0f
    var b = 2.0f
    var c = 3.0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun tst() {
        if (a > b) return
        if (a > c) return
        if (a > 10) return
        if (this is Any) return
    }

    fun test() {
        if (a > b) return
        if (a > c) return
        if (a > 10) return
    }

    fun test2() {
        if (a > b) return
        if (a > c) return
        if (a > 10) return
    }
}