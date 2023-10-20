package com.app.mvvmprojectsetup.retrofit

import com.app.mvvmprojectsetup.ui.CommonResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */
interface Api {


    @POST("user")
    suspend fun isUserActive(
        @Body body: Long
    ): Response<CommonResponse>


}