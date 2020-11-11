package com.coding.zxm.wanandroid.gallery

import com.coding.zxm.network.APIConstants
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.gallery.model.BingResponse
import me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/11 . All rights reserved.
 */
interface BingService {

    /**
     * 获取Bing壁纸图片
     */
    @Headers(DOMAIN_NAME_HEADER + APIConstants.DOMAN_BING)
    @GET("/HPImageArchive.aspx?format=js&idx={pageIndex}}&n={pageSize}")
    suspend fun getBingPicList(
        @Path("pageIndex") pageIndex: Int,
        @Path("pageSize") pageSize: Int
    ): BingResponse<MutableList<BingImageEntity>>
}