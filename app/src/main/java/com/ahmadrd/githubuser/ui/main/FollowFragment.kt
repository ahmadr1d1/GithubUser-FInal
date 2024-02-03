package com.ahmadrd.githubuser.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.ahmadrd.githubuser.ui.adapter.GetUserAdapter
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.FragmentFollowBinding
import com.ahmadrd.githubuser.ui.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvUser: RecyclerView


    companion object {
        const val USER_POSITION = "Position"
        const val USERNAME = "Username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val position = arguments?.getInt(USER_POSITION, 0)
        val username = arguments?.getString(USERNAME)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.error.observe(requireActivity()) {
            if (it) {
                Toast.makeText(requireContext(), "Failed to load API", Toast.LENGTH_LONG).show()
            }
            detailViewModel.toastError()
        }

        if (position == 1) {
            detailViewModel.getFollowers(username.toString())
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setData(it)
            }
        } else {
            detailViewModel.getFollowing(username.toString())
                detailViewModel.following.observe(viewLifecycleOwner) {
                    if (it != null) {
                        setData(it)
                    }
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