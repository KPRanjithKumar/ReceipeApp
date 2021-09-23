package ran.am.receipeapp

import com.google.gson.annotations.SerializedName

class RecipeDetails {

    var data: List<Datum>? = null

    class Datum {

        @SerializedName("pk")
        var id: Int? = null


        @SerializedName("titlee")
        var title: String? = null

        @SerializedName("author")
        var author: String? = null



        @SerializedName("ingredients")
        var ingredients: String? = null

        @SerializedName("instructions")
        var instructions: String? = null
    }
}