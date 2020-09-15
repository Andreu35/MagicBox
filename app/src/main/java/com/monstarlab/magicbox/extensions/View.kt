package com.monstarlab.magicbox.extensions

import android.view.View

fun View.goneUnless(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.hideUnless(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}