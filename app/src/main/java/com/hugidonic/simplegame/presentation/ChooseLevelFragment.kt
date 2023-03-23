package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.databinding.FragmentChooseLevelBinding
import com.hugidonic.simplegame.domain.entity.Level

class ChooseLevelFragment: Fragment() {

	private var _binding: FragmentChooseLevelBinding? = null
	private val binding: FragmentChooseLevelBinding
		get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		with(binding) {
			btnLevelTest.setOnClickListener { launchGameFragment(Level.TEST) }
			btnLevelEasy.setOnClickListener { launchGameFragment(Level.EASY) }
			btnLevelNormal.setOnClickListener { launchGameFragment(Level.NORMAL) }
			btnLevelHard.setOnClickListener { launchGameFragment(Level.HARD) }
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun launchGameFragment(level: Level) {
		requireActivity().supportFragmentManager
			.beginTransaction()
			.replace(R.id.main_container, GameFragment.newInstance(level))
			.addToBackStack(GameFragment.TAG)
			.commit()

	}

	companion object {
		const val TAG = "ChooseLevelFragment"

		@JvmStatic
		fun newInstance(): Fragment {
			return ChooseLevelFragment()
		}
	}
}