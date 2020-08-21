package com.example.exoplayerdemo

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_layout.*

class MainActivity : AppCompatActivity() {

    lateinit var simpleExoPlayer: SimpleExoPlayer

    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Make activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Video url
        val uri = Uri.parse("https://i.imgur.com/7bMqysJ.mp4")

        //Initialize load control
        val loadControl = DefaultLoadControl()

        //Initialize band width meter
        val bandwidthMeter = DefaultBandwidthMeter()

        //Initialize track selector
        val trackSelector = DefaultTrackSelector(
            AdaptiveTrackSelection.Factory(
                bandwidthMeter
            )
        )

        //Initialize simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector,loadControl)

        //Initialize data source factory
        val factory = DefaultHttpDataSourceFactory("exoplayer_video")

        //Initialize extractors factory
        val extractorFactory = DefaultExtractorsFactory()

        //Initialize media source
        val mediaSource = ExtractorMediaSource(uri,factory,extractorFactory,null,null)

        //set player
        player_view.setPlayer(simpleExoPlayer)
        //keep screen on
        player_view.setKeepScreenOn(true)
        //prepare media
        simpleExoPlayer.prepare(mediaSource)

        //play video when ready
        simpleExoPlayer.setPlayWhenReady(true)

        simpleExoPlayer.addListener(object : Player.EventListener{
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

//            override fun onSeekProcessed() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }

//            override fun onTracksChanged(
//                trackGroups: TrackGroupArray?,
//                trackSelections: TrackSelectionArray?
//            ) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                if (isLoading){
                    progress_bar.visibility = View.VISIBLE
                }
                else{
                    progress_bar.visibility = View.GONE
                }
            }

//            override fun onPositionDiscontinuity(reason: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

//            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING){
                    progress_bar.visibility = View.VISIBLE
                }
                else if(playbackState == Player.STATE_READY){
                    progress_bar.visibility = View.GONE
                }


            }

        })

        bt_fullscreen.setOnClickListener {
            if (flag){
                //when flag is true
                //it means we are not having full screen
                bt_fullscreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen))
                //set potrait orientation
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            }
            else{
                bt_fullscreen.setImageDrawable(resources.getDrawable(R.drawable.ic_fullscreen_exit))
                //set potrait orientation
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.playWhenReady = false

        simpleExoPlayer.playbackState
    }

    override fun onRestart() {
        super.onRestart()

        simpleExoPlayer.playWhenReady = true

        simpleExoPlayer.playbackState
    }

}
