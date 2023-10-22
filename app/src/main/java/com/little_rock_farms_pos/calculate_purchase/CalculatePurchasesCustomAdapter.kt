import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.calculate_purchase.CalculatePurchaseCardViewModel


class CalculatePurchasesCustomAdapter(private val initialItems: MutableList<CalculatePurchaseCardViewModel>) : RecyclerView.Adapter<CalculatePurchasesCustomAdapter.ViewHolder>() {

    private var items: MutableList<CalculatePurchaseCardViewModel> = initialItems
    private lateinit var _parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lrf_calculate_purchase_card_view, parent, false)
        _parent = parent
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = initialItems[position]
        holder.categoryName.text = itemsViewModel.category
        holder.productName.text = itemsViewModel.product
        holder.priceValue.text = itemsViewModel.price_string
        holder.quantityValue.text = itemsViewModel.quantity
        holder.subtotalValue.text = itemsViewModel.subtotal_string
        holder.buttonDelete.setOnClickListener {
            val viewHolderPosition = holder.adapterPosition
            items.removeAt(viewHolderPosition)
            notifyItemRemoved(viewHolderPosition)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.card_category)
        val productName: TextView = view.findViewById(R.id.card_product)
        val priceValue: TextView = view.findViewById(R.id.card_price)
        val quantityValue: TextView = view.findViewById(R.id.card_quantity)
        val subtotalValue: TextView = view.findViewById(R.id.card_subtotal)
        val buttonDelete: Button = view.findViewById(R.id.button_delete_purchase_item)
    }

    fun addItem(calculatePurchaseCardViewModel: CalculatePurchaseCardViewModel) {
        items.add(calculatePurchaseCardViewModel)
        val position = items.indexOf(calculatePurchaseCardViewModel)
        notifyItemInserted(position)
    }

    fun getItems(): MutableList<CalculatePurchaseCardViewModel> {
        return items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        items = mutableListOf()
        notifyDataSetChanged()
    }
}