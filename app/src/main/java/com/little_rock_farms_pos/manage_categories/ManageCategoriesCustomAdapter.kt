import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.manage_categories.CategoryCardViewModel
import com.little_rock_farms_pos.persistence.models.CategoryViewModel


class ManageCategoriesCustomAdapter(private val initialItems: MutableList<CategoryCardViewModel>) : RecyclerView.Adapter<ManageCategoriesCustomAdapter.ViewHolder>() {

    private var items: MutableList<CategoryCardViewModel> = initialItems
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _recyclerAdapter: ManageCategoriesCustomAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lrf_manage_categories_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = initialItems[position]
        holder.textView.text = itemsViewModel.category
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.card_category)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEntireListOfItems(categoryCardViewModels: MutableList<CategoryCardViewModel>) {
        items = categoryCardViewModels
        notifyDataSetChanged()
    }

    fun addItem(categoryCardViewModel: CategoryCardViewModel) {
        items.add(categoryCardViewModel)
        val position = items.indexOf(categoryCardViewModel)
        notifyItemInserted(position)
    }

    fun removeItem(categoryCardViewModel: CategoryCardViewModel) {
        val position = items.indexOf(categoryCardViewModel)
        items.remove(categoryCardViewModel)
        notifyItemRemoved(position)
    }
}