package id.infiniteuny.dokuin.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.ui.otp.VerifyOtpActivity
import id.infiniteuny.dokuin.ui.upload_file.UploadFilePresenter
import id.infiniteuny.dokuin.util.logE
import id.infiniteuny.dokuin.util.toast
import id.infiniteuny.dokuin.util.toastLong
import kotlinx.android.synthetic.main.activity_detail_file.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailFileActivity : BaseActivity((R.layout.activity_detail_file)),DetailFileView {

    private val presenter by inject<DetailFilePresenter>{
        parametersOf(this)
    }
    private lateinit var data:DocumentModel
    override fun viewCreated(savedInstanceState: Bundle?) {
        data=intent.extras?.get("data") as DocumentModel
        showData(data)
        if(SharedPref(this).userRole=="school"){
            btn_approve.visibility= View.VISIBLE
            btn_approve.setOnClickListener {
                presenter.sendOtp(SharedPref(this).userEmail)
            }
        }
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showData(data:DocumentModel){
        tv_doc_title.text=data.title
        val sles = if(data.status=="approved"){
            "document"
        }else{
            "document/unverif"
        }
        val base="http://bnpb.divistant.com:3000/"
        val url= "${base}${sles}?filename=${data.title}&key=${SharedPref(this).key}"
        logE(url)
        val urls= "https://drive.google.com/viewerng/viewer?embedded=true&url=$url"
        //val doc= "<iframe src='http://docs.google.com/viewer?url=${url}&embedded=true'width='100%'height='100%'style='border: none;'></iframe>";
        wv_viewer.settings.javaScriptEnabled = true
        wv_viewer.settings.allowFileAccess = true
        wv_viewer.loadUrl(urls)
        toastLong("Please reselect the document again if the pdf file is not showing!")
    }

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(presenter)
    }

    override fun onLoading(state: Boolean) {
        when(state){
            true->pg_loading.visibility=View.VISIBLE
            false->pg_loading.visibility=View.GONE
        }
    }

    override fun onError(msg: String) {
        toast(msg)
    }

    override fun showResult(data: VerifyResponse) {
        toast(data.data!!.status)
    }

    override fun otpSended() {
        val email=SharedPref(this).userEmail
        toast("Kode OTP Terkirim ke Email $email")
        val intent= Intent(this, VerifyOtpActivity::class.java)
        intent.putExtra("caller","verif")
        intent.putExtra("email",email)
        intent.putExtra("data-doc",data)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}



