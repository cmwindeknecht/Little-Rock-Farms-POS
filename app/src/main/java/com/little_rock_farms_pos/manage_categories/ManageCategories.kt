package com.little_rock_farms_pos.manage_categories

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.little_rock_farms_pos.databinding.LrfManageCategoriesBinding
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.models.CategoryViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ManageCategories : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var _binding: LrfManageCategoriesBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = LrfManageCategoriesBinding.inflate(inflater, container, false)
        categoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonAddCategory.setOnClickListener {
            addCategoryHandler()
        }
    }

    private fun addCategoryHandler() {
        val input = _binding.editTextCategory.text.toString()

        lifecycleScope.launch {
            categoryViewModel.insert(Category(categoryName =input))

            val categories = categoryViewModel.findAll()
            Log.d("manage_category", "categories = $categories")
        }
    }
}