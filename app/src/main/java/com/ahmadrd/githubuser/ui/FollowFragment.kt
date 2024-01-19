package com.ahmadrd.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.githubuser.adapter.GetUserAdapter
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.FragmentFollowBinding
import com.ahmadrd.githubuser.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvUser: RecyclerView
//    private lateinit var viewModel: DetailViewModel
//    private val viewModel by viewModels<DetailViewModel>()


    companion object {
        const val TAG = "FollowFragments"
        const val USER_POSITION = "position"
        const val USERNAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

//        binding.rvFoll.layoutManager = LinearLayoutManager(requireActivity())

        var position = arguments?.getInt(USER_POSITION, 0)
        var username = arguments?.getString(USERNAME)
        Log.d("arguments: position", position.toString())
        Log.d("arguments: username", username.toString())

//        arguments?.let {
//            position = it.getInt(USER_POSITION, 0)
//            username = it.getString(USERNAME)
//        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.error.observe(requireActivity()) {
            if (it) {
                Toast.makeText(requireContext(), "Failed to load API", Toast.LENGTH_LONG).show()
            }
            detailViewModel.doneToastError()
        }

        if (position == 1) {
            detailViewModel.getFollowers(username.toString())
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setData(it)
            }
        } else {
            detailViewModel.getFollowing(username.toString())
                detailViewModel.following.observe(viewLifecycleOwner) {
                    setData(it)
                }
            }

        rvUser = binding.rvFoll
        rvUser.setHasFixedSize(true)
        showRecyclerList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setData(listUser: List<ItemsItem>) {
        val adapter = GetUserAdapter()
        adapter.submitList(listUser)
        binding.rvFoll.adapter = adapter
    }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(requireActivity())
        val listUserAdapter = GetUserAdapter()
        rvUser.adapter = listUserAdapter
    }
}