package com.playrix.gar.view.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.playrix.gar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val backManager = object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            android.os.Process.myPid().killApplication()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBackPresser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addBackPresser() {
        requireActivity().onBackPressedDispatcher.addCallback(backManager)
    }

    private fun Int.killApplication() {
        requireActivity().finishAffinity()
        android.os.Process.killProcess(this)
    }
}