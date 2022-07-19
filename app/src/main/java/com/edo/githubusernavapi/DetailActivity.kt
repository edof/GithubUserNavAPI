package com.edo.githubusernavapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.edo.githubusernavapi.databinding.ActivityDetailBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var user: GitResponse
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    var totFollowers = 0
    var totFollowing = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)
        user = intent.getSerializableExtra(EXTRA_USER) as GitResponse
        val uname = user.login
        supportActionBar?.apply {
            title = "@$uname"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        shimmerViewContainer = binding.shimmerViewContainer
        getDetail(uname)
    }

    private fun getDetail(uname: String) {
        GitConfig.gitService.getDetailGitUsers(uname)
            .enqueue(object : Callback<DetailUsers> {
                override fun onResponse(call: Call<DetailUsers>, response: Response<DetailUsers>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        sendData(responseBody)
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        with(binding) {
                            avaDetail.visibility = View.VISIBLE
                            unameDetail.visibility = View.VISIBLE
                            nameDetail.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                    Log.e("tag", "errornya ${t.message}")
                }

            })
    }

    private fun sendData(users: DetailUsers) {
        totFollowers = users.followers
        totFollowing = users.following
        val uname = user.login
        viewPagerAdapter = ViewPagerAdapter(this, uname)

        with(binding) {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Followers (${totFollowers})"
                    1 -> tab.text = "Following (${totFollowing})"
                }
            }.attach()
//            Toast.makeText(applicationContext, totFollowers.toString(), Toast.LENGTH_SHORT).show()
            unameDetail.text = "@${user.login}"
            nameDetail.text = users.name
            Glide.with(applicationContext)
                .load(user.avatarUrl)
                .circleCrop()
                .into(avaDetail)
        }
    }

    public override fun onResume() {
        super.onResume()
        shimmerViewContainer.startShimmer()
    }

    public override fun onPause() {
        shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}