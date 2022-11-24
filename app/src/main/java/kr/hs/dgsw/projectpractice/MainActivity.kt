package kr.hs.dgsw.projectpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kr.hs.dgsw.projectpractice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    private val questionAdapter = QuestionAdapter()
    companion object {
        var category = "ALL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initListener()
        setSpinner()
        setRcv()
        checkDB()
    }

    private fun checkDB() {
        if(App.prefs.title.isNullOrBlank() && App.prefs.content.isNullOrBlank()){
            binding.saveLayout.visibility = View.GONE
        } else {
            binding.saveLayout.visibility = View.VISIBLE
        }
    }

    private fun initListener(){
        binding.addBtn.setOnClickListener(this)
        binding.savePostBtn.setOnClickListener(this)
    }

    private fun getAllQuestion() {
        RetrofitBuilder.api.getAllQuestion(category).enqueue(object :
            Callback<List<QuestionListData>> {
            override fun onResponse(
                call: Call<List<QuestionListData>>,
                response: Response<List<QuestionListData>>,
            ) {
                Log.d("testasd", response.toString())
                if (response.isSuccessful) {
                    Log.d("testasd", response.body().toString())
                    var data = response.body()// GsonConverter를 사용해 데이터매핑
                    if (data != null) {
                        questionAdapter.data = data
                    } else {
                        questionAdapter.data = listOf()
                    }
                    questionAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<QuestionListData>>, t: Throwable) {
                Log.d("testasd", "실패$t")
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun setRcv() {
        binding.rcvQuestion.adapter = questionAdapter
        binding.rcvQuestion.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        category = "ALL"
        getAllQuestion()

    }

    private fun setSpinner() {
        var data = resources.getStringArray(R.array.category)
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
                    when (position) {
                        0 -> {
                            category = "ALL"
                            getAllQuestion()
                        }
                        1 -> {
                            category = "Android"
                            getAllQuestion()
                        }
                        2 -> {
                            category = "Front"
                            getAllQuestion()
                        }
                        3 -> {
                            category = "Back"
                            getAllQuestion()
                        }
                        4 -> {
                            category = "Game"
                            getAllQuestion()
                        }
                        else -> {
                            category = "Etc"
                            getAllQuestion()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.addBtn -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            binding.savePostBtn -> {
                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra("save", 1)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAllQuestion()
        checkDB()
    }

    override fun onRestart() {
        super.onRestart()
        getAllQuestion()
        checkDB()
    }


}