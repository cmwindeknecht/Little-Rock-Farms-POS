package com.little_rock_farms_pos.manage_products

import ManageProductsCustomAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.databinding.LrfManageProductsBinding
import com.little_rock_farms_pos.persistence.entities.CategoryDTO
import com.little_rock_farms_pos.persistence.entities.Product
import com.little_rock_farms_pos.persistence.models.CategoryViewModel
import com.little_rock_farms_pos.persistence.models.ProductViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.stream.Collectors


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ManageProducts : Fragment() {

    private lateinit var _binding: LrfManageProductsBinding
    private lateinit var _categoryViewModel: CategoryViewModel
    private lateinit var _productViewModel: ProductViewModel
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _recyclerAdapter: ManageProductsCustomAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = LrfManageProductsBinding.inflate(inflater, container, false)
        _categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        _productViewModel = ViewModelProviders.of(this)[ProductViewModel::class.java]
        populateRecyclerView(this.context)
        populateDropDown(this.context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonAddProduct.setOnClickListener {
            addProductHandler()
        }
    }

    private fun populateRecyclerView(context: Context?) {
        lifecycleScope.launch {
            _recyclerView = _binding.root.findViewById(com.little_rock_farms_pos.R.id.manage_products_recycler_view)
            _recyclerView.layoutManager = LinearLayoutManager(context)

            val data = ArrayList<ProductCardViewModel>()
            val categories = _categoryViewModel.findAll()
                .map { CategoryDTO(it.categoryId!!, it.categoryName, _productViewModel.findByCategoryId(it.categoryId)) }
            categories
                .forEach{ category -> category.products
                    .forEach { product -> data.add(ProductCardViewModel(category.categoryName, product.productName, product.productPrice.toString())) }
                }
            val adapter = ManageProductsCustomAdapter(data)
            _recyclerAdapter = adapter
            _recyclerView.adapter = _recyclerAdapter
        }
    }

    private fun populateDropDown(context: Context?) {
        lifecycleScope.launch {
            val categories = _categoryViewModel.findAll().stream().map { it.categoryName }.collect(Collectors.toList())

            val adapter: ArrayAdapter<String>? = context?.let {
                ArrayAdapter<String>(
                    it, android.R.layout.simple_spinner_item, categories
                )
            }

            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val sItems = _binding.root.findViewById(com.little_rock_farms_pos.R.id.manage_products_category_dropdown) as Spinner
            sItems.adapter = adapter
        }
    }

    private fun addProductHandler() {
        val inputProductName = _binding.editTextProduct.text.toString()
        val inputProductPrice = _binding.editTextProductPrice.text.toString()
        val sItems = _binding.root.findViewById(com.little_rock_farms_pos.R.id.manage_products_category_dropdown) as Spinner
        val categoryName = sItems.selectedItem.toString()
        lifecycleScope.launch {
            try {
                val category = _categoryViewModel.findAll().filter { category -> category.categoryName == categoryName }[0]
                val productMaybe = _productViewModel.findByCategoryId(category.categoryId!!).filter { it.productName == inputProductName }

                val formatter = DecimalFormat("#,###,##0.00#")
                val price = formatter.format(inputProductPrice.toFloat())
                if (productMaybe.isNotEmpty()) {
                    if (productMaybe.size == 1) {
                        val existingProduct = productMaybe[0]
                        existingProduct.productPrice = price.toDouble()
                        _productViewModel.update(existingProduct)
                        val existingCard = _recyclerAdapter.getItems().filter { it.product == inputProductName && it.category == categoryName }[0]
                        existingCard.price = String.format("$ %s", price)
                        _recyclerAdapter.updateItem(existingCard)
                    }
                } else {
                    val newProduct = Product(productName = inputProductName, productPrice = price.toDouble(), productCategoryId = category.categoryId)
                    _productViewModel.insert(newProduct)
                    _recyclerAdapter.addItem(ProductCardViewModel(category=categoryName, product = newProduct.productName,  price = String.format("$ %s", price)))
                }
            } catch (e: Exception) {
                Log.e("manage_products", "Failed to add product", e)
            }
        }
    }
}