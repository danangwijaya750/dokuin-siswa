package id.infiniteuny.dokuin.ui.school.daftar_siswa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.ui.detail_siswa.DetailSiswaActivity
import kotlinx.android.synthetic.main.fragment_daftar_siswa.*


class DaftarSiswaFragment : BaseFragment(R.layout.fragment_daftar_siswa) {

    companion object {
        fun getInstance(): DaftarSiswaFragment = DaftarSiswaFragment()
    }

    private val listStudent = mutableListOf<StudentModel>()

    private val adapterLatestApproved = object : RvAdapter<StudentModel>(listStudent,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: StudentModel): Int = R.layout.item_student

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            StudentVH(view)

    }

    private fun handleClick(data: StudentModel) {
        val intent = Intent(context!!, DetailSiswaActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        rv_latest.apply {
            adapter = adapterLatestApproved
            val layMan = LinearLayoutManager(this@DaftarSiswaFragment.context!!)
            layMan.orientation = LinearLayoutManager.VERTICAL
            layoutManager = layMan
        }
        spinner_class.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                populateData1()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> populateData1()
                    1 -> populateData2()
                    2 -> populateData3()
                }
            }

        }

    }

    private fun populateData1() {
        listStudent.clear()
        listStudent.add(
            StudentModel(
                "xywaEuuVQ3fs9eOaKFJm8cbDSjt1",
                "Danang Wijaya",
                "Berbah, Sleman",
                "5567543",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Ahsan Firdaus",
                "Godean, Sleman",
                "1235522",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Ananda Firdaus",
                "Moyudan, Sleman",
                "2231456",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Ricardo Fereira",
                "Karangmalang",
                "5467321",
                ""
            )
        )
        adapterLatestApproved.notifyDataSetChanged()
    }

    private fun populateData2() {
        listStudent.clear()
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Muhammad Adi",
                "Berbah, Sleman",
                "5567543",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Wijaya D",
                "Kalasan Sleman",
                "4435211",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Ricardo Wijaya",
                "Bulaksumur",
                "1156753",
                ""
            )
        )
        adapterLatestApproved.notifyDataSetChanged()
    }

    private fun populateData3() {
        listStudent.clear()
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Danang Dwiyoga",
                "Godean Sleman",
                "5543654",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Pepi Supepi",
                "Pajangan Bantul",
                "4456543",
                ""
            )
        )
        listStudent.add(
            StudentModel(
                "kgRK8fcJ8pWRNRiIjXBp4KIC3cs2",
                "Pepi Ricardo",
                "Prambanan Sleman",
                "5567632",
                ""
            )
        )
        adapterLatestApproved.notifyDataSetChanged()
    }


}