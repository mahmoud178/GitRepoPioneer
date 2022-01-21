package com.example.pioneerstask.ui.display

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pioneerstask.R
import com.example.pioneerstask.data.model.Item
import com.example.pioneerstask.utils.Lottie
import com.example.pioneerstask.utils.makeToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_display.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView


class DisplayActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var adapter: GitAdapter
    private var itemsVisibility: Boolean = false
    private val myCalendar = Calendar.getInstance()
    private var programmingLang = ""
    private lateinit var programmingLangItems: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        initUI()
    }

    private fun initUI() {
        closeFilter.setOnClickListener(this)
        filterClick.setOnClickListener(this)
        dateLayout.setOnClickListener(this)
        applyFilterClick.setOnClickListener(this)
        selectedDateTxt.text = updateView(myCalendar.time)
        programmingLangItems = arrayOf(
            "All Languages",
            "java",
            "kotlin",
            "swift",
            "TypeScript",
            "Go",
            "Rust",
            "Python",
            "JavaScript",
            "Julia"
        )
        val adapter: SpinnerAdapter =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                programmingLangItems
            )

        spinner.adapter = adapter
        spinner.onItemSelectedListener = this;

    }

    @SuppressLint("CheckResult")
    private fun getData(created: String, itemsPerPage: String, lang: String) {

        Lottie().showProgress(supportFragmentManager, true)
        val displayViewModel =
            ViewModelProviders.of(this).get(DisplayViewModel::class.java)

        displayViewModel.getData(
            created, "stars", "desc", itemsPerPage, lang
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it.items.isNotEmpty()) {
                    provideData(it.items)

                } else {
                    this.makeToast("No Data To Be previewed")

                }
                Lottie().showProgress(supportFragmentManager, false)
            }, { e ->
                this.makeToast("" + e.message)
                Lottie().showProgress(supportFragmentManager, false)
            })
    }

    private fun resetViews() {
        programmingLang = ""
        itemsCountEdit.setText("", TextView.BufferType.EDITABLE)
        selectedLangTxt.text = programmingLang
    }

    private fun provideData(list: List<Item>) {
        adapter = GitAdapter(this, list)
        displayRecycler.layoutManager =
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
        displayRecycler.adapter = adapter
    }

    override fun onClick(p0: View?) {
        when (p0) {
            closeFilter -> {
                itemsVisibility = true
                closeFilter.visibility = View.GONE
                filterComponents.visibility = View.GONE
            }
            filterClick -> {
                if (itemsVisibility) {
                    closeFilter.visibility = View.VISIBLE
                    filterComponents.visibility = View.VISIBLE
                }
            }
            dateLayout -> {
                DatePickerDialog(
                    this@DisplayActivity, date, myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
            applyFilterClick -> {
                validateInputs()
            }
        }
    }

    private var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
            myCalendar.set(Calendar.YEAR, p1)
            myCalendar.set(Calendar.MONTH, p2)
            myCalendar.set(Calendar.DAY_OF_MONTH, p3)
            selectedDateTxt.text = updateView(myCalendar.time)

        }

    private fun updateView(time: Date): String {
        val myFormatApi = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormatApi, Locale.US)
        return sdf.format(time)
    }

    private fun validateInputs() {
        when {
            itemsCountEdit.text.trim().toString()
                .isEmpty() -> {
                this.makeToast("Viewed Items Can not Be Empty")
            }
            else -> {
                programmingLang = if (programmingLang.isNotEmpty()) {
                    "language:$programmingLang"
                } else {
                    "language"
                }
                getData(
                    "created:>${updateView(myCalendar.time)}",
                    itemsCountEdit.text.toString(), programmingLang
                )
                resetViews()
            }
        }


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        programmingLang = if (p2 == 0) {
            ""
        } else {
            programmingLangItems[p2]
        }
        selectedLangTxt.text = programmingLang


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}