package alexander.skornyakov.yourwords.ui.main.newword

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Meaning
import alexander.skornyakov.yourwords.databinding.NewWordMeaningListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MeaningsAdapter : ListAdapter<Meaning, MeaningsAdapter.MeaningViewHolder>(DiffCalback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeaningsAdapter.MeaningViewHolder {
        return MeaningViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MeaningsAdapter.MeaningViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class MeaningViewHolder(val binding: NewWordMeaningListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meaning){
            binding.meaning = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : MeaningViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewWordMeaningListItemBinding.inflate(layoutInflater, parent, false)
                layoutInflater.inflate(R.layout.new_word_meaning_list_item, parent, false)
                return (MeaningViewHolder(binding))
            }
        }
    }

}

class DiffCalback : DiffUtil.ItemCallback<Meaning>() {
    override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return oldItem.meaning == newItem.meaning
    }

    override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
        return oldItem.meaning == newItem.meaning
    }

}
