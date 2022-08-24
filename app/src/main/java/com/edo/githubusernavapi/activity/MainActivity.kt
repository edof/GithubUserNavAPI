package com.edo.githubusernavapi.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.edo.githubusernavapi.*
import com.edo.githubusernavapi.adapter.RvAdapter
import com.edo.githubusernavapi.databinding.ActivityMainBinding
import com.edo.githubusernavapi.git.GitConfig
import com.edo.githubusernavapi.viewmodel.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RvAdapter
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(Preferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMode().observe(this){
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setSupportActionBar(binding.myToolbar)
        shimmerViewContainer = binding.shimmerViewContainer

        adapter = RvAdapter(arrayListOf(), this@MainActivity)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.adapter = adapter
        adapter.setOnItemClickCallback(object : RvAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitResponse) {
                showClick(data)
            }
        })
        getUsers("edof")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                shimmerViewContainer.visibility = View.VISIBLE
                binding.rvMain.visibility = View.GONE
                shimmerViewContainer.startShimmer()
                getUsers(query)
                searchView.clearFocus()
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.menu_fav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showClick(data: GitResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
    }


    fun getUsers(uname: String) {
        GitConfig.gitService.getGithubUsers(uname)
            .enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    if (response.isSuccessful) {
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        binding.rvMain.visibility = View.VISIBLE
                        val responseBody = response.body()
                        if (responseBody != null) {
                            setData(responseBody.items)
                        }
                    }
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Log.d("Error Get", "" + t.stackTraceToString())
                }
            })
    }

    fun setData(data: List<GitResponse>) {
        if (data.isEmpty()) {
            binding.tvNotfound.visibility = View.VISIBLE
            binding.rvMain.visibility = View.GONE
            Snackbar.make(
                binding.tvNotfound, "Username tidak ditemukan.",
                Snackbar.LENGTH_SHORT
            ).show()
        } else adapter.setData(data)
    }

    public override fun onResume() {
        super.onResume()
        shimmerViewContainer.startShimmer()
    }

    public override fun onPause() {
        shimmerViewContainer.stopShimmer()
        super.onPause()
    }
}