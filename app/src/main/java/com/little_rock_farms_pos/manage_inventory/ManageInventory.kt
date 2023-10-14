package com.little_rock_farms_pos.manage_inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.databinding.LrfManageInventoryBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ManageInventory : Fragment() {

    private var _binding: LrfManageInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = LrfManageInventoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonManageInventoryToManageCategories.setOnClickListener {
            findNavController().navigate(R.id.action_ManageInventory_to_ManageCategories)
        }
        binding.buttonManageInventoryToManageProducts.setOnClickListener {
            findNavController().navigate(R.id.action_ManageInventory_to_ManageProducts)
        }
        binding.buttonManageInventoryToManagePrices.setOnClickListener {
            findNavController().navigate(R.id.action_ManageInventory_to_ManagePrices)
        }
        binding.buttonManageInventoryToMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_ManageInventory_to_MainMenu)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}