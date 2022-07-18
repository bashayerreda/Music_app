package com.example.clonespotify.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonespotify.R
import com.example.clonespotify.adapters.SongAdapter
import com.example.clonespotify.others.Status
import com.example.clonespotify.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var songAdapter: SongAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subscribeToObservers()
        songAdapter.setItemClickListener {
            mainViewModel.playOrToggleSong(it)
        }
    }

    private fun setupRecyclerView() = rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when(result.status) {
                Status.SUCCESS -> {
                    allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.songs = songs
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> allSongsProgressBar.isVisible = true
            }
        }
    }
    /*  fun swipeItem() {
      val mIth = ItemTouchHelper(
          object : ItemTouchHelper.SimpleCallback(
              0 or ItemTouchHelper.RIGHT,
              ItemTouchHelper.RIGHT
          ) {
              override fun onMove(
                  recyclerView: RecyclerView,
                  viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
              ): Boolean {
                  val fromPos = viewHolder.adapterPosition
                  val toPos = target.adapterPosition
                  // move item in `fromPos` to `toPos` in adapter.
                  return true // true if moved, false otherwise
              }
              override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  var swipedMemePosition: Int = viewHolder.adapterPosition
                  //second step we enter in adapter and search position inside list to know what is the meme of this position now we have meme finally we will add it in db
                  val memesPosition: Song = songAdapter.getmemesPosition(swipedMemePosition)
                  val memes = Song()
                  lifecycleScope.launch {
                      mainViewModel.playOrToggleSong(memesPosition)
                      songAdapter.notifyItemChanged(swipedMemePosition)
                  }
              }
          })
      mIth.attachToRecyclerView(rvAllSongs)
  }*/

}




