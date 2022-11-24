package kr.hs.dgsw.projectpractice

import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("/getallquestion")
    fun getAllQuestion(@Query("category") category: String) : Call<List<QuestionListData>>

    @POST("/postquestion")
    fun postQuestion(@Body questionListData: QuestionListData) : Call<Void>

    @GET("/getquestion")
    fun getQuestion(@Query("category") category: String, @Query("id") id: Int) : Call<QuestionListData>

    @GET("/getcomments")
    fun getComment(@Query("category") category: String, @Query("id") id: Int) : Call<List<CommentListData>>

    @POST("/postcomment")
    fun postComment(@Body commentData: CommentData) : Call<Void>
}