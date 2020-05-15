package com.sakb.spl.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by dev.mahmoud_ashraf on 10/3/2019.
 */

data class MovieResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null

)