package kr.hs.dgsw.projectpractice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.projectpractice.databinding.QuestionListItemBinding

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.QuestionVH>() {

    var data = listOf<QuestionListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionVH {
        val binding = QuestionListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionVH(binding, parent.context)
    }

    override fun onBindViewHolder(holder: QuestionVH, position: Int) {
        val question = data[position]
        holder.setData(question, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class QuestionVH(val binding: QuestionListItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun setData(questionListData: QuestionListData, position: Int) {
            binding.titleQuestionTv.text = questionListData.title
            binding.contentsQuestionTv.text = questionListData.contents
            binding.categoryQuestionTv.text = questionListData.category
            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    Log.d("testasd", position.toString())
                    putExtra("id", position)
                }.run { context.startActivity(this) }
            }
        }
    }
}
