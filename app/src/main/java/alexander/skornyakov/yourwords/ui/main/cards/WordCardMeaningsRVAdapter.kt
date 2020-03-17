package alexander.skornyakov.yourwords.ui.main.cards

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.databinding.WordCardMeaningsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordCardMeaningsRVAdapter :
    ListAdapter<Meaning,WordCardMeaningsRVAdapter.ViewHolder>(SetsDiffCallback()) {

    class ViewHolder private constructor(val binding: WordCardMeaningsItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordCardMeaningsItemBinding.inflate(layoutInflater,parent,false)
                layoutInflater.inflate(R.layout.sets_item, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(meaning: Meaning){
            binding.meaning.text = meaning.meaning
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meaning = getItem(position)
        holder.bind(meaning)
    }


}

class SetsDiffCallback : DiffUtil.ItemCallback<Meaning>(){
    override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return (oldItem.id == newItem.id) && (oldItem.meaning == newItem.meaning)
    }

    override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return  oldItem.meaning == newItem.meaning
    }
}