package com.app.mvvmprojectsetup.ui

import com.app.mvvmprojectsetup.retrofit.Api
import com.app.mvvmprojectsetup.util.NetworkManager
import com.app.mvvmprojectsetup.retrofit.DataHelper
import com.app.mvvmprojectsetup.util.Utils


import javax.inject.Inject

/**
 * @Created by akash on 8/10/2023.
 * Know more about author on <a href="https://akash.cloudemy.in">...</a>
 */
class UserRepo @Inject constructor(
    private val api: Api,
    private val networkManager: NetworkManager
) {

    suspend fun isUserActive(
        req: Long,
        callback: (DataHelper<CommonResponse>) -> Unit
    ) {
        if (!networkManager.isNetworkAvailable()) {
            callback.invoke(DataHelper.Error("No Internet Connection"))
            return
        }
        callback.invoke(DataHelper.Loading())

        val response =   api.isUserActive(req)

        callback.invoke(
            if (response.isSuccessful && response.body()?.status == true) {
                DataHelper.Success(response.body()!!)
            } else if (response.body()?.message != null) {
                DataHelper.Error(response.body()!!.message)
            } else DataHelper.Error(Utils.getError("message",response.errorBody()))
        )
    }

}