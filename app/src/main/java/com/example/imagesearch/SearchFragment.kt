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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.adapter.MyAdapter
import com.example.imagesearch.data.ImageDataModel
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.network.NetWorkClient
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val itemList = arrayListOf<ImageDataModel>()
    val sendList = arrayListOf<ImageDataModel>()

    private val listAdapter by lazy {
        MyAdapter(
            onClickItem = { position, item ->
                modifyTodoItem(position, item)
        },)
    }

    private fun modifyTodoItem(
        position: Int?,
        imageDataModel: ImageDataModel?
    ) {

        if (imageDataModel != null) {
            imageDataModel.heart = !imageDataModel.heart

            if(imageDataModel.heart){
                if(!sendList.contains(imageDataModel)){
                    sendList.add(imageDataModel)
                }
            }else{
                sendList.remove(imageDataModel)
            }
        }

        listAdapter.modifyItem(
            position,
            imageDataModel
        )

        Log.d("sendList", sendList.toString())
        setFragmentResult("requestKey", bundleOf("bundleKey" to sendList))

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setText(SharedPreference.loadData(requireContext()))

        initView()

    }

    private fun initView() = with(binding) {

        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        btnSearch.setOnClickListener {

            itemList.clear()
            listAdapter.ItemsClear()
            communicateNetWork(etSearch.text.toString())
            SharedPreference.saveData(requireContext(), etSearch.text.toString())
        }

        setFragmentResultListener("removeList") { requestKey, bundle ->
            val result = bundle.getParcelable<ImageDataModel>("removelist")

            if (result != null) {
                modifyTodoItem(itemList.indexOf(result), result)
            }

        }

    }

    // http 통신을 하기 때문에 MainThread에서 못 동작 : 메인 스레드에서 통신한다면 딜레이 발생, 화면 멈춰있음
    private fun communicateNetWork(text: String) = lifecycleScope.launch() {

        val responseData = NetWorkClient.ImageNetWork.searchImage(
            "KakaoAK 7e502df8f12ed67f6258c373ebc968b3",
            text,
            "accuracy",
            1,
            80
        )

        for( i in 0 until responseData.body()?.documents!!.size){
            itemList.add(ImageDataModel(responseData.body()?.documents!![i].image_url, responseData.body()?.documents!![i].sitename, responseData.body()?.documents!![i].datetime, false))
        }

        listAdapter.addItems(itemList)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}