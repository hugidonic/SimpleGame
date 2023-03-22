package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hugidonic.simplegame.databinding.FragmentWelcomeBinding

class WelcomeFragment: Fragment() {

	private var _binding: FragmentWelcomeBinding? = null
	private val binding: FragmentWelcomeBinding
		get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentWelcomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
} 