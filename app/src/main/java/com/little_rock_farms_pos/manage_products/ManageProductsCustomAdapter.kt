import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.manage_products.ProductCardViewModel


class ManageProductsCustomAdapter(private val initialItems: MutableList<ProductCardViewModel>) : RecyclerView.Adapter<ManageProductsCustomAdapter.ViewHolder>() {

    private var items: MutableList<ProductCardViewModel> = initialItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lrf_manage_products_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = initialItems[position]
        holder.categoryName.text = itemsViewModel.category
        holder.productName.text = itemsViewModel.product
        holder.priceValue.text = itemsViewModel.price
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.card_category)
        val productName: TextView = view.findViewById(R.id.card_product)
        val priceValue: TextView = view.findViewById(R.id.card_price)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEntireListOfItems(productCardViewModels: MutableList<ProductCardViewModel>) {
        items = productCardViewModels
        notifyDataSetChanged()
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