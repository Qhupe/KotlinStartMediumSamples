package com.example.activitesintent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.activitesintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //ViewBinding kullanımı ile devam ediyoruz.
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var saveSharedName : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = this.getSharedPreferences("newToDosave", Context.MODE_PRIVATE)
        saveSharedName = sharedPreferences.getString("newToDo",null)
        if (saveSharedName!=""&&saveSharedName!=null) {
            val nextActivity = Intent(this, userActivity::class.java)
            startActivity(nextActivity)
        }

        println("On Create çağırıldı")

        //Login fonksiyonunu çağırarak giriş işlemini yapıyoruz
        login(view)
    }

    override fun onResume() {
        super.onResume()
        println("On Resume Çağırıldı")
    }

    override fun onPause() {
        super.onPause()
        println("On Pause Çağırıldı")

    }


    override fun onStop() {
        super.onStop()
        println("On Stop Çağırıldı")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("On Destroy Çağırıldı")

    }


    fun login(view: View){
        //Burada new activity adında bir değer tanımladık, bu değer bizi parametre olarak gideceğimiz diğer ekrana yönlendirecek
        val nextActivity = Intent(this,userActivity::class.java)
        //View Binding ile elemntlere erişim sağlıyoruz
        val userEmail = binding.userEmail.text
        val userName = binding.userName.text
        val userPassword = binding.userPassword.text

        //View Binding ile loginButton elementini dinliyoruz
        binding.loginButton.setOnClickListener {
            //Basit seviyede Auth işlemleri için if blokları kullanıyoruz
            //Basit bir doğruluk için @gmail.com referansı ile minimum karakter sayısını kontrol edelim
            if(userName != null && userName.length>2 && userName.length < 20){
                if (userEmail !=null && userEmail.length>11){
                    if (userPassword != null && userPassword.length>3 && userPassword.length<12){

                        //putExtra ile geçilecek olan activitye veri aktarımı sağlıyoruz
                        //Veri aktarımını sağlarken doğru tip dönşümü yapmayı unutmayalım
                        nextActivity.putExtra("username",userName.toString())
                        nextActivity.putExtra("email",userEmail.toString())
                        sharedPreferences.edit().putString("newToDo",userPassword.toString()).apply()
                        //Burada ise tüm bilgiler doğru ise diğer aktivity'i yani geçceğimiz ekrana start veriyoruz.
                        startActivity(nextActivity)
                    }else{
                        //else Kısımları içerisinde koşul sağlanmıyor ise Toast Mesaj ile kullancıya mesaj dönderiyoruz
                        Toast.makeText(this,"Parola Doğru Değil",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"E-Mail Doğru Değil",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"İsim Doğru Değil",Toast.LENGTH_SHORT).show()
            }
        }

    }
}