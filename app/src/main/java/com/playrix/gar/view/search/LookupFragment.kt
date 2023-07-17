package com.playrix.gar.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.playrix.gar.convertImageUrlToBitmap
import com.playrix.gar.R
import com.playrix.gar.data.constants.lookup_items.ImageUrlObject.imageUrl
import com.playrix.gar.data.room.PhotosDatabase.Companion.getDatabaseInstance
import com.playrix.gar.databinding.FragmentLookupBinding
import com.playrix.gar.view.adapters_rv.ImageLookupAdapter
import com.playrix.gar.view.toast.ToastPusher
import com.playrix.gar.some_services.services.downloader.DownloadingManager
import com.playrix.gar.viewmodels.LookupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LookupFragment : Fragment() {
    private val viewModel: LookupViewModel by viewModels()

    private var _binding: FragmentLookupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLookupAdapter: ImageLookupAdapter

    @Inject
    lateinit var toastPusher: ToastPusher

    @Inject
    lateinit var downloadingManager: DownloadingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLookupBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeBaseInit()
        registerForContextMenu(binding.wallpaperRecyclerView)

        observeLiveDataResponse()
    }

    private fun makeBaseInit() {
        setupRv()
        addTextInputListener()
    }

    private fun setupRv() {
        binding.wallpaperRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = imageLookupAdapter
        }
    }

    private fun observeLiveDataResponse() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lookupFlow.collectLatest {
                    if (it != null) {
                        imageLookupAdapter.submitList(it.photos_response)
                        binding.textSearch.changeViewVisibility(false)
                        if (it.photos_response.isEmpty()) {
                            binding.textSearch.changeViewVisibility()
                        }
                    } else {
                        binding.textSearch.changeViewVisibility()
                    }
                }
            }
        }
    }

    private fun addTextInputListener() {
        binding.userQueryString.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.getApiWallpaper(binding.userQueryString.text.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {}
        })
    }

    override fun onContextItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.like -> {
            onLikeClicked()

            true
        }

        R.id.save_locally -> {
            onSaveClicked()

            true
        }
        else -> false
    }

    private fun onSaveClicked() {
        val url = imageUrl

        if (url != null) {
            downloadingManager.downloadImageFromUrl(requireContext(), url)
        }
    }

    private fun onLikeClicked() {
        toastPusher.pushCustomTextToast("Liked")

        lifecycleScope.launch {
            viewModel.apply {
                requireContext().convertImageUrlToBitmap()
                    .addToLiked(getDatabaseInstance(requireContext().applicationContext))
            }
        }
    }

    private fun View.changeViewVisibility(isVisible: Boolean = true) {
        visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.INVISIBLE
        }
    }
}