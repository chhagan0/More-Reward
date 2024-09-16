package com.pocket.rush

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cxzcodes.rupeequiz.Utils.Utils
import com.cxzcodes.rupeequiz.Utils.Utils.addWinningAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getWinningAmount
import com.pocket.rush.databinding.ActivityRedeemBinding

class RedeemActivity : AppCompatActivity() {
    lateinit var binding:ActivityRedeemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRedeemBinding.inflate(layoutInflater)
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
                 Toast.makeText(this, "Your earned amount is the less than the minimum withdraw limit", Toast.LENGTH_LONG).show()

             } else {
                 val progressbar = ProgressDialog(this)
                progressbar.setCancelable(false)
                progressbar.setMessage("Please Wait..")
                progressbar.show()

                 Handler().postDelayed({
                    updatedata()
                    progressbar.dismiss()
                    Toast.makeText(this, "Withdraw Successful!! Payment will be credited 24 hours", Toast.LENGTH_SHORT).show()

                     val remainingAmount = winamount - enteredAmount
                    addWinningAmount(remainingAmount)

                }, 2000)
            }
        }
        binding.ivBack.setOnClickListener { finish() }


    }
    fun updatedata(){
val mode=Utils.getData(this,"mode")
if (mode=="yes"){
    binding.cv.visibility=View.VISIBLE
    binding.tvCoin.visibility=View.GONE
    val balance= getWinningAmount().toString()
    binding.tvBalance.text =  "₹ $balance"

}else{
    val balance= getWinningAmount().toString()
    binding.tvBalance.text =  " $balance"
}



    }


}