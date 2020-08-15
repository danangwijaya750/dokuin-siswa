package id.infiniteuny.dokuin.data.model
import com.google.gson.annotations.SerializedName


data class VerifyResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
) {

    data class Data(
        @SerializedName("status")
        var status: String?
    )
}