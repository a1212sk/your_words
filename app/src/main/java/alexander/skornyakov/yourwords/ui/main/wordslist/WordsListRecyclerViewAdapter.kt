package alexander.skornyakov.yourwords.ui.main.wordslist

import alexander.skornyakov.yourwords.R
import alexander.skornyakov.yourwords.data.entity.Word
import alexander.skornyakov.yourwords.databinding.WordslistItemBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        class WordViewHolder private constructor(val binding: WordslistItemBinding)
            : RecyclerView.ViewHolder(binding.root){

            private fun enableSetButtons(){
                binding.wordTextView.visibility = View.GONE
                binding.wordDeleteBtn.visibility = View.VISIBLE
                binding.wordEditBtn.visibility = View.VISIBLE
            }

            private fun disableSetButtons(){
                binding.wordTextView.visibility = View.VISIBLE
                binding.wordDeleteBtn.visibility = View.GONE
                binding.wordEditBtn.visibility = View.GONE
            }

            fun bind(item: Word, clickListener: WordsClickListener) {
                binding.word = item
                binding.root.setOnLongClickListener{
                    enableSetButtons()
                    GlobalScope.launch (Dispatchers.Main){
                        delay(2000)
                        disableSetButtons()
                    }
                    true
                }
                binding.constraintLayout.setOnClickListener{ clickListener.onClick(it,item.id) }
                binding.wordEditBtn.setOnClickListener{ clickListener.onClick(it,item.id) }
                binding.wordDeleteBtn.setOnClickListener{ clickListener.onClick(it,item.id) }
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
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return  oldItem == newItem
    }

}

class WordsClickListener(val clickListener: (view: View, wordId: String) -> Unit) {
    fun onClick(view: View, wordId: String){
        wordId?.let {
            clickListener(view,wordId)
        }
    }
}
