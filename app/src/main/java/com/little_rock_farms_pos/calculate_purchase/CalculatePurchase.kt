package com.little_rock_farms_pos.calculate_purchase

import CalculatePurchasesCustomAdapter
import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.google.android.material.textview.MaterialTextView
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.databinding.LrfCalculatePurchaseBinding
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.entities.Product
import com.little_rock_farms_pos.persistence.models.CategoryViewModel
import com.little_rock_farms_pos.persistence.models.ProductViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.stream.Collectors


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CalculatePurchase : Fragment() {

    private lateinit var _binding: LrfCalculatePurchaseBinding
    private lateinit var _categoryViewModel: CategoryViewModel
    private lateinit var _productViewModel: ProductViewModel
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _recyclerAdapter: CalculatePurchasesCustomAdapter
    private lateinit var _selected_category: Category
    private lateinit var _selected_product: Product

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = LrfCalculatePurchaseBinding.inflate(inflater, container, false)
        _categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        _productViewModel = ViewModelProviders.of(this)[ProductViewModel::class.java]
        populateDropDowns(this.context)
        populateRecyclerView(this.context)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.buttonAddPurchase.setOnClickListener {
            addPurchaseHandler()
        }

        _binding.buttonNewPurchase.setOnClickListener {
            _recyclerAdapter.clearItems()
        }

        _binding.buttonCalculateTotal.setOnClickListener{
            val total = _recyclerAdapter.getItems().map{ it.subtotal }.sum()
            val formatter = DecimalFormat("##0.00#")
            val priceFormatted = formatter.format(total)
            _binding.totalPrice.text = String.format("$ %s", priceFormatted.toString())
        }

        _binding.calculatePurchaseProductDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val category = (selectedItemView as MaterialTextView).text.toString()
                lifecycleScope.launch {
                    _selected_category = _categoryViewModel.findAll().filter { it.categoryName == category }[0]
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // no-op
            }
        }

        _binding.calculatePurchaseProductDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val product = (selectedItemView as MaterialTextView).text.toString()
                lifecycleScope.launch {
                    _selected_product = _productViewModel.findAll().filter { it.productName == product }[0]
                }
                if (::_selected_product.isInitialized) {
                    val formatter = DecimalFormat("##0.00#")
                    val priceFormatted = formatter.format(_selected_product.productPrice)
                    _binding.calculatePurchasePriceHint.text = String.format("$ %s", priceFormatted.toString())
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // No-op
            }
        }
    }

    private fun populateRecyclerView(context: Context?) {
        lifecycleScope.launch {
            _recyclerView = _binding.root.findViewById(com.little_rock_farms_pos.R.id.calculate_price_recycler_view)
            _recyclerView.layoutManager = LinearLayoutManager(context)
            val data = ArrayList<CalculatePurchaseCardViewModel>()
            val adapter = CalculatePurchasesCustomAdapter(data)
            _recyclerAdapter = adapter
            _recyclerView.adapter = _recyclerAdapter
        }
    }

    private fun populateDropDowns(context: Context?) {
        lifecycleScope.launch {
            val categories = _categoryViewModel.findAll().stream().map { it.categoryName }.collect(Collectors.toList())

            val categoryAdapter: ArrayAdapter<String>? = context?.let {
                ArrayAdapter<String>(
                    it, R.layout.lrf_spinner_item, categories
                )
            }
            categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val categorySpinner = _binding.root.findViewById(R.id.calculate_purchase_category_dropdown) as Spinner
            categorySpinner.adapter = categoryAdapter

            val categoryName = categorySpinner.selectedItem?.toString()

            if (categoryName != null) {
                val category = _categoryViewModel.findAll().filter { it.categoryName == categoryName }[0].categoryId
                val products = _productViewModel.findByCategoryId(category!!).map { it.productName }

                val productAdapter: ArrayAdapter<String>? = context?.let {
                    ArrayAdapter<String>(
                        it, R.layout.lrf_spinner_item, products
                    )
                }
                productAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val productSpinner = _binding.root.findViewById(R.id.calculate_purchase_product_dropdown) as Spinner
                productSpinner.adapter = productAdapter

                _recyclerAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                    override fun onChanged() {
                        val total = _recyclerAdapter.getItems().map{ it.subtotal }.sum()
                        val formatter = DecimalFormat("##0.00#")
                        val priceFormatted = formatter.format(total)
                        _binding.totalPrice.text = String.format("$ %s", priceFormatted.toString())
                    }
                })

                if (products.isNotEmpty()) {
                    val product = _productViewModel.findAll().filter { it.productName == products[0] }[0]
                    val formatter = DecimalFormat("##0.00#")
                    val priceFormatted = formatter.format(product.productPrice)
                    _binding.calculatePurchasePriceHint.text = String.format("$ %s", priceFormatted.toString())
                }
            }
        }
    }

    private fun addPurchaseHandler() {
        val categorySpinner = _binding.root.findViewById(R.id.calculate_purchase_category_dropdown) as Spinner
        val categoryName = categorySpinner.selectedItem?.toString()
        val productSpinner = _binding.root.findViewById(R.id.calculate_purchase_product_dropdown) as Spinner
        val productName = productSpinner.selectedItem?.toString()

        val inputQuantity = _binding.editTextPurchaseQuantity.text.toString()

        if (categoryName == null && productName == null || inputQuantity.isEmpty()) {
            return
        }

        val formatter = DecimalFormat("##0.00#")
        val inputQuantityFormatted = formatter.format(inputQuantity.toFloat())

        lifecycleScope.launch {
            try {
                val category = _categoryViewModel.findAll().filter { category -> category.categoryName == categoryName }[0]
                val product = _productViewModel.findByCategoryId(category.categoryId!!).filter { it.productName == productName }[0]

                val priceString = String.format("$ %s", formatter.format(product.productPrice).toString())
                val subtotal = product.productPrice.toFloat() * inputQuantityFormatted.toFloat()
                val subtotalString = String.format("$ %s", formatter.format(subtotal).toString())

                categoryName?.let {
                    if (productName != null) {
                        val newPurchase = CalculatePurchaseCardViewModel(
                            category= it,
                            product=productName,
                            price = product.productPrice.toFloat(),
                            price_string = priceString,
                            quantity = inputQuantityFormatted,
                            subtotal = subtotal,
                            subtotal_string = subtotalString
                        )

                        _recyclerAdapter.addItem(newPurchase)
                        _binding.totalPrice.text = "-"
                    }
                }

            } catch (e: Exception) {
                Log.e("manage_products", "Failed to add product", e)
            }
        }
    }
}