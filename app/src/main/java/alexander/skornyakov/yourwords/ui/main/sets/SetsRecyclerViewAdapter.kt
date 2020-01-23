package alexander.skornyakov.yourwords.ui.main.sets

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

class SetsRecyclerViewAdapter(private val clickListener: SetsClickListener)
    : ListAdapter<WordsSet, SetsRecyclerViewAdapter.WordViewHolder>(SetsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(clickListener, item)
    }

    class WordViewHolder private constructor(val binding: SetsItemBinding) : ViewHolder(binding.root){

        private fun enableSetButtons(){
            binding.wordTextView.visibility = View.GONE
            binding.setsDeleteButton.visibility = View.VISIBLE
            binding.setsRenameButton.visibility = View.VISIBLE
        }

        private fun disableSetButtons(){
            binding.wordTextView.visibility = View.VISIBLE
            binding.setsDeleteButton.visibility = View.GONE
            binding.setsRenameButton.visibility = View.GONE
        }

        fun bind(clickListener: SetsClickListener, item: WordsSet) {
            binding.wordsSet = item
            binding.wordTextView.text = item.name
            binding.root.setOnLongClickListener{
                enableSetButtons()
                GlobalScope.launch (Dispatchers.Main){
                    delay(2000)
                    disableSetButtons()
                }
                true
            }
            binding.constraintLayout.setOnClickListener { clickListener.onClick(it,binding.wordsSet) }
            binding.setsRenameButton.setOnClickListener { clickListener.onClick(it, binding.wordsSet) }
            binding.setsDeleteButton.setOnClickListener { clickListener.onClick(it, binding.wordsSet) }
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

class SetsClickListener(val clickListener: (view: View, setId: WordsSet) -> Unit) {
    fun onClick(view: View, set: WordsSet?){
        set?.let {
            clickListener(view, it)
        }
    }
}
