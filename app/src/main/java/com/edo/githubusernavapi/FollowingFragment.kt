package com.edo.githubusernavapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.edo.githubusernavapi.adapter.RvFollowingAdapter
import com.edo.githubusernavapi.databinding.FragmentFollowingBinding
import com.edo.githubusernavapi.git.GitConfig
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var _binding: FragmentFollowingBinding? = null
private val binding get() = _binding!!
private lateinit var adapter: RvFollowingAdapter
private lateinit var shimmerViewContainer: ShimmerFrameLayout

class FollowingFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uname = arguments?.getString("UNAME")
//        _binding?.username?.text = uname.toString()
        shimmerViewContainer = binding.shimmerViewContainer

        adapter = RvFollowingAdapter(arrayListOf(), view.context)
        binding.rvMain.layoutManager = LinearLayoutManager(context)
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.adapter = adapter

        getFollowers(uname.toString())
    }

    private fun getFollowers(uname: String) {
        GitConfig.gitService.getDetailFollowing(uname)
            .enqueue(object : Callback<List<DetailFollowing>> {
                override fun onResponse(
                    call: Call<List<DetailFollowing>>,
                    response: Response<List<DetailFollowing>>
                ) {
                    if (response.isSuccessful) {
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        binding.rvMain.visibility = View.VISIBLE
                        val responseBody = response.body()
//                        Log.d("abcd ", responseBody.toString())
                        if (responseBody != null) {
                            setData(responseBody as MutableList<DetailFollowing>)
                        }
                    }
                }

                override fun onFailure(call: Call<List<DetailFollowing>>, t: Throwable) {
                    Log.d("Error Get", "" + t.stackTraceToString())
                }
            })
    }

    private fun setData(data: MutableList<DetailFollowing>) {
        if (data.isEmpty()) {
            binding.tvNotfound.visibility = View.VISIBLE
            binding.rvMain.visibility = View.GONE
            Snackbar.make(
                binding.tvNotfound, "Tidak mengikuti siapapun.",
                Snackbar.LENGTH_SHORT
            ).show()
        } else adapter.setData(data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shimmerViewContainer.stopShimmer()
        _binding = null
    }
}