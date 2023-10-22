package com.little_rock_farms_pos.manage_categories

import ManageCategoriesCustomAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.little_rock_farms_pos.databinding.LrfManageCategoriesBinding
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.models.CategoryViewModel
import kotlinx.coroutines.launch
import java.util.stream.Collectors


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ManageCategories : Fragment() {

    private lateinit var _binding: LrfManageCategoriesBinding
    private lateinit var _categoryViewModel: CategoryViewModel
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _recyclerAdapter: ManageCategoriesCustomAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LrfManageCategoriesBinding.inflate(inflater, container, false)
        _categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        populateRecyclerView(this.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonAddCategory.setOnClickListener {
            addCategoryHandler()
        }
    }

    private fun populateRecyclerView(context: Context?) {
        lifecycleScope.launch {
            _recyclerView = _binding.root.findViewById(com.little_rock_farms_pos.R.id.manage_categories_recycler_view)
            _recyclerView.layoutManager = LinearLayoutManager(context)

            val data = _categoryViewModel.findAll().stream().map {
                ManageCategoriesViewModel(it.categoryName)
            }.collect(Collectors.toList())
            val adapter = ManageCategoriesCustomAdapter(data)
            _recyclerAdapter = adapter
            _recyclerView.adapter = _recyclerAdapter
        }
    }

    private fun addCategoryHandler() {
        val input = _binding.editTextCategory.text.toString()

        lifecycleScope.launch {
            val categories = _categoryViewModel.findAll().filter { it.categoryName == input }

            if (categories.isEmpty()) {
                val newCategory = Category(categoryName =input)
                _categoryViewModel.insert(newCategory)
                _recyclerAdapter.addItem(ManageCategoriesViewModel(input))
            }
        }
    }
}