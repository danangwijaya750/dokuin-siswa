package id.infiniteuny.dokuin.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.Data
import id.infiniteuny.dokuin.data.model.HistoryModel
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.ui.otp.VerifyOtpActivity
import id.infiniteuny.dokuin.util.logE
import id.infiniteuny.dokuin.util.toast
import id.infiniteuny.dokuin.util.toastLong
import kotlinx.android.synthetic.main.activity_detail_file.*
import kotlinx.android.synthetic.main.bottom_sheet_detail_document.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class DetailFileActivity : BaseActivity((R.layout.activity_detail_file)), DetailFileView {

    private val presenter by inject<DetailFilePresenter> {
        parametersOf(this)
    }

    private val histories= mutableListOf<HistoryModel>()
    private val rvHistoryAdapter=object:RvAdapter<HistoryModel>(histories, {}){
        override fun layoutId(position: Int, obj: HistoryModel): Int = R.layout.item_history

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = HistoryVH(view)

    }
    private lateinit var data: Data
    private lateinit var sheetBehavior:BottomSheetBehavior<ConstraintLayout>
    override fun viewCreated(savedInstanceState: Bundle?) {
        data = intent.extras?.get("data") as Data
        showData(data)
        if (SharedPref(this).userRole == "school") {
            btn_approve.visibility = View.VISIBLE
            btn_approve.setOnClickListener {
                btn_approve.visibility = View.INVISIBLE
                //presenter.sendOtp(SharedPref(this).userEmail)
                val intent = Intent(this, VerifyOtpActivity::class.java)
                intent.putExtra("caller", "verif")
//                intent.putExtra("email", email)
                intent.putExtra("data-doc", data)
                startActivity(intent)
            }
        }
        if (data.valid == "2") {
            btn_approve.text = "Approved"
            btn_approve.isEnabled = false
            btn_approve.visibility = View.GONE
            btn_download.visibility=View.VISIBLE
            btn_download.setOnClickListener {
                showDialog()
            }
        }
         sheetBehavior=BottomSheetBehavior.from(bottom_sheet)

        iv_more.setOnClickListener {
            if(sheetBehavior.state!=BottomSheetBehavior.STATE_EXPANDED){
                bg.visibility=View.VISIBLE
                sheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
            }
        }
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    bg.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

        iv_back.setOnClickListener {
            onBackPressed()
        }
    }
    private fun showDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        val alertLayout = layoutInflater.inflate(R.layout.layout_dialog_download, null)
        alertDialogBuilder.setView(alertLayout)
        alertDialogBuilder.setCancelable(true)
        val btnAsFile = alertLayout.findViewById<Button>(R.id.btn_as_file)
        val btnPrintable = alertLayout.findViewById<Button>(R.id.btn_as_printable)
        val dialog = alertDialogBuilder.create()
        btnAsFile.setOnClickListener {
            //download As File
            //http://34.123.104.234:5000/8PO6LKAAT9ZY4VORQ02II86P0MD8XS9L1602929089.9789972.pdf/download-for-print
            val base = "http://34.123.104.234:5000/"
            val url = "${base}${data.fileName}/generate/signature/download"
            logE("print : $url")
            presenter.submitAction(data.fileName!!,SharedPref(this).userName,"Mengunduh Dokumen")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
            dialog.dismiss()
        }
        btnPrintable.setOnClickListener {
            //download As Printable
            //http://34.123.104.234:5000/8PO6LKAAT9ZY4VORQ02II86P0MD8XS9L1602929089.9789972.pdf/download-for-print
            val base = "http://34.123.104.234:5000/"
            val url = "${base}${data.fileName}/download-for-print"
            logE("print : $url")
            presenter.submitAction(data.fileName!!,SharedPref(this).userName,"Mengunduh Dokumen")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            presenter.submitAction(data.fileName!!,SharedPref(this).userName,"Mengunduh Dokumen")
            startActivity(browserIntent)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showData(data: Data) {
        tv_doc_title.text = data.fileTitle
//        val sles = if (data.status == "approved") {
//            "document"
//        } else {
//            "document/unverif"
//        }
        val base = "http://34.123.104.234:5000/"
        val url = "${base}${data.fileName}/generate/original"
        logE(url)
        val urls = "https://drive.google.com/viewerng/viewer?embedded=true&url=$url"
        //val doc= "<iframe src='http://docs.google.com/viewer?url=${url}&embedded=true'width='100%'height='100%'style='border: none;'></iframe>";
        wv_viewer.settings.javaScriptEnabled = true
        wv_viewer.settings.allowFileAccess = true
        wv_viewer.loadUrl(urls)
        toastLong("Please reselect the document again if the pdf file is not showing!")
        presenter.submitAction(data.fileName!!,SharedPref(this).userName,"Melihat Dokumen")
    }

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(presenter)
    }

    override fun onLoading(state: Boolean) {
        when (state) {
            true -> pg_loading.visibility = View.VISIBLE
            false -> pg_loading.visibility = View.GONE
        }
    }

    override fun onError(msg: String) {
        toast(msg)
    }

    override fun showResult(data: VerifyResponse) {
        toast(data.message)
        btn_approve.visibility = View.VISIBLE
        btn_approve.text = "Approved"
        btn_approve.isEnabled = false
    }

    override fun otpSended() {
        val email = SharedPref(this).userEmail
        toast("Kode OTP Terkirim ke Email $email")
        val intent = Intent(this, VerifyOtpActivity::class.java)
        intent.putExtra("caller", "verif")
        intent.putExtra("email", email)
        intent.putExtra("data-doc", data)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if(sheetBehavior.state==BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
        }else {
            super.onBackPressed()
            finish()
        }
    }
}



