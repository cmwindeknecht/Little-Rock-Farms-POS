import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.manage_categories.ManageCategoriesViewModel
import com.little_rock_farms_pos.manage_products.ManageProductsViewModel
import com.little_rock_farms_pos.persistence.AppDatabase
import com.little_rock_farms_pos.persistence.models.CategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ManageCategoriesCustomAdapter(private val initialItems: MutableList<ManageCategoriesViewModel>) : RecyclerView.Adapter<ManageCategoriesCustomAdapter.ViewHolder>() {

    private var items: MutableList<ManageCategoriesViewModel> = initialItems
    private lateinit var database: AppDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lrf_manage_categories_card_view, parent, false)
        database = AppDatabase.getInstance(parent.context)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = initialItems[position]
        holder.textView.text = itemsViewModel.category
        holder.deletebutton.setOnClickListener {
            val viewHolderPosition = holder.adapterPosition
            val item = items[viewHolderPosition]
            GlobalScope.launch (Dispatchers.IO) {
                deleteItemOnClick(item)
            }
            items.removeAt(viewHolderPosition)
            notifyItemRemoved(viewHolderPosition)
            notifyDataSetChanged()
        }
    }

    private suspend fun deleteItemOnClick(item: ManageCategoriesViewModel) {
        val category = database.categoryDao().findAll().filter { it.categoryName == item.category }[0]
        database.productDao().findByCategoryId(categoryId = category.categoryId!!)
            .forEach { database.productDao().delete(it) }
        database.categoryDao().delete(category)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.card_category)
        val deletebutton: Button = view.findViewById(R.id.button_delete_price)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEntireListOfItems(manageCategoriesViewModels: MutableList<ManageCategoriesViewModel>) {
        items = manageCategoriesViewModels
        notifyDataSetChanged()
    }

    fun addItem(manageCategoriesViewModel: ManageCategoriesViewModel) {
        items.add(manageCategoriesViewModel)
        val position = items.indexOf(manageCategoriesViewModel)
        notifyItemInserted(position)
    }

    fun removeItem(manageCategoriesViewModel: ManageCategoriesViewModel) {
        val position = items.indexOf(manageCategoriesViewModel)
        items.remove(manageCategoriesViewModel)
        notifyItemRemoved(position)
    }
}