package com.pocket.rush

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.pocket.rush.databinding.ActivityMainBinding
import com.cxzcodes.rupeequiz.Utils.Utils
import com.cxzcodes.rupeequiz.Utils.Utils.addDepositAmount
import com.cxzcodes.rupeequiz.Utils.Utils.addWinningAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getDepositAmount
import com.cxzcodes.rupeequiz.Utils.Utils.getWinningAmount
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.pocket.rush.Adapter.AdapterManager
import com.pocket.rush.Adapter.MSGAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.onesignal.OneSignal
import com.pocket.rush.RedeemActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MSGAdapter
    private val numbers = mutableListOf<String>()
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    var pos = ""
    private var player: MediaPlayer? = null
    private var coin = 0
    var db = FirebaseFirestore.getInstance()
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var ONESIGNAL_APP_ID = "1a466729-6716-4135-b154-4a77262b4316"
    var coinDep = 0
    var c1 = 0
    var c2 = 0
    var c3 = 0
    var c4 = 0
    var c5 = 0
    var c6 = 0
    var id: String = ""
    internal val UPI_MOD = 0
    var U_DEP = ""
    var play = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        clicklistners()
        fetchdata()
        menuItemClick()
        updatadata()
        notification()
        oneSignal()
        updatecoin()
    }

    private fun clicklistners() {
        binding.ivSpin.setOnClickListener {
            playspin()
        }
        binding.ivWheel.setOnClickListener {
            playspin()
        }
        binding.btnWithdraw.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RedeemActivity::class.java
                )
            )
        }
        binding.btnmoreSpin.setOnClickListener {
            if (play == false) {
                binding.rv.visibility = View.GONE
                binding.frameLayout.visibility = View.VISIBLE
            }

        }

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val params: ViewGroup.LayoutParams = binding.navView.layoutParams
        params.width = (screenWidth / 1.5).toInt()
        binding.navView.layoutParams = params

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.nav_open, R.string.nav_close
        )
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)
        actionBarDrawerToggle!!.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        binding.menuBtn.setOnClickListener {
            if (binding.drawerLayout != null) {
                binding.drawerLayout!!.openDrawer(GravityCompat.START, true)
            }
        }

        binding.firstDeposit.setOnClickListener {
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c1
            U_DEP = c1.toString()
            binding.firstDeposit.setTextColor(getColor(R.color.white))
            binding.secondDeposit.setTextColor(getColor(R.color.app_color))
            binding.thirdDeposit.setTextColor(getColor(R.color.app_color))
            binding.fourthDeposit.setTextColor(getColor(R.color.app_color))
            binding.fifthDeposit.setTextColor(getColor(R.color.app_color))
            binding.sixthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c1.toString())


        }
        binding.secondDeposit.setOnClickListener {
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c2
            U_DEP = c2.toString()
            binding.secondDeposit.setTextColor(getColor(R.color.white))
            binding.firstDeposit.setTextColor(getColor(R.color.app_color))
            binding.thirdDeposit.setTextColor(getColor(R.color.app_color))
            binding.fourthDeposit.setTextColor(getColor(R.color.app_color))
            binding.fifthDeposit.setTextColor(getColor(R.color.app_color))
            binding.sixthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c2.toString())


        }
        binding.thirdDeposit.setOnClickListener {
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c3 + 10
            U_DEP = c3.toString()
            binding.thirdDeposit.setTextColor(getColor(R.color.white))
            binding.secondDeposit.setTextColor(getColor(R.color.app_color))
            binding.firstDeposit.setTextColor(getColor(R.color.app_color))
            binding.fourthDeposit.setTextColor(getColor(R.color.app_color))
            binding.fifthDeposit.setTextColor(getColor(R.color.app_color))
            binding.sixthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c3.toString())


        }
        binding.fourthDeposit.setOnClickListener {
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c4 + 30
            U_DEP = c4.toString()
            binding.fourthDeposit.setTextColor(getColor(R.color.white))
            binding.secondDeposit.setTextColor(getColor(R.color.app_color))
            binding.thirdDeposit.setTextColor(getColor(R.color.app_color))
            binding.firstDeposit.setTextColor(getColor(R.color.app_color))
            binding.fifthDeposit.setTextColor(getColor(R.color.app_color))
            binding.sixthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c4.toString())


        }
        binding.fifthDeposit.setOnClickListener {
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c5 + 60
            U_DEP = c5.toString()
            binding.fifthDeposit.setTextColor(getColor(R.color.white))
            binding.secondDeposit.setTextColor(getColor(R.color.app_color))
            binding.thirdDeposit.setTextColor(getColor(R.color.app_color))
            binding.firstDeposit.setTextColor(getColor(R.color.app_color))
            binding.fourthDeposit.setTextColor(getColor(R.color.app_color))
            binding.sixthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c5.toString())


        }
        binding.sixthDeposit.setOnClickListener {
            binding.sixthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_bg))
            binding.firstDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.secondDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.thirdDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fourthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            binding.fifthDeposit.setBackgroundDrawable(getDrawable(R.drawable.ic_box_border))
            coinDep = c6 + 100
            U_DEP = c6.toString()
            binding.sixthDeposit.setTextColor(getColor(R.color.white))
            binding.secondDeposit.setTextColor(getColor(R.color.app_color))
            binding.thirdDeposit.setTextColor(getColor(R.color.app_color))
            binding.firstDeposit.setTextColor(getColor(R.color.app_color))
            binding.fifthDeposit.setTextColor(getColor(R.color.app_color))
            binding.fourthDeposit.setTextColor(getColor(R.color.app_color))
            binding.depositEditText.setText(c6.toString())


        }
        binding.payBtn.setOnClickListener {
            payUsingUpi(U_DEP.toString(), id, R.string.app_name.toString(), "ThankYOU")
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
            startActivityForResult(chooser, UPI_MOD)
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

        if (requestCode == UPI_MOD) {
            if (resultCode == Activity.RESULT_OK || resultCode == 11) {
                data?.let { upiPaymentDataOperation(it) }
            } else {

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
                    val preciouscmoney = Utils.getData(this, "spin")
                    val totalcoin = coinDep + preciouscmoney!!.toInt()
                    Utils.saveData(this, "spin", totalcoin.toString())
                    this.addDepositAmount(totalcoin)

                    updatecoin()
                    updatadata()
                    binding.rv.visibility=View.VISIBLE

                    binding.frameLayout.visibility=View.GONE
                    Log.d("UPI", "responseStr: $approvalRefNo")
                }

                status == "failure" -> {
                    // Handle failed transaction

                }

                else -> {

                }
            }
        } ?: run {

        }
    }

    fun updatecoin() {
        val text1 = Random.nextInt(55, 60)
        binding.firstDeposit.setText(text1.toString())
        c1 = text1
        binding.firstDeposit.setText("₹ " + text1.toString())

        binding.textOne.setText("Minimum deposit limit is ₹ " + text1)
        val text2 = Random.nextInt(95, 100)
        U_DEP = text2.toString()
        coinDep = text2

        binding.secondDeposit.setText("₹ " + text2.toString())
        c2 = text2
        binding.depositEditText.setText(text2.toString())

        val text3 = Random.nextInt(150, 159)
        binding.thirdDeposit.setText("₹ " + text3.toString())
        c3 = text3
        val text4 = Random.nextInt(200, 209)
        c4 = text4
        binding.fourthDeposit.setText("₹ " + text4.toString())
        val text5 = Random.nextInt(300, 309)
        c5 = text5
        binding.fifthDeposit.setText("₹ " + text5.toString())


        val text6 = Random.nextInt(400, 409)
        c6 = text6
        binding.sixthDeposit.setText("₹ " + text6.toString())


    }

    private fun menuItemClick() {
        val menu = binding.navView?.menu
        val home = menu?.findItem(R.id.nav_home)

        val privacy = menu?.findItem(R.id.nav_policy)
        val term = menu?.findItem(R.id.nav_terms)
        val logout = menu?.findItem(R.id.nav_exit)

        val headerView = binding.navView.getHeaderView(0)
        val username = headerView.findViewById<TextView>(R.id.tvusername)
         val name = Utils.getData(this, "name")
         username.setText(name.toString())

        home?.setOnMenuItemClickListener {
            binding.drawerLayout?.closeDrawers()
            true
        }

        logout?.setOnMenuItemClickListener {
            finishAffinity()
           finish()


            true
        }



        privacy?.setOnMenuItemClickListener {
            binding.drawerLayout?.closeDrawers()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://sites.google.com/view/pocketrush/home")
                )
            )
            true
        }
        term?.setOnMenuItemClickListener {
            binding.drawerLayout?.closeDrawers()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://sites.google.com/view/pocketrush/home")
                )
            )
            true
        }


    }

    private fun playspin() {
        val mode = Utils.getData(this, "mode")
        if (mode == "no") {
            val random = Random.nextInt(1, 14)
            spinNow(random, pos)
            playMusic()

        } else {
            val spin = Utils.getData(this, "spin")!!.toInt()

            if (spin >= 10) {
                playMusic()

                val newAmount = getDepositAmount() - 10
                addDepositAmount(newAmount)
                if (getWinningAmount() == 0) {
                    spinNow(4, pos)
                } else if (this.getWinningAmount() == 45) {
                    spinNow(5, pos)
                } else if (this.getWinningAmount() == 105) {
                    spinNow(10, pos)
                } else if (this.getWinningAmount() == 175) {
                    spinNow(5, pos)
                } else if (this.getWinningAmount() == 235) {

                    spinNow(0, pos)
                } else if (this.getWinningAmount() == 250) {
                    //1st payment start

                    spinNow(11, pos)
                } else if (this.getWinningAmount() == 260) {
                    spinNow(9, pos)
                } else if (this.getWinningAmount() == 290) {
                    spinNow(11, pos)
                } else if (this.getWinningAmount() == 300) {
                    spinNow(8, pos)
                } else if (this.getWinningAmount() == 305) {
                    spinNow(0, pos)

                    //2nd payment start
                } else if (this.getWinningAmount() == 320) {

                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 310) {
                    spinNow(3, pos)
                } else if (this.getWinningAmount() == 335) {
                    spinNow(8, pos)
                } else if (this.getWinningAmount() == 340) {
                    spinNow(9, pos)
                } else if (this.getWinningAmount() == 370) {
                    spinNow(11, pos)
                    //3rd payment start
                } else if (this.getWinningAmount() == 380) {
                    spinNow(1, pos)
                } else if (this.getWinningAmount() == 400) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 390) {
                    spinNow(8, pos)
                } else if (this.getWinningAmount() == 395) {
                    spinNow(4, pos)
                } else if (this.getWinningAmount() == 440) {
                    spinNow(2, pos)

                    //3rd dpayment start
                } else if (this.getWinningAmount() == 430) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 420) {
                    spinNow(3, pos)
                } else if (this.getWinningAmount() == 445) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 435) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 425) {
                    spinNow(7, pos)

                    //4th payment
                } else if (this.getWinningAmount() == 460) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 450) {
                    spinNow(1, pos)
                } else if (this.getWinningAmount() == 470) {
                    spinNow(3, pos)
                } else if (this.getWinningAmount() == 495) {
                    spinNow(2, pos)
                } else if (this.getWinningAmount() == 485) {
                    spinNow(2, pos)
                } else {
                    val random = Random.nextInt(0, 2)

                    spinNow(random, pos)
                }


            } else {
                showDialog()

            }
        }
    }

    private fun showDialog() {
        val insufficientDialog = AlertDialog.Builder(this)
        insufficientDialog.apply {
            setTitle("Spin & Win Coin")
            setMessage("You don't have sufficient Coin to spin")
            setPositiveButton("Add Money", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    binding.frameLayout.visibility = View.VISIBLE

                }
            })
        }
        val dialog = insufficientDialog.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.let { button ->
            button.textSize = 18F
            // Change text color
            button.setTextColor(ContextCompat.getColor(this, R.color.app_color))
        }

    }

    override fun onResume() {
        super.onResume()
        updatadata()
    }


    private fun spinNow(randomInt: Int, isUpiAvailable: String) {
        play = true
        val spinOption = 15
        val SpinOptionDeg = 360 / spinOption
        val rotate = RotateAnimation(
            0f,
            (360 - SpinOptionDeg * randomInt).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = (SpinOptionDeg * (spinOption - randomInt) + 2000).toLong()
        rotate.interpolator = DecelerateInterpolator()
        rotate.fillAfter = true
        binding.ivWheel?.setDrawingCacheEnabled(true)
        val spinAnim =
            AnimationUtils.loadAnimation(this, R.anim.spin_wheel) as RotateAnimation
        spinAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.ivWheel?.startAnimation(rotate)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Log.e("value for spin : ", randomInt.toString() + "")
                if (player != null) {
                    player?.release()
                }
                when (randomInt) {
                    0 -> coin = resources.getString(R.string.case_1).toInt()
                    1 -> coin = resources.getString(R.string.case_2).toInt()
                    2 -> coin = resources.getString(R.string.case_3).toInt()
                    3 -> coin = resources.getString(R.string.case_4).toInt()
                    4 -> coin = resources.getString(R.string.case_5).toInt()
                    5 -> coin = resources.getString(R.string.case_6).toInt()
                    6 -> coin = resources.getString(R.string.case_7).toInt()
                    7 -> coin = resources.getString(R.string.case_8).toInt()
                    8 -> coin = resources.getString(R.string.case_9).toInt()
                    9 -> coin = resources.getString(R.string.case_10).toInt()
                    10 -> coin = resources.getString(R.string.case_11).toInt()
                    11 -> coin = resources.getString(R.string.case_12).toInt()
                    12 -> coin = resources.getString(R.string.case_13).toInt()
                    13 -> coin = resources.getString(R.string.case_14).toInt()
                    14 -> coin = resources.getString(R.string.case_15).toInt()
                }
                val previousdpin = Utils.getData(this@MainActivity, "spin")!!.toInt()
                val availablespin = previousdpin - 10
                Utils.saveData(this@MainActivity, "spin", availablespin.toString())
                winDialog(coin.toString(), isUpiAvailable)


            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("@@@", "onAnimationRepeat: ")
            }
        })
        binding.ivWheel?.startAnimation(spinAnim)

    }

    private fun winDialog(amount: String, isUpiAvailable: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.win_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView>(R.id.tv_win).text = "Congrats!!You have Won $amount  "
        dialog.create()
        dialog.show()

        val mediaPlayer = MediaPlayer.create(this, R.raw.win_sound)
        mediaPlayer.start()


        val spinMore = dialog.findViewById<TextView>(R.id.btn_spin_more)
        spinMore.setOnClickListener {
            mediaPlayer.release()
            dialog.dismiss()
            play = false

        }


        val winningAmount = this.getWinningAmount()
        val newWinningAmount = winningAmount + amount.toInt()
        addWinningAmount(newWinningAmount)
        updatadata()


    }


    private fun playMusic() {
        player = MediaPlayer.create(this, R.raw.wheel_sound)
        player?.setOnCompletionListener(MediaPlayer.OnCompletionListener { })
        player?.start()

    }

    fun updatadata() {
        val mode = Utils.getData(this, "mode")
        if (mode == "no") {
            val spin = Utils.getData(this, "spin").toString()
            val winning = getWinningAmount().toString()
            binding.ivcoin.visibility = View.VISIBLE
            binding.tvEarning.text = " $winning"
            binding.tvDeposit.text = " $spin"
        } else {
            val spin = Utils.getData(this, "spin").toString()
            val winning = getWinningAmount().toString()

            binding.tvEarning.text = "₹ $winning"
            binding.tvDeposit.text = "₹ $spin"
        }


    }

    fun closeapp() {
        if (binding.frameLayout.visibility == View.VISIBLE) {
            binding.frameLayout.visibility = View.GONE

            binding.rv.visibility = View.VISIBLE
        } else {
            val dialog = Dialog(this, R.style.CustomDialogTheme)
            dialog.setContentView(R.layout.exit_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val btncancel: LinearLayout = dialog.findViewById(R.id.btnCancel)
            val btnExit: LinearLayout = dialog.findViewById(R.id.btnExit)
            btncancel.setOnClickListener { dialog.dismiss() }
            btnExit.setOnClickListener { finishAffinity() }
            dialog.create()
            dialog.show()
        }


    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        closeapp()
    }

    fun fetchdata() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val ref = db.collection("getupi").document("upi")
        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val upi = document.getString("Upi")
                    id = upi.toString()
                    Utils.saveData(this, "upi", upi.toString())

                }
            }
            .addOnFailureListener {
            }
    }

    private fun notification() {
        val mode = Utils.getData(this, "mode")
        if (mode == "yes") {
            numbers.addAll(
                listOf(
                    "Ravi Sharma  Won 1050 🎉",
                    "Priya Patel  Won 1250 🏆",
                    "Amit Kumar  Won 2100 🎉",
                    "Anjali Singh  Won 1850 🏅",
                    "Rajesh Gupta  Won 1950 🏆",
                    "Sneha Verma  Won 1450 🎉",
                    "Suresh Yadav  Won 850 🎉",
                    "Pooja Chauhan  Won 1320 🏅",
                    "Vikram Rao  Won 920 🎉",
                    "Karan Desai  Won 1045 🏅",
                    "Manish Mehta  Won 1550 🏆",
                    "Neha Reddy  Won 1620 🎉",
                    "Rakesh Nair  Won 920 🏅",
                    "Deepa Thakur  Won 1430 🎉",
                    "Sanjay Pandey  Won 2150 🏆",
                    "Arjun Sharma  Won 1145 🎉",
                    "Ravi Patel  Won 990 🎉",
                    "Divya Joshi  Won 875 🏅",
                    "Sumit Desai  Won 1745 🏆",
                    "Nisha Singh  Won 1330 🎉",
                    "Ashok Jain  Won 1234 🎉",
                    "Meena Shah  Won 1575 🏆",
                    "Rahul Iyer  Won 1890 🏅",
                    "Aarti Bansal  Won 1987 🎉",
                    "Mohit Kapoor  Won 820 🎉",
                    "Rohan Agarwal  Won 990 🏅",
                    "Vivek Arora  Won 1100 🎉",
                    "Seema Malik  Won 1340 🏆",
                    "Anil Gupta  Won 1560 🎉",
                    "Kavita Rao  Won 930 🏅",
                    "Rohit Khanna  Won 1725 🎉",
                    "Ritu Sharma  Won 1025 🏆",
                    "Suraj Shetty  Won 920 🎉",
                    "Sneha Gupta  Won 1650 🏅",
                    "Anjali Reddy  Won 1280 🎉",
                    "Harish Nair  Won 950 🏆",
                    "Amit Sinha  Won 1410 🎉",
                    "Pooja Desai  Won 1087 🏅",
                    "Prakash Verma  Won 980 🎉",
                    "Divya Rao  Won 1120 🏆",
                    "Rajeev Patel  Won 870 🎉",
                    "Sunita Singh  Won 990 🏅",
                    "Gaurav Joshi  Won 1125 🎉",
                    "Neeraj Mehta  Won 1320 🏆",
                    "Aditya Chauhan  Won 1145 🎉",
                    "Kiran Pandey  Won 980 🏅",
                    "Rakesh Reddy  Won 1105 🎉",
                    "Vikas Gupta  Won 950 🏆"


                )

            )
        } else {
            numbers.addAll(
                listOf(
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",
                    "Rozana Cash Welcome to app name play and enjoy, ",


                    )
            )

        }


        adapter = MSGAdapter(numbers)

        val handler = Handler(Looper.getMainLooper())
        val scrollRunnable = object : Runnable {
            override fun run() {
                binding.rvMsg.smoothScrollToPosition(adapter.itemCount - 1)
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(scrollRunnable, 2000)

        binding.rvMsg.layoutManager =
            AdapterManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMsg.adapter = adapter

        binding.rvMsg.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            private var lastTouchEventTime = 0L

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                lastTouchEventTime = System.currentTimeMillis()
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                lastTouchEventTime = System.currentTimeMillis()
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

            init {
                binding.rvMsg.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    private var lastScrollTime = System.currentTimeMillis()

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (System.currentTimeMillis() - lastTouchEventTime > 500) {
                                handler.postDelayed(scrollRunnable, 2000)
                            }
                        }
                        lastScrollTime = System.currentTimeMillis()
                    }
                })
            }
        })

    }

    private fun oneSignal() {
        val mode = Utils.getData(this, "mode")
        if (mode == "no") {
            binding.btnmoreSpin.visibility = View.GONE
            binding.llDep.visibility = View.GONE
            binding.view.visibility = View.GONE
        } else {
            binding.tvWallet.setText("Withdraw")
        }

        if (ONESIGNAL_APP_ID != null) {
            OneSignal.initWithContext(this, ONESIGNAL_APP_ID)


            CoroutineScope(Dispatchers.IO).launch {
                OneSignal.Notifications.requestPermission(true)
            }
        }

    }

}