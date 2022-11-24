package kr.hs.dgsw.projectpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kr.hs.dgsw.projectpractice.MainActivity.Companion.category
import kr.hs.dgsw.projectpractice.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityDetailBinding
    private val commentsAdapter = CommentsAdapter()
    private var id = 0
    private val thiscategory: String by lazy {
        category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        initListener()
        id = intent.getIntExtra("id", 0)
//        val category = category

        getQuestion(thiscategory)
        setRcv(thiscategory)
    }

    private fun initListener(){
        binding.btnBack.setOnClickListener(this)
        binding.postBtn.setOnClickListener(this)
    }

    private fun getQuestion(category: String) {
        RetrofitBuilder.api.getQuestion(category, id).enqueue(object :
            Callback<QuestionListData> {
            override fun onResponse(
                call: Call<QuestionListData>,
                response: Response<QuestionListData>,
            ) {
                Log.d("testasd", response.toString())
                if (response.isSuccessful) {
                    Log.d("testasd", response.body().toString())
                    var data = response.body()// GsonConverter를 사용해 데이터매핑
                    if(data != null){
                        binding.titleQuestionEt.setText(data.title)
                        binding.contentsQuestionTv.text = data.contents
                    }

                }
            }

            override fun onFailure(call: Call<QuestionListData>, t: Throwable) {
                Log.d("testasd", "실패$t")
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getComment(category: String) {
        RetrofitBuilder.api.getComment(category, id).enqueue(object :
            Callback<List<CommentListData>> {
            override fun onResponse(
                call: Call<List<CommentListData>>,
                response: Response<List<CommentListData>>,
            ) {
                Log.d("testasd", response.toString())
                if (response.isSuccessful) {
                    Log.d("testasd", response.body().toString())
                    var data = response.body()// GsonConverter를 사용해 데이터매핑
                    if(data != null){
                        commentsAdapter.data = data
                    } else {
                        commentsAdapter.data = listOf()
                    }

                    commentsAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<List<CommentListData>>, t: Throwable) {
                Log.d("testasd", "실패$t")
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun postComment(commentData: CommentData) {
        RetrofitBuilder.api.postComment(commentData).enqueue(object :
            Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>,
            ) {
                Log.d("testasd", response.toString())
                if (response.isSuccessful) {
                    Log.d("testasd", response.body().toString())
                    var data = response.body()// GsonConverter를 사용해 데이터매핑
                    getComment(thiscategory)

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("testasd", "실패$t")
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setRcv(category: String) {
        binding.rcvComments.adapter = commentsAdapter
        binding.rcvComments.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getComment(category)
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.btnBack -> {
                finish()
            }
            binding.postBtn -> {
                if(binding.commentsEt.text.isNullOrBlank()){
                    Toast.makeText(this, "댓글 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    postComment(
                        CommentData(
                            id+1,
                            binding.commentsEt.text.toString()
                        )
                    )
                    binding.commentsEt.setText("")
                }
            }
        }
    }
}