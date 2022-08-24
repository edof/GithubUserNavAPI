package com.edo.githubusernavapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edo.githubusernavapi.DBModule
import com.edo.githubusernavapi.viewmodel.FavoriteViewModel
import com.edo.githubusernavapi.GitResponse
import com.edo.githubusernavapi.adapter.RvAdapter
import com.edo.githubusernavapi.databinding.ActivityFavoriteBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private lateinit var adapter: RvAdapter
    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DBModule(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmerViewContainer = binding.shimmerViewContainer
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.apply {
            title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = RvAdapter(arrayListOf(), this@FavoriteActivity)
        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.setHasFixedSize(true)
        binding.rvFav.adapter = adapter
        adapter.setOnItemClickCallback(object : RvAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitResponse) {
                showClick(data)
            }
        })
        viewModel.getFavUsers().observe(this){
            if (it.isEmpty()){
                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.visibility = View.GONE
                binding.tvNotfound.visibility = View.VISIBLE
                binding.rvFav.visibility = View.GONE
                Snackbar.make(
                    binding.tvNotfound, "Username tidak ditemukan.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.visibility = View.GONE
                binding.rvFav.visibility = View.VISIBLE
                adapter.setData(it)
            }
        }
    }

    private fun showClick(data: GitResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
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