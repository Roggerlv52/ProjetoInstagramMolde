package com.example.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagram.R
import com.example.instagram.databinding.DialogCustomBinding


class CustomDialog(contex: Context) : Dialog(contex){
    private lateinit var binding : DialogCustomBinding

    private lateinit var txtButtons : Array<TextView>
    private var titleId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun setTitle(titleId: Int) {
      this.titleId = titleId
    }
    fun addButton(vararg texts : Int,listener : View.OnClickListener){
     txtButtons = Array(texts.size){
         TextView(context)
     }
        texts.forEachIndexed{index, txtid ->
            txtButtons[index].id = txtid
            txtButtons[index].setText(txtid)
            txtButtons[index].setOnClickListener{
                listener.onClick(it)
                dismiss() //esconder dialog
            }
        }
    }
    override fun show(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)//para diminuir o titulo em sdk21
        super.show()

        titleId?.let {
        binding.dialogTitle.setText(it)
        }

        for(txtView in txtButtons){
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(30,50,30,50)

            binding.dialogConteiner.addView(txtView,layoutParams)
        }
    }
}