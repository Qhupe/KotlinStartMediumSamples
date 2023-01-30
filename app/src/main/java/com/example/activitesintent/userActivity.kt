package com.example.activitesintent

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.activitesintent.databinding.ActivityUserBinding

class userActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    //sayaca heryerden erişmek için tanımlamayı burda yaptık
    private lateinit var timer: CountDownTimer
    private lateinit var nextActivity: Intent
    var number = 0
    var runnable : Runnable = Runnable{}
    var handler : Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        getActivityData(view)
        verifiybutton(view)
        ScreenClickControl(view)
        open_kronometre(view)
        stop_kronometre(view)
        countTimer()
    }

    fun getActivityData(view: View){
        //inten değeri tanımlayarak gönderdiğimiz verileri çekmek isteyeceğiz
        val intent = intent
        //burada ise userName ve userEmail değerlerimize intent.getStringExtra ile gönderdiğimiz değerleri atadık.
        val userName = intent.getStringExtra("username")
        val userEmail = intent.getStringExtra("email")
        //Burada ise ViewBinding ile aldığımız değerleri yönlendirdiğimiz elementlere yazdırdık.
        binding.dataUserName.text=userName
        binding.dataEmail.text=userEmail

    }

    fun verifiybutton(view: View) {
        // Burada ara konu olarak uyarı mesajı yani Alert Messages konusuna değineceğim
        //Toast mesajları login ekranında koşulları kontrol ederken denemiş olduk
        //burada ise alert messages deneyeceğiz
        binding.verifyButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Verify")
            alert.setMessage("I accept that my information is correct")
            alert.setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(applicationContext, "Verify", Toast.LENGTH_SHORT).show()
            }
            alert.setNegativeButton("No") { dialog, which ->
                Toast.makeText(applicationContext, "Unverify", Toast.LENGTH_SHORT).show()
            }
            alert.show()
        }
    }

    fun countTimer(){
        nextActivity = Intent(this,MainActivity::class.java)

        //Burada milisInFuture olarak isimlendirilen ilk parametre bize kaç saniyeden başlanacağı bilgisinin milisaniye cinsinden referansıdır
        //ikinci parametre ise countDownInterval olarak isimlendirilen parametre ise kaç saniyede bir işlem yapılacağı bilgisidir.
        //CountDownTimer Bir Abstract sınıftır yani bunu kafamıza göre kullanamayız inherit etmemiz lazım ve bu sınıftan bir nesne türetmemiz gerekecek
        timer = object : CountDownTimer(9999*12,1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                binding.countText.text="Count : ${p0/1000}"
            }

            override fun onFinish() {
                //Burada applicationContext yerine this kullanamıyoruz çünkü bir üst sınıfı gösteremiyor, Contextler konusunda bahsetmiştik
                Toast.makeText(applicationContext,"Uzun Süre İşlem Yapılmadığı için Çıkış Yapılıyor...",Toast.LENGTH_SHORT).show()
                startActivity(nextActivity)
                finish()
            }
        }.start()
    }

    fun ScreenClickControl(view: View){
        binding.userLayout.setOnClickListener {
            Toast.makeText(this,"AKsiyon Alındı Sayaç Sıfırlandı",Toast.LENGTH_SHORT).show()
            timer.cancel()
            timer.start()
        }
    }

    fun open_kronometre(view: View){
        number = 0
        binding.btnKrnmtr.setOnClickListener {
        timer.cancel()
            vsibleStart()
            runnable = object : Runnable{
                override fun run() {
                    number = number+1
                    binding.krnmtrText.text="Time : "+number.toString()
                    handler.postDelayed(runnable,1000)
                }

            }
            handler.post(runnable)
        }
    }

    fun stop_kronometre(view: View){
        binding.btnKrnmtrStop.setOnClickListener {
            handler.removeCallbacks(runnable)
            number=0
            binding.krnmtrText.text="Time: 0"
            timer.start()
            visibleStop()
        }
    }

    fun vsibleStart(){
        binding.imageView2.visibility=View.GONE
        binding.btnKrnmtr.visibility=View.GONE
        binding.verifyButton.visibility=View.GONE
        binding.dataEmail.visibility=View.GONE
        binding.dataUserName.visibility=View.GONE
        binding.countText.visibility=View.GONE
        binding.btnKrnmtrStop.visibility=View.VISIBLE
        binding.krnmtrText.visibility=View.VISIBLE

    }

    fun visibleStop(){
        binding.krnmtrText.visibility=View.GONE
        binding.btnKrnmtrStop.visibility=View.GONE
        binding.verifyButton.visibility=View.VISIBLE
        binding.imageView2.visibility=View.VISIBLE
        binding.btnKrnmtr.visibility=View.VISIBLE
        binding.dataUserName.visibility=View.VISIBLE
        binding.dataUserName.visibility=View.VISIBLE
        binding.countText.visibility=View.VISIBLE
    }
}