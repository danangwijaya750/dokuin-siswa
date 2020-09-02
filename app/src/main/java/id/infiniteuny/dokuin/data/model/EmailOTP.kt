package id.infiniteuny.dokuin.data.model
import com.google.gson.annotations.SerializedName


data class SendEmailOTP(
    @SerializedName("digit")
    var digit: Int=0,
    @SerializedName("expire")
    var expire: Int=0,
    @SerializedName("maxattempt")
    var maxattempt: Int=1,
    @SerializedName("message")
    var message: String="Please Enter This OTP : {{otp}}",
    @SerializedName("recipient")
    var recipient: String,
    @SerializedName("subject")
    var subject: String="Kode OTP DOKUIN.ID"
)
data class VerifyEmailOTP(
    @SerializedName("digit")
    var digit: Int=0,
    @SerializedName("expire")
    var expire: Int=0,
    @SerializedName("otpstr")
    var otpstr: String
)
data class BigBoxResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?
)