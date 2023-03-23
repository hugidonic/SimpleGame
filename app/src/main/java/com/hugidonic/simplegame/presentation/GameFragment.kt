package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.databinding.FragmentGameBinding
import com.hugidonic.simplegame.domain.entity.GameResult
import com.hugidonic.simplegame.domain.entity.GameSettings
import com.hugidonic.simplegame.domain.entity.Level

class GameFragment: Fragment() {
	private lateinit var level: Level
	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseArgs()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.tvSum.setOnClickListener {
			launchGameFinishedFragment(
				GameResult(
					false,
					0,0,
					GameSettings(0,0,0,0)
				)
			)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun parseArgs() {
		@Suppress("DEPRECATION")
		requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
			level = it
		}
	}

	private fun launchGameFinishedFragment(gameResult: GameResult) {
		requireActivity().supportFragmentManager
			.beginTransaction()
			.replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
			.addToBackStack(GameFinishedFragment.TAG)
			.commit()
	}

	companion object {
		private const val KEY_LEVEL = "level"
		const val TAG = "GameFragment"

		@JvmStatic
		fun newInstance(level: Level): Fragment {
			return GameFragment().apply {
				arguments = Bundle().apply {
					putSerializable(KEY_LEVEL, level)
				}
			}
		}
	}
}