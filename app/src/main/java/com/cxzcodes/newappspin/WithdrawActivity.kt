package com.cxzcodes.newappspin

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cxzcodes.newappspin.databinding.ActivityWithdrawBinding
import com.cxzcodes.rupeequiz.Utils.Utils
import com.cxzcodes.rupeequiz.Utils.Utils.addWinningAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getWinningAmount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WithdrawActivity : AppCompatActivity() {
    lateinit var binding:ActivityWithdrawBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updatedata()
        binding.ivPaytm.setOnClickListener {
            binding.etNumber.setHint("Enter your number")
            binding.etNumber.inputType=InputType.TYPE_CLASS_NUMBER
        }
        binding.ivUpi.setOnClickListener {
            binding.etNumber.setHint("Enter your UPI")
            binding.etNumber.inputType=InputType.TYPE_CLASS_TEXT
        }



        binding.btnWithdraw.setOnClickListener {
            val winamount = this.getWinningAmount().toInt()
            val enteredAmount = binding.etCoin?.text.toString().toIntOrNull()

             if (binding.etNumber?.text.toString().length < 10) {
                binding.etNumber.error = "Invalid Phone"
                return@setOnClickListener
            }

             if (enteredAmount == null || enteredAmount <= 0) {
                binding.etCoin.error = "Enter a valid amount"
                return@setOnClickListener
            }

             if (enteredAmount > winamount || winamount <= 500) {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.win_dialog)
                dialog.findViewById<ImageView>(R.id.iv_win).setImageDrawable(getDrawable(R.drawable.oops))
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.findViewById<TextView>(R.id.tv_win).text = "You have insufficient coins"
                dialog.findViewById<TextView>(R.id.tv_msg).text = ""
                val btn: TextView = dialog.findViewById(R.id.btn_spin_more)
                btn.text = "OK"
                btn.setOnClickListener { dialog.dismiss() }
                dialog.create()
                dialog.show()
            } else {
                 val progressbar = ProgressDialog(this)
                progressbar.setCancelable(false)
                progressbar.setMessage("Please Wait..")
                progressbar.show()

                 Handler().postDelayed({
                    updatedata()
                    progressbar.dismiss()
                    Toast.makeText(this, "Withdraw Successful!!", Toast.LENGTH_SHORT).show()

                     val remainingAmount = winamount - enteredAmount
                    addWinningAmount(remainingAmount)

                }, 2000)
            }
        }
        binding.ivBack.setOnClickListener { finish() }


    }
    fun updatedata(){
val balance= getWinningAmount().toString()
             binding.tvBalance.text =  " $balance"


    }


}