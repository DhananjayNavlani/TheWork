package com.example.bookstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookstore.databinding.BookViewBinding

class BookAdapter(val context: Context, val list: List<Books>) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    //inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      inner class ViewHolder(private val binding: BookViewBinding) : RecyclerView.ViewHolder(binding.root) {

          fun bind(book: Books){
              with(book){
                  binding.textTitleBook.text = title
                  binding.textGenreBook.text = genre
                  Glide.with(context)
                      .load(book.bimageUrl)
                      .placeholder(R.drawable.ic_launcher_background)
                      .error(R.drawable.ic_launcher_foreground)
                      .fitCenter()
                      .into(binding.imageBook)
              }
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.book_view,parent,false)
//        return ViewHolder(itemLayout)
        return ViewHolder(BookViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
