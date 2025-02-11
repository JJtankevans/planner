package com.vitoroliveira.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vitoroliveira.planner.R
import com.vitoroliveira.planner.databinding.FragmentInitialLoadingBinding

class InitialLoadingFragment : Fragment() {
    private var _binding: FragmentInitialLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInitialLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //TODO: tela de loading incial
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}