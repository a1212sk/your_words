package alexander.skornyakov.yourwords.ui.sets

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.WordsSet
import alexander.skornyakov.yourwords.databinding.SetsItemBinding
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SetsRecyclerViewAdapter(val clickListener: SetsClickListener,
                              val longClickListener: SetsClickListener,
                              val renameListener: SetsClickListener,
                              val deleteListener: SetsClickListener)
    : ListAdapter<WordsSet, SetsRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(clickListener,longClickListener, renameListener, deleteListener, item)
    }

    class WordViewHolder private constructor(val binding: SetsItemBinding) : ViewHolder(binding.root){

        fun bind(clickListener: SetsClickListener,
                 longClickListener: SetsClickListener,
                 renameListener: SetsClickListener,
                 deleteListener: SetsClickListener,
                 item: WordsSet) {
            binding.wordsSet = item
            binding.wordTextView.text = item.name
            binding.clickListener = clickListener
            binding.root.setOnLongClickListener{
                longClickListener.onClick(binding.wordsSet)
                binding.wordTextView.visibility = View.GONE
                binding.setsDeleteButton.visibility = View.VISIBLE
                binding.setsRenameButton.visibility = View.VISIBLE
                GlobalScope.launch (Dispatchers.Main){
                    delay(2000)
                    binding.wordTextView.visibility = View.VISIBLE
                    binding.setsDeleteButton.visibility = View.GONE
                    binding.setsRenameButton.visibility = View.GONE
                }

                true
            }
            binding.setsRenameButton.setOnClickListener { renameListener.onClick(binding.wordsSet) }
            binding.setsDeleteButton.setOnClickListener { deleteListener.onClick(binding.wordsSet) }
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

class SetsClickListener(val clickListener: (setId: WordsSet) -> Unit) {
    fun onClick(set: WordsSet?){
        set?.let {
            clickListener(it)
        }
    }
}
