package ran.am.receipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val responseText = findViewById<View>(R.id.textView) as TextView
       val title = findViewById<View>(R.id.editTextTextPersonName2) as EditText
       val author = findViewById<View>(R.id.editTextTextPersonName3) as EditText
       val inge = findViewById<View>(R.id.editTextTextPersonName4) as EditText
       val ins = findViewById<View>(R.id.editTextTextPersonName5) as EditText
        val savebtn = findViewById<View>(R.id.button) as Button

        /*  //  val responseText = findViewById<View>(R.id.textView) as TextView
           //val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
           val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

           val call: Call<RecipeDetails?>? = apiInterface!!.getRecipies()

           call?.enqueue(object : Callback<RecipeDetails?> {
               override fun onResponse(
                   call: Call<RecipeDetails?>?,
                   response: Response<RecipeDetails?>

               ) {
                   Log.d("123abcTAG", response.code().toString() + "")
                   var displayResponse = ""
                   val resource: RecipeDetails? = response.body()
                   val datumList = resource?.data

                   for (datum in datumList!!) {
                       displayResponse += """ ${datum.title} ${datum.author}
                           ${datum.ingredients}
                           ${datum.instructions}
   """
                   }
                   responseText.text = displayResponse
                   Log.d("123abcTAG", displayResponse + "")

               }

               override fun onFailure(call: Call<RecipeDetails?>, t: Throwable?) {
                   call.cancel()
               }
           })*/

        savebtn.setOnClickListener {


            var f = RecipeDetails.Datum( title.text.toString(),author.text.toString(),
                inge.text.toString(),ins.text.toString())

            addUser(f,onResult = {
                title.setText("")
                author.setText("")
                inge.setText("")
                ins.setText("")
                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();

            })
        }
    }


    fun addUser(userData: RecipeDetails.Datum, onResult: (RecipeDetails?) -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)


        if (apiInterface != null) {
             apiInterface.addRecipie(userData).enqueue(object : Callback<RecipeDetails> {
                override fun onResponse(call: Call<RecipeDetails>, response: Response<RecipeDetails>) {
                    onResult(response.body())


                }

                override fun onFailure(call: Call<RecipeDetails>, t: Throwable) {
                    onResult(null)
                }

            })
        }
    }

    fun viewreceipe(view: android.view.View) {
        intent = Intent(applicationContext,ViewRecipes::class.java)
        startActivity(intent)
    }
}
