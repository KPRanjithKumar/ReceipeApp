package ran.am.receipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewRecipes : AppCompatActivity() {
    private var recipeDetails: List<RecipeDetails.Datum>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_receipe)
        val responseText = findViewById<View>(R.id.textView2) as TextView

        //Here we declare and initialize recyclerview and adapter

        //Calls the api and set it to adapter inside setUpRecyclerViewData()
        getRecipes(onResult = {
            recipeDetails = it
            Log.e("Data", recipeDetails.toString())
         //   setUpRecyclerViewData();
            var stringToBePritined:String? = "";
            for(recipe in recipeDetails!!){
                stringToBePritined = stringToBePritined +recipe.title + "\n"+recipe.author + "\n"+recipe.ingredients + "\n"+recipe.instructions+ "\n\n"
            }
            responseText.text= stringToBePritined
        } );
    }

    private fun getRecipes(onResult: (List<RecipeDetails.Datum>?) -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getRecipies()?.enqueue(object : Callback<List<RecipeDetails.Datum>> {
                override fun onResponse(
                    call: Call<List<RecipeDetails.Datum>>,
                    response: Response<List<RecipeDetails.Datum>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<RecipeDetails.Datum>>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
                    Log.d("aSFFAasd", "onFailure: "+t.message)
                }

            })
        }
    }

    private fun setUpRecyclerViewData() {
        //Here we can use the array and set it to adapter
    }
}