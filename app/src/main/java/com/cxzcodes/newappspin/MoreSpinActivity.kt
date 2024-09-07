package com.cxzcodes.newappspin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cxzcodes.newappspin.Adapter.AdapterManager
import com.cxzcodes.newappspin.Adapter.MSGAdapter

import com.cxzcodes.newappspin.databinding.ActivityMainBinding
import com.cxzcodes.newappspin.databinding.ActivityMoreSpinBinding
import com.cxzcodes.rupeequiz.Utils.Utils
import com.cxzcodes.rupeequiz.Utils.Utils.addDepositAmount
import com.cxzcodes.rupeequiz.Utils.Utils.addWinningAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getDepositAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getWinningAmount
import soup.neumorphism.NeumorphCardView
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MoreSpinActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreSpinBinding
    var coin = 50
    var upii: String = ""
    var am1=""
    var am2=""
    var am3=""
    var am4=""
    var am5=""
    var am6=""
    internal val UPI_PAYMENT = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreSpinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val upi = Utils.getData(this, "upi")
        upii = upi.toString()
         clicklistners()
        updatadata()
        updatecoin()
    }

    private fun clicklistners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnPay.setOnClickListener {
            payUsingUpi("$coin", upii, R.string.app_name.toString(), "Thank-You!!")
        }
        binding.tvPrivacyPolicy?.setOnClickListener {
             startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
            true
        }
        binding.btn1.setOnClickListener {
            coin = am1.toInt()
            binding.tvPay.setText("5 Spin")
            binding.btn1.setBackgroundColor(getColor(R.color.grey))
            binding.btn2.setBackgroundColor(getColor(R.color.app_color))
            binding.btn3.setBackgroundColor(getColor(R.color.app_color))
            binding.btn4.setBackgroundColor(getColor(R.color.app_color))
            binding.btn5.setBackgroundColor(getColor(R.color.app_color))
            binding.btn6.setBackgroundColor(getColor(R.color.app_color))


            binding.txt11.setTextColor(getColor(R.color.app_color))
            binding.txt111.setTextColor(getColor(R.color.app_color))

            binding.txt22.setTextColor(getColor(R.color.white))
            binding.txt222.setTextColor(getColor(R.color.white))

            binding.txt33.setTextColor(getColor(R.color.white))
            binding.txt333.setTextColor(getColor(R.color.white))
            binding.txt44.setTextColor(getColor(R.color.white))
            binding.txt444.setTextColor(getColor(R.color.white))
            binding.txt55.setTextColor(getColor(R.color.white))
            binding.txt555.setTextColor(getColor(R.color.white))
            binding.txt66.setTextColor(getColor(R.color.white))
            binding.txt666.setTextColor(getColor(R.color.white))


        }
        binding.btn2.setOnClickListener {
            coin = am2.toInt()
            binding.tvPay.setText("10 Spin")

            binding.btn2.setBackgroundColor(getColor(R.color.grey))
            binding.btn1.setBackgroundColor(getColor(R.color.app_color))
            binding.btn3.setBackgroundColor(getColor(R.color.app_color))
            binding.btn4.setBackgroundColor(getColor(R.color.app_color))
            binding.btn5.setBackgroundColor(getColor(R.color.app_color))
            binding.btn6.setBackgroundColor(getColor(R.color.app_color))


            binding.txt22.setTextColor(getColor(R.color.app_color))
            binding.txt222.setTextColor(getColor(R.color.app_color))

            binding.txt11.setTextColor(getColor(R.color.white))
            binding.txt111.setTextColor(getColor(R.color.white))

            binding.txt33.setTextColor(getColor(R.color.white))
            binding.txt333.setTextColor(getColor(R.color.white))
            binding.txt44.setTextColor(getColor(R.color.white))
            binding.txt444.setTextColor(getColor(R.color.white))
            binding.txt55.setTextColor(getColor(R.color.white))
            binding.txt555.setTextColor(getColor(R.color.white))
            binding.txt66.setTextColor(getColor(R.color.white))
            binding.txt666.setTextColor(getColor(R.color.white))

        }
        binding.btn3.setOnClickListener {
            coin = am3.toInt()
            binding.tvPay.setText("15 Spin")

            binding.btn3.setBackgroundColor(getColor(R.color.grey))
            binding.btn2.setBackgroundColor(getColor(R.color.app_color))
            binding.btn1.setBackgroundColor(getColor(R.color.app_color))
            binding.btn4.setBackgroundColor(getColor(R.color.app_color))
            binding.btn5.setBackgroundColor(getColor(R.color.app_color))
            binding.btn6.setBackgroundColor(getColor(R.color.app_color))

            binding.txt33.setTextColor(getColor(R.color.app_color))
            binding.txt333.setTextColor(getColor(R.color.app_color))

            binding.txt22.setTextColor(getColor(R.color.white))
            binding.txt222.setTextColor(getColor(R.color.white))

            binding.txt11.setTextColor(getColor(R.color.white))
            binding.txt111.setTextColor(getColor(R.color.white))
            binding.txt44.setTextColor(getColor(R.color.white))
            binding.txt444.setTextColor(getColor(R.color.white))
            binding.txt55.setTextColor(getColor(R.color.white))
            binding.txt555.setTextColor(getColor(R.color.white))
            binding.txt66.setTextColor(getColor(R.color.white))
            binding.txt666.setTextColor(getColor(R.color.white))

        }
        binding.btn4.setOnClickListener {
            coin = am4.toInt()
            binding.tvPay.setText("20 Spin")

            binding.btn4.setBackgroundColor(getColor(R.color.grey))
            binding.btn2.setBackgroundColor(getColor(R.color.app_color))
            binding.btn3.setBackgroundColor(getColor(R.color.app_color))
            binding.btn1.setBackgroundColor(getColor(R.color.app_color))
            binding.btn5.setBackgroundColor(getColor(R.color.app_color))
            binding.btn6.setBackgroundColor(getColor(R.color.app_color))

            binding.txt44.setTextColor(getColor(R.color.app_color))
            binding.txt444.setTextColor(getColor(R.color.app_color))

            binding.txt22.setTextColor(getColor(R.color.white))
            binding.txt222.setTextColor(getColor(R.color.white))

            binding.txt33.setTextColor(getColor(R.color.white))
            binding.txt333.setTextColor(getColor(R.color.white))
            binding.txt11.setTextColor(getColor(R.color.white))
            binding.txt111.setTextColor(getColor(R.color.white))
            binding.txt55.setTextColor(getColor(R.color.white))
            binding.txt555.setTextColor(getColor(R.color.white))
            binding.txt66.setTextColor(getColor(R.color.white))
            binding.txt666.setTextColor(getColor(R.color.white))
        }
        binding.btn5.setOnClickListener {
            coin = am5.toInt()
            binding.tvPay.setText("30 Spin")

            binding.btn5.setBackgroundColor(getColor(R.color.grey))
            binding.btn2.setBackgroundColor(getColor(R.color.app_color))
            binding.btn3.setBackgroundColor(getColor(R.color.app_color))
            binding.btn4.setBackgroundColor(getColor(R.color.app_color))
            binding.btn1.setBackgroundColor(getColor(R.color.app_color))
            binding.btn6.setBackgroundColor(getColor(R.color.app_color))

            binding.txt55.setTextColor(getColor(R.color.app_color))
            binding.txt555.setTextColor(getColor(R.color.app_color))

            binding.txt22.setTextColor(getColor(R.color.white))
            binding.txt222.setTextColor(getColor(R.color.white))

            binding.txt33.setTextColor(getColor(R.color.white))
            binding.txt333.setTextColor(getColor(R.color.white))
            binding.txt44.setTextColor(getColor(R.color.white))
            binding.txt444.setTextColor(getColor(R.color.white))
            binding.txt11.setTextColor(getColor(R.color.white))
            binding.txt111.setTextColor(getColor(R.color.white))
            binding.txt66.setTextColor(getColor(R.color.white))
            binding.txt666.setTextColor(getColor(R.color.white))
        }
        binding.btn6.setOnClickListener {
            coin = am6.toInt()
            binding.tvPay.setText("40 Spin")

            binding.btn6.setBackgroundColor(getColor(R.color.grey))
            binding.btn2.setBackgroundColor(getColor(R.color.app_color))
            binding.btn3.setBackgroundColor(getColor(R.color.app_color))
            binding.btn4.setBackgroundColor(getColor(R.color.app_color))
            binding.btn5.setBackgroundColor(getColor(R.color.app_color))
            binding.btn1.setBackgroundColor(getColor(R.color.app_color))

            binding.txt66.setTextColor(getColor(R.color.app_color))
            binding.txt666.setTextColor(getColor(R.color.app_color))

            binding.txt22.setTextColor(getColor(R.color.white))
            binding.txt222.setTextColor(getColor(R.color.white))

            binding.txt33.setTextColor(getColor(R.color.white))
            binding.txt333.setTextColor(getColor(R.color.white))
            binding.txt44.setTextColor(getColor(R.color.white))
            binding.txt444.setTextColor(getColor(R.color.white))
            binding.txt55.setTextColor(getColor(R.color.white))
            binding.txt555.setTextColor(getColor(R.color.white))
            binding.txt11.setTextColor(getColor(R.color.white))
            binding.txt111.setTextColor(getColor(R.color.white))
        }

    }

    fun payUsingUpi(amount: String, upiId: String, name: String, note: String) {
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()

        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        if (null != chooser.resolveActivity(this.packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(
                this,
                "No UPI app found, please install one to continue",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPI_PAYMENT) {
            if (resultCode == Activity.RESULT_OK || resultCode == 11) {
                data?.let { upiPaymentDataOperation(it) }
            } else {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun upiPaymentDataOperation(data: Intent) {
        val trxt = data.getStringExtra("response")
        Log.d("UPI", "onActivityResult: $trxt")
        var status = ""
        var approvalRefNo = ""

        trxt?.let {
            val response = it.split("&")
            for (str in response) {
                val equalStr = str.split("=")
                if (equalStr.size >= 2) {
                    when (equalStr[0].toLowerCase()) {
                        "status" -> status = equalStr[1].toLowerCase()
                        "approvalrefno", "txnref" -> approvalRefNo = equalStr[1]
                    }
                }
            }

            when {
                status == "success" -> {
                    val preciouscmoney = Utils.getData(this,"spin")
                    val totalcoin = coin + preciouscmoney!!.toInt()
                    Utils.saveData(this,"spin",totalcoin.toString())
                    this.addDepositAmount(totalcoin)
                    Utils.saveData(this, "spin", totalcoin.toString())
                    Toast.makeText(this, "Coin Added successful.", Toast.LENGTH_SHORT)
                        .show()
                    updatecoin()
                    updatadata()
                    finish()
                    Log.d("UPI", "responseStr: $approvalRefNo")
                }

                status == "failure" -> {
                    // Handle failed transaction
                    Toast.makeText(
                        this,
                        "Transaction failed. Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // Handle other status
                    Toast.makeText(
                        this,
                        "Transaction status unknown. Please check with your bank.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } ?: run {
            // Handle null response
            Toast.makeText(
                this,
                "No response received. Please try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updatecoin() {
        val text1 = Random.nextInt(50, 59)
        binding.txt1.setText( text1.toString() +" ₹")
        am1=text1.toString()
        val text2 = Random.nextInt(100, 109)
        binding.txt2.setText( text2.toString() +" ₹")
        coin=text2
        am2=text2.toString()
        val text3 = Random.nextInt(150, 159)
        binding.txt3.setText( text3.toString() +" ₹")
        am3=text3.toString()
         val text4 = Random.nextInt(200, 209)
        am4=text4.toString()
        binding.txt4.setText( text4.toString() +" ₹")
        val text5 = Random.nextInt(300, 309)
        binding.txt5.setText( text5.toString() +" ₹")
        am5=text5.toString()

        val text6 = Random.nextInt(400, 409)
        binding.txt6.setText( text6.toString() +" ₹")
        am6=text6.toString()

    }
    fun updatadata() {
        val spin = Utils.getData(this,"spin")
        val winning=this.getWinningAmount()

        binding.tvEarning.text = " $winning"
        binding.tvDeposit.text = " $spin"


    }

}