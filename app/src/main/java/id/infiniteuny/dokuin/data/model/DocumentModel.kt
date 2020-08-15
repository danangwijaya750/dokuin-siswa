package id.infiniteuny.dokuin.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class DocumentModel (
    @Exclude
    var id:String,
    var title:String,
    var dateUpload: Date,
    var dateApproved:Date,
    var studentId:String,
    var schoolId:String,
    var status:String
):Parcelable{
    constructor():this("","",Date(),Date(),"","","")

    fun withId(id:String): DocumentModel {
        this.id=id
        return this
    }
}