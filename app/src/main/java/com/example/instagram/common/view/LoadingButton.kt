package com.example.instagram.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.instagram.R

class LoadingButton : FrameLayout {
    private lateinit var button : Button
    private lateinit var progress : ProgressBar
    private var text : String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStylerAttr: Int) : super(
        context, attrs, defStylerAttr) {
     setup(context,attrs)
    }

    // Atributos que vão chegar do xml                                                     " as " para converter
    private fun setup(context: Context, attrs : AttributeSet?){
     val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_loading, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton,0,0)
         text = typedArray.getString(R.styleable.LoadingButton_text)

         button = getChildAt(0) as Button
         progress = getChildAt(1) as ProgressBar
         button.text = text
         button.isEnabled = false

        typedArray.recycle()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button.isEnabled = enabled
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }
    public fun showProgress(enabled: Boolean){
        if (enabled){
            button.text =""
            button.isEnabled = false
            progress.visibility = View.VISIBLE
        }else{
            button.text = text
            button.isEnabled = true
            progress.visibility = View.GONE
        }
    }
}