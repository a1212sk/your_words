package alexander.skornyakov.yourwords.ui.main.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.databinding.WordslistItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordsListRecyclerViewAdapter(val clickListener: WordsClickListener)
    : ListAdapter<Word, WordsListRecyclerViewAdapter.WordViewHolder>(
    SetsDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

        class WordViewHolder private constructor(val binding: WordslistItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Word, clickListener: WordsClickListener) {
            //binding.wordTextView.text = item.word
            binding.word = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordslistItemBinding.inflate(layoutInflater,parent,false)
                layoutInflater.inflate(R.layout.wordslist_item, parent, false)
                return WordViewHolder(
                    binding
                )
            }
        }
    }
}

class SetsDiffCallback : DiffUtil.ItemCallback<Word>(){
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.wordId == newItem.wordId
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return  oldItem == newItem
    }

}

class WordsClickListener(val clickListener: (wordId: Long) -> Unit) {
    fun onClick(word: Word?){
        word?.let {
            clickListener(it.wordId)
        }
    }
}