package com.little_rock_farms_pos.main_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.databinding.LrfMainMenuBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainMenu : Fragment() {

    private var _binding: LrfMainMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = LrfMainMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMainMenuToManageInventory.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenu_to_ManageInventory)
        }
        binding.buttonMainMenuToCalculatePurchase.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenu_to_CalculatePurchase)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}