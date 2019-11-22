package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.databinding.SetsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SetsRecyclerViewAdapter(val clickListener: SetsClickListener) : ListAdapter<WordsSet, SetsRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(clickListener,item)
    }

    class WordViewHolder private constructor(val binding: SetsItemBinding) : ViewHolder(binding.root){
        fun bind(clickListener: SetsClickListener, item: WordsSet) {
            binding.wordsSet = item
            binding.wordTextView.text = item.name
            binding.clickListener = clickListener
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
        return oldItem.setId == newItem.setId
    }

    override fun areContentsTheSame(oldItem: WordsSet, newItem: WordsSet): Boolean {
        return  oldItem == newItem
    }

}

class SetsClickListener(val clickListener: (setId: Long) -> Unit) {
    fun onClick(set: WordsSet?){
        set?.let {
            clickListener(it.setId)
        }
    }
}
