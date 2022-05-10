package com.example.instagram.common.util

import android.text.Editable
import android.text.TextWatcher

class TxtWatcher(val onTextChanged : (String)-> Unit): TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged(s.toString())
    }
    override fun afterTextChanged(p0: Editable?) {

    }
}