package alexander.skornyakov.yourwords.ui.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.Word
import alexander.skornyakov.yourwords.data.WordsSet
import alexander.skornyakov.yourwords.databinding.SetsItemBinding
import alexander.skornyakov.yourwords.ui.sets.SetsRecyclerViewAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordsListRecyclerViewAdapter : ListAdapter<Word, WordsListRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class WordViewHolder private constructor(val binding: SetsItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Word) {
            binding.wordTextView.text = item.word
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SetsItemBinding.inflate(layoutInflater,parent,false)
                layoutInflater.inflate(R.layout.wordslist_item, parent, false)
                return WordViewHolder(binding)
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