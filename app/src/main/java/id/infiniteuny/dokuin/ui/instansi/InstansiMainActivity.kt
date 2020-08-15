package id.infiniteuny.dokuin.ui.instansi

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.detail_siswa.DetailSiswaActivity
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.util.logE
import kotlinx.android.synthetic.main.activity_instansi_main.*

class InstansiMainActivity : BaseActivity(R.layout.activity_instansi_main) {

    private val db=FirebaseFirestore.getInstance()
    private val listSearch= mutableListOf<UserModel>()
    private val rvAdapter=object:RvAdapter<UserModel>(listSearch,{
        handleClick(it)
    }){
        override fun layoutId(position: Int, obj: UserModel): Int =R.layout.item_student

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =SearchVH(view)

    }
    private fun handleClick(data: UserModel){
        val intent=Intent(this,DetailSiswaActivity::class.java)
        val obj=StudentModel(data.uid.toString(),data.name.toString(),"Godean Sleman","1235522","")
        intent.putExtra("data",obj)
        startActivity(intent)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

        user_profile_instansi.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        tv_user_name.text = getString(R.string.greeting, SharedPref(this).userName)

        rv_user.apply {
            adapter=rvAdapter
            val layMan=LinearLayoutManager(this@InstansiMainActivity)
            layMan.orientation=LinearLayoutManager.VERTICAL
            layoutManager=layMan
        }
        getSearched()

        et_search.setOnEditorActionListener(object:TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId==EditorInfo.IME_ACTION_SEARCH){
                    performSearch()
                    return true
                }
                return false
            }
        })
    }

    private fun performSearch(){
        listSearch.clear()
        db.collection("user").document(et_search.text.toString()).get()
            .addOnSuccessListener {
                if(it.exists()){
                    val data= it.toObject(UserModel::class.java)!!
                    listSearch.add(data)
                    addToHistory(data)
                }
                showToRv()
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
            }
    }
    private fun addToHistory(data:UserModel){
        db.collection("history")
            .document(data.uid.toString())
            .set(data)
            .addOnSuccessListener {
                logE("success")
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
            }
    }

    private fun getSearched(){
        listSearch.clear()
        db.collection("history").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    it.forEach {snap->
                        listSearch.add(snap.toObject(UserModel::class.java))
                    }
                    showToRv()
                }
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
            }
    }
    private fun showToRv(){
        rvAdapter.notifyDataSetChanged()
    }


}