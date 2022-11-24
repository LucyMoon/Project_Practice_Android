package kr.hs.dgsw.projectpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kr.hs.dgsw.projectpractice.databinding.ActivityAddBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityAddBinding
    private var category = "ALL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        val save = intent.getIntExtra("save", 0)
        initListener()
        setSpinner()
        if (save == 1) {
            binding.etTitle.setText(App.prefs.title)
            binding.etContents.setText(App.prefs.content)
            binding.categorySpinner.setSelection(App.prefs.category)
            setCategoryText(App.prefs.category)
        }
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener(this)
        binding.tvAddPost.setOnClickListener(this)
    }

    private fun postQuestion(questionListData: QuestionListData) {
        RetrofitBuilder.api.postQuestion(questionListData).enqueue(object :
            Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>,
            ) {
                if (response.isSuccessful) {
                    var data = response.body()// GsonConverter를 사용해 데이터매핑
                    finish()

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("error", "실패$t")
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnBack -> {
                App.prefs.title = binding.etTitle.text.toString()
                App.prefs.content = binding.etContents.text.toString()
                App.prefs.category = binding.categorySpinner.selectedItemPosition
                finish()
            }
            binding.tvAddPost -> {
                if (checkBlank()) {
                    postQuestion(
                        QuestionListData(
                            null,
                            category,
                            binding.etTitle.text.toString(),
                            binding.etContents.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun checkBlank(): Boolean {
        if (binding.etTitle.text.isNullOrBlank()) {
            Toast.makeText(this, "타이틀을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
            binding.etTitle.requestFocus()
            return false
        } else if (binding.etContents.text.isNullOrBlank()) {
            Toast.makeText(this, "내용을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
            binding.etContents.requestFocus()
            return false
        }
        return true
    }

    private fun setSpinner() {
        var data = resources.getStringArray(R.array.postcategory)
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        binding.categorySpinner.adapter = adapter

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setCategoryText(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun setCategoryText(position: Int) {
        when (position) {
            0 -> {
                category = "Android"
            }
            1 -> {
                category = "Front"
            }
            2 -> {
                category = "Back"
            }
            3 -> {
                category = "Game"
            }
            else -> {
                category = "Etc"
            }
        }
    }

}