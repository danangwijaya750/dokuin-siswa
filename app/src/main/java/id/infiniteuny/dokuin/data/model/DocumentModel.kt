package id.infiniteuny.dokuin.data.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class DocumentModel (
    @Exclude
    var id:String,
    var title:String,
    var dateUpload: Date,
    var dateApproved:Date,
    var studentId:String,
    var schoolId:String,
    var status:String
){
    constructor():this("","",Date(),Date(),"","","")

    fun withId(id:String): DocumentModel {
        this.id=id
        return this
    }
}