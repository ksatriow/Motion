package com.satrio.motion.ui.videoplayer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.satrio.motion.databinding.VideoDialogBinding


class VideoPlayerDisplay(videoId: String) : AppCompatDialogFragment() {
    private var binding: VideoDialogBinding? = null
    private val videoId: String

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = VideoDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding!!.getRoot())
        lifecycle.addObserver(binding!!.youtubePlayer)
        binding!!.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Toast.makeText(context, "Some error occurred!", Toast.LENGTH_SHORT).show()
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding!!.youtubePlayer.release()
    }

    init {
        this.videoId = videoId
    }
}
