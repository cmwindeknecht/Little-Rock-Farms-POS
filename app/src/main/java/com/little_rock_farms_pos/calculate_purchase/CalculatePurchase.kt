package com.little_rock_farms_pos.calculate_purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.little_rock_farms_pos.R
import com.little_rock_farms_pos.databinding.FragmentFirstBinding
import com.little_rock_farms_pos.databinding.LrfCalculatePurchaseBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CalculatePurchase : Fragment() {

    private var _binding: LrfCalculatePurchaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = LrfCalculatePurchaseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCalculatePurchaseToMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_CalculatePurchase_to_MainMenu)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}