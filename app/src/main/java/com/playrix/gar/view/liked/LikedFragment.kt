package com.playrix.gar.view.liked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.playrix.gar.R
import com.playrix.gar.data.constants.lookup_items.RemoveMenuPojo.chosenPojo
import com.playrix.gar.data.room.PhotosDatabase.Companion.getDatabaseInstance
import com.playrix.gar.databinding.FragmentLikedBinding
import com.playrix.gar.view.adapters_rv.LikedAdapter
import com.playrix.gar.viewmodels.LikedViewModel
import com.playrix.gar.some_services.services.downloader.DownloadingManager
import com.playrix.gar.some_services.services.lookuper.LookupImageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LikedFragment : Fragment() {

    @Inject
    lateinit var likedAdapter: LikedAdapter

    @Inject
    lateinit var wallpaperManager: LookupImageManager

    @Inject
    lateinit var downloadingManager: DownloadingManager

    private val viewModel: LikedViewModel by viewModels()

    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLikedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setSharedFlowObservers()
        viewModel.extractLiked(getDatabaseInstance(requireContext()))

        registerForContextMenu(binding.rvLiked)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.remove_like -> {
            viewModel.removeImage(getDatabaseInstance(requireContext()))
            true
        }

        R.id.set_as_main_menu -> {
            wallpaperManager.setMainFromBitmap(requireContext())
            true
        }

        R.id.download_menu -> {
            downloadingManager.saveLikedBitmap(chosenPojo?.photoBitmap)
            true
        }

        else -> false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRv() {
        binding.rvLiked.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = likedAdapter
        }
    }

    private fun setSharedFlowObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.likedPhotosFlow.collectLatest {
                    if (it.isNotEmpty()) {
                        manageErrorTextVisibility(false)
                        likedAdapter.submitList(it)
                    } else {
                        manageErrorTextVisibility(true)
                    }
                }
            }
        }
    }

    private fun manageErrorTextVisibility(makeVisible: Boolean) {
        binding.textLiked.visibility = when (makeVisible) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }
}