package id.infiniteuny.dokuin.data.model
import com.google.gson.annotations.SerializedName


data class VerifyResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var status: Boolean?
) {

    data class Data(
    @SerializedName("\$class")
    var classX: String?,
    @SerializedName("document")
    var document: String?,
    @SerializedName("owner")
    var owner: String?,
    @SerializedName("signatureId")
    var signatureId: String?,
    @SerializedName("value")
    var value: String?
    )
}