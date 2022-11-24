package kr.hs.dgsw.projectpractice

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.projectpractice.databinding.CommentsListItemBinding
import kr.hs.dgsw.projectpractice.databinding.QuestionListItemBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsVH>() {

    var data = listOf<CommentListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsVH {
        val binding = CommentsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentsVH(binding)
    }

    override fun onBindViewHolder(holder: CommentsVH, position: Int) {
        val comments = data[position]
        holder.setData(comments)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CommentsVH(val binding: CommentsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(comment: CommentListData) {
            binding.commentTv.text = comment.comment
        }
    }
}
