package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.WordsSet
import alexander.skornyakov.yourwords.databinding.SetsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SetsRecyclerViewAdapter : ListAdapter<WordsSet, SetsRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class WordViewHolder private constructor(val binding: SetsItemBinding) : ViewHolder(binding.root){
        fun bind(item: WordsSet) {
            binding.wordTextView.text = item.name
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SetsItemBinding.inflate(layoutInflater,parent,false)
                layoutInflater.inflate(R.layout.sets_item, parent, false)
                return WordViewHolder(binding)
            }
        }
    }
}

class SetsDiffCallback : DiffUtil.ItemCallback<WordsSet>(){
    override fun areItemsTheSame(oldItem: WordsSet, newItem: WordsSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WordsSet, newItem: WordsSet): Boolean {
        return  oldItem == newItem
    }

}

