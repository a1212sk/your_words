package alexander.skornyakov.yourwords.ui.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.Word
import alexander.skornyakov.yourwords.databinding.CardsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CardsRecyclerViewAdapter
    : ListAdapter<Word, CardsRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class WordViewHolder private constructor(val binding: CardsItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Word) {
            binding.wordTextView.text = item.word
            binding.word = item
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardsItemBinding.inflate(layoutInflater,parent,false)
                layoutInflater.inflate(R.layout.cards_item, parent, false)
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