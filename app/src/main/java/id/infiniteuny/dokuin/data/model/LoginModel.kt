package id.infiniteuny.dokuin.data.model
import com.google.gson.annotations.SerializedName


data class LoginModel(
    @SerializedName("data")
    var `data`: String?,
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean
)