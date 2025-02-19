package com.vitoroliveira.planner.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.vitoroliveira.planner.R
import com.vitoroliveira.planner.data.utils.imageBase64ToBitMap
import com.vitoroliveira.planner.databinding.FragmentHomeBinding
import com.vitoroliveira.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val userRegistrationViewModel by activityViewModels<UserRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            btnSaveNewPlannerActivity.setOnClickListener {
                UpdatePlannerActivityDialogFragment().show(
                    childFragmentManager,
                    UpdatePlannerActivityDialogFragment.TAG
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            launch {
                userRegistrationViewModel.profile.collect { profile ->
                    binding.tvUserName.text = getString(R.string.ola_usuario, profile.name)
                    imageBase64ToBitMap(base64String = profile.image)?.let { imageBitmap ->
                        binding.ivUserPhoto.setImageBitmap(imageBitmap)
                    }
                }
            }
            launch {
                userRegistrationViewModel.isTokenValid.distinctUntilChanged { old, new ->
                    old == new
                }.collect { isTokenValid ->
                    Log.d("CheckIsTokenValid", "setupObservers: isTokenValid = $isTokenValid")
                    if (isTokenValid == false) showNewTokenSnackBar()
                }
            }
        }
    }

    private fun showNewTokenSnackBar() {
        Snackbar.make(
            requireView(),
            "Oopss... O seu token expirou.",
            Snackbar.LENGTH_INDEFINITE
        ).setAction(
            "ÖBTER NOVO TOKEN"
        ) {
            userRegistrationViewModel.obtainNewToken()
        }
            .setActionTextColor(
                ContextCompat.getColor(requireContext(), R.color.lime_300)
            ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}