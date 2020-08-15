package id.infiniteuny.dokuin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentModel(
    var studentId:String,
    var name:String,
    var address:String,
    var nisn:String,
    var status:String
):Parcelable