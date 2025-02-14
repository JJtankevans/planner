package com.vitoroliveira.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vitoroliveira.planner.R
import com.vitoroliveira.planner.databinding.FragmentUserRegistrationBinding
import com.vitoroliveira.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserRegistrationFragment : Fragment() {
    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    private val userRegistrationViewModel by viewModels<UserRegistrationViewModel>()

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if(uri != null) {
            binding.ivAddPhoto.setImageURI(uri)
            userRegistrationViewModel.updateProfile(
                image = uri.toString()
            )
        } else {
            Toast.makeText(
                requireContext(),
                "Oops... Nenhuma foto selecionada.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            ivAddPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }

            tietName.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    name = text.toString()
                )
            }

            tietEmail.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    email = text.toString()
                )
            }

            tietPhone.addTextChangedListener { text ->
                userRegistrationViewModel.updateProfile(
                    phone = text.toString()
                )
            }

            btnSaveUser.setOnClickListener {
                userRegistrationViewModel.saveIsUserRegistered(isUserRegistered = true)
                navController.navigate(R.id.action_userRegistrationFragment_to_homeFragment)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            userRegistrationViewModel.isProfileValid.collect { isProfileValid ->
                binding.btnSaveUser.isEnabled = isProfileValid
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}