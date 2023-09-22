package com.example.imagesearch.data

import com.google.gson.annotations.SerializedName

//data class ImageSearch(val response: ImageResponse)

data class ImageResponse(
    //하나의 이미지 검색 결과에 하나의 메타 데이터
    @SerializedName("meta")
    val metadata : MetaData?,
    // 하나의 이미지 검색 결과에 여러 이미지 목록
    @SerializedName("documents")
    val documents: MutableList<Documents>?
)

data class MetaData(
    @SerializedName("total_count")
    val total_count : String,
    @SerializedName("pageable_count")
    val pageable_count: String,
    @SerializedName("is_end")
    val is_end : Boolean
)

data class Documents(
    @SerializedName("display_sitename")
    val sitename : String,
    val collection : String,
    val image_url : String,
    val datetime: String
)

//data class Documents(
//    val collection: String,
//    val thumbnail_url: String,
//    val image_url: String,
//    val width: String,
//    val height: String,
//    val display_sitename: String,
//    val doc_url: String,
//    val datetime: String
//)