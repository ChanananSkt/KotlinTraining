package com.codemobiles.android.cmauthenmvvm.model

data class YoutubeBean (
    val youtubes: List<Youtube>,
    val error: Boolean,
    val errorMsg: String
)

data class Youtube (
    val id: String,
    val title: String,
    val subtitle: String,
    val avatar_image: String,
    val youtube_image: String
)
