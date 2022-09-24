package com.github.api.models.response


import com.github.api.models.entities.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserResponse(
    @Json(name = "incomplete_results")
    var incompleteResults: Boolean?,
    @Json(name = "items")
    var items: List<Item>?,
    @Json(name = "total_count")
    var totalCount: Int?
)