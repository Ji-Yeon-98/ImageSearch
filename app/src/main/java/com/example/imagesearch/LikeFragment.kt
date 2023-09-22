package com.example.imagesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.adapter.MyAdapter
import com.example.imagesearch.data.ImageDataModel
import com.example.imagesearch.databinding.FragmentLikeBinding
import com.example.imagesearch.databinding.FragmentSearchBinding

class LikeFragment : Fragment() {


    companion object {
        fun newInstance() = LikeFragment()
    }

    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!

    val likeList = arrayListOf<ImageDataModel>()
    val removeList = arrayListOf<ImageDataModel>()

    private val listAdapter by lazy {
        MyAdapter(
            onClickItem = { position, item ->
                setFragmentResult("removeList", bundleOf("removelist" to item))
                removeTodoItem(position = position, imageDataModel = item)
                likeList.remove(item)
            },)
    }

    private fun removeTodoItem(
        position: Int?,
        imageDataModel: ImageDataModel?
    ) {
        if (position != null) {
            listAdapter.removeItem(
                position,
                imageDataModel
            )
        }
    }

    val itemList = arrayListOf<ImageDataModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {

        recyclerViewLike.adapter = listAdapter
        recyclerViewLike.layoutManager = GridLayoutManager(requireContext(), 2)


        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getParcelableArrayList<ImageDataModel>("bundleKey")

            likeList.clear()

            if (result != null) {
                result.forEach {
                    likeList.add(it)
                }
            }

            listAdapter.ItemsClear()
            listAdapter.addItems(likeList)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}