package com.example.clonespotify.ui.viewmodels

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonespotify.entities.Song
import com.example.clonespotify.expoplayer.MusicServiceConnector

import com.example.clonespotify.expoplayer.callbacks.isPlayEnabled
import com.example.clonespotify.expoplayer.callbacks.isPlaying
import com.example.clonespotify.expoplayer.callbacks.isPrepared
import com.example.clonespotify.others.Constants.MEDIA_ROOT_ID
import com.example.clonespotify.others.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicServiceConnector: MusicServiceConnector
) : ViewModel() {
    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = musicServiceConnector.isConnected
    val networkError = musicServiceConnector.networkError
    val curPlayingSong = musicServiceConnector.curPlayingSong
    val playbackState = musicServiceConnector.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnector.subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.map {
                    Song(
                        it.mediaId!!,
                        it.description.title.toString(),
                        it.description.subtitle.toString(),
                        it.description.mediaUri.toString(),
                        it.description.iconUri.toString()
                    )
                }
                _mediaItems.postValue(Resource.success(items))
            }
        })
    }

    fun skipToNextSong() {
        musicServiceConnector.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnector.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnector.transportControls.seekTo(pos)
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if(isPrepared && mediaItem.mediaId ==
            curPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if(toggle) musicServiceConnector.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnector.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnector.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnector.unsubscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}

















