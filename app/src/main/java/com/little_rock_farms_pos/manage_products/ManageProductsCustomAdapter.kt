import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.manage_products.ProductCardViewModel
import com.little_rock_farms_pos.persistence.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ManageProductsCustomAdapter(private val initialItems: MutableList<ProductCardViewModel>) : RecyclerView.Adapter<ManageProductsCustomAdapter.ViewHolder>() {

    private var items: MutableList<ProductCardViewModel> = initialItems
    private lateinit var database: AppDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lrf_manage_products_card_view, parent, false)
        database = AppDatabase.getInstance(parent.context)

        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = initialItems[position]
        holder.categoryName.text = itemsViewModel.category
        holder.productName.text = itemsViewModel.product
        holder.priceValue.text = itemsViewModel.price
        holder.buttonDelete.setOnClickListener {
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

    override fun getItemCount(): Int {
        return items.size
    }

    private suspend fun deleteItemOnClick(item: ProductCardViewModel) {
        val category = database.categoryDao().findAll().filter { it.categoryName == item.category }[0]
        val product = database.productDao().findByCategoryId(categoryId = category.categoryId!!)
            .filter { it.productName == item.product }[0]
        database.productDao().delete(product)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.card_category)
        val productName: TextView = view.findViewById(R.id.card_product)
        val priceValue: TextView = view.findViewById(R.id.card_price)
        val buttonDelete: Button = view.findViewById(R.id.button_delete_price)
    }

    fun addItem(productCardViewModel: ProductCardViewModel) {
        items.add(productCardViewModel)
        val position = items.indexOf(productCardViewModel)
        notifyItemInserted(position)
    }

    fun getItems(): MutableList<ProductCardViewModel> {
        return items
    }

    fun updateItem(productCardViewModel: ProductCardViewModel) {
        val position = items.indexOf(productCardViewModel)
        items[position] = productCardViewModel
        notifyItemChanged(position)
    }

    fun removeItem(productCardViewModel: ProductCardViewModel) {
        val position = items.indexOf(productCardViewModel)
        items.remove(productCardViewModel)
        notifyItemRemoved(position)
    }
}