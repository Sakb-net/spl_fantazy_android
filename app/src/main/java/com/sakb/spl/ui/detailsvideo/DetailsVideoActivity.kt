package com.sakb.spl.ui.detailsvideo

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.constants.Constants
import com.sakb.spl.databinding.ActivityDetailsVideoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class DetailsVideoActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsVideoBinding
    private lateinit var dialog: Dialog

    private var onInitializedListener: YouTubePlayer.OnInitializedListener? = null


    private var mYoutubePlayer: YouTubePlayer? = null
    private var frag: YouTubePlayerSupportFragment? = null
    private var upload_id: String? = ""
    private var titleTv: String? = ""
    private var createdAt: String? = ""
    private var desc: String? = ""
    private var link: String? = ""
    private var video: String? = ""
    override val viewModel by viewModel<DetailsVideosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_video)
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        upload_id = intent.getStringExtra("upload_id")
        titleTv = intent.getStringExtra("title")
        createdAt = intent.getStringExtra("created_at")
        desc = intent.getStringExtra("desc")
        link = intent.getStringExtra("link")
        video = intent.getStringExtra("video")
        frag =
            supportFragmentManager.findFragmentById(R.id.player_fragment) as YouTubePlayerSupportFragment


        if (upload_id == "") {

            binding.yVideoll.visibility = View.INVISIBLE
            binding.jzvdVideoll.visibility = View.VISIBLE

            binding.jzVideo.setUp(Constants.baseUrl + video, "", JzvdStd.SCREEN_NORMAL)

            Timber.e("img==> " + Constants.baseUrl + video)
            Glide.with(binding.jzVideo.posterImageView)
                .load(Constants.baseUrl + video)
                // .centerCrop()
                .into(binding.jzVideo.posterImageView)

        } else {

            binding.jzvdVideoll.visibility = View.INVISIBLE
            binding.yVideoll.visibility = View.VISIBLE

            onInitializedListener = object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    youTubePlayer: YouTubePlayer?,
                    wasRestored: Boolean
                ) {

                    youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
                    youTubePlayer?.setPlaybackEventListener(playbackEventListener)
                    if (!wasRestored) {
                        youTubePlayer?.cueVideo(upload_id)
                    }
                    mYoutubePlayer = youTubePlayer

                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    ///Timber.e("////////////////"+p0?.toString())
                    /// Timber.e("////////////////"+p1?.toString())


                    /// context?.toast(""+p1?.name)
                }
            }
            /////////////
            frag?.initialize(getString(R.string.GOOGLE_API_KEY), onInitializedListener)

        }
        title = "Video"
        binding.toolbar.title = ""
        binding.titleTv.text = titleTv
        binding.createdAtTv.text = createdAt
        binding.descriptionTv.text = desc

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()


        //  youtube_player.initialize(getString(R.string.GOOGLE_API_KEY), this)
//        binding.addBtn.setOnClickListener {
//            startActivityForResult(Intent(context,AddCommentActivity::class.java)
//                .putExtra("link",link)
//                .putExtra("type","video")
//                ,
//                700)
//        }


        //  getObservableData()

        /*  binding.shareBtn.setOnClickListener {
              val intent = Intent(Intent.ACTION_SEND)
              intent.type = "text/plain"
              intent.putExtra(Intent.EXTRA_SUBJECT, ""+getString(R.string.app_name))
              intent.putExtra(Intent.EXTRA_TEXT, ""+binding.titleTv.text)
              startActivity(Intent.createChooser(intent, "choose one"))
          }*/
        /*  val video : videosResponse.Data? = intent.getParcelableExtra("obj")

          binding.titleTv.text = video?.name
          binding.createdAtTv.text = video?.createdAt
          binding.descriptionTv.text = video?.content*/

    }

    private fun changeViewsFonts() {
//        Util.changeViewTypeFace(this@DetailsVideoActivity,
//            Constants.FONT_REGULAR,
//            binding.toolbarTitle)

    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onPlaying() {

        }

        override fun onPaused() {

        }

        override fun onStopped() {

        }

        override fun onBuffering(b: Boolean) {

        }

        override fun onSeekTo(i: Int) {

        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onLoading() {

        }

        override fun onLoaded(s: String) {

        }

        override fun onAdStarted() {

        }

        override fun onVideoStarted() {

        }

        override fun onVideoEnded() {

        }

        override fun onError(errorReason: YouTubePlayer.ErrorReason) {

        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.youtube_exit_animation)
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        onInitializedListener = null
    }
}
