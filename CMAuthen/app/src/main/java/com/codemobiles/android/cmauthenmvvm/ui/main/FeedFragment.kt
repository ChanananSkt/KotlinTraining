package com.codemobiles.android.cmauthenmvvm.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentFeedBinding
import com.codemobiles.android.cmauthenmvvm.databinding.ItemListBinding
import com.codemobiles.android.cmauthenmvvm.model.Youtube
import com.codemobiles.android.cmauthenmvvm.viewmodel.AppViewModelFactory
import com.codemobiles.android.cmauthenmvvm.viewmodel.FeedViewModel
import com.codemobiles.android.cmauthenmvvm.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)

        // Load feed view-model
        val factory = AppViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[FeedViewModel::class.java]

        // Setup recycler view
        binding.mRecyclerView.adapter = FeedRecyclerViewAdapter(listOf<Youtube>())
        binding.mRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // load data
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.feed("foods")
        }

        // observe
        observe()
        return binding.root
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.mDataArray.collect() {
                binding.mRecyclerView.adapter = FeedRecyclerViewAdapter(it)
            }
        }

    }

    inner class FeedRecyclerViewAdapter(val dataArray: List<Youtube>) :
        RecyclerView.Adapter<FeedRecyclerViewAdapter.CustomViewHolder>() {
        inner class CustomViewHolder(val binding: ItemListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {

                binding.itemListAvatar.setOnClickListener{
//                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragmentToDetailActivity2())
                    findNavController().navigate(SuccessFragmentDirections.actionSuccessFragmentToDetailFragment())
                }

                binding.root.setOnClickListener {
                    val position = it.getTag(R.id.item_list_title) as Int
                    val item = dataArray[position]
                    Toast.makeText(requireContext(), "${item.title}", Toast.LENGTH_LONG).show()

                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://www.youtube.com/watch?v=${item.id}")
                        intent.setPackage("com.google.android.youtube")
                        startActivity(intent)

                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Error - Not found YouTube app!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context))
            return CustomViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return dataArray.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val item = dataArray[position]
            holder.binding.itemListTitle.text = item.title
            holder.binding.itemListSubtitle.text = item.subtitle
            holder.binding.root.setTag(R.id.item_list_title, position)

            val DEBUG = false
            if (DEBUG) {
                Glide.with(requireContext())
                    .load("https://s.isanook.com/ca/0/ud/283/1416655/01.jpg")
                    .into(holder.binding.itemListYoutubeImage)

                Glide.with(requireContext())
                    .load("https://cdn-icons-png.flaticon.com/512/4253/4253264.png")
                    .into(holder.binding.itemListAvatar)
            } else {
                Glide.with(requireContext()).load(item.youtube_image)
                    .into(holder.binding.itemListYoutubeImage)

                Glide.with(requireContext()).load(item.avatar_image)
                    .into(holder.binding.itemListAvatar)
            }


        }

    }

}