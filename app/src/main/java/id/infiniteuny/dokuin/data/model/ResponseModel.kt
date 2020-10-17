package id.infiniteuny.dokuin.data.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ResponseModel(
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
)
@Parcelize
data class Data(
    @SerializedName("\$class")
    var classX: String?,
    @SerializedName("fileChecksum")
    var fileChecksum: String?,
    @SerializedName("fileName")
    var fileName: String?,
    @SerializedName("fileTitle")
    var fileTitle: String?,
    @SerializedName("owner")
    var owner: String?,
    @SerializedName("signator1")
    var signator1: String?,
    @SerializedName("signator2")
    var signator2: String?,
    @SerializedName("signatureFile")
    var signatureFile: String?,
    @SerializedName("valid")
    var valid: String?,
    @SerializedName("timestamp")
    var timestamp:String?
):Parcelable