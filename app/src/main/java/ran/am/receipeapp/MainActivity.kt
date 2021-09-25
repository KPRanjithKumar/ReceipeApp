package ran.am.receipeapp

import android.app.ProgressDialog
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

        val title = findViewById<View>(R.id.editTextTextPersonName2) as EditText
        val author = findViewById<View>(R.id.editTextTextPersonName3) as EditText
        val inge = findViewById<View>(R.id.editTextTextPersonName4) as EditText
        val ins = findViewById<View>(R.id.editTextTextPersonName5) as EditText
        val savebtn = findViewById<View>(R.id.button) as Button

        savebtn.setOnClickListener {
            var f = RecipeDetails.Datum(title.text.toString(), author.text.toString(),
                                         inge.text.toString(),    ins.text.toString())

            addReceipe(f, onResult = {
                title.setText("")
                author.setText("")
                inge.setText("")
                ins.setText("")
                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }
    }

    fun addReceipe(userData: RecipeDetails.Datum, onResult: (RecipeDetails?) -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addRecipie(userData).enqueue(object : Callback<RecipeDetails> {
                override fun onResponse(
                    call: Call<RecipeDetails>,
                    response: Response<RecipeDetails>
                ) {
                    onResult(response.body())
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<RecipeDetails>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    fun viewreceipe(view: android.view.View) {
        intent = Intent(applicationContext, ViewRecipes::class.java)
        startActivity(intent)
    }
}