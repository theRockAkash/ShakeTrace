package com.app.mvvmprojectsetup.retrofit

import android.content.Context
import android.util.Log.VERBOSE
import com.app.mvvmprojectsetup.BuildConfig
import com.creatorstool.shaketrace.logger.Level
import com.creatorstool.shaketrace.logger.PrettyLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofitApi(interceptor: Interceptor, @ApplicationContext context: Context): Api {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val prettyInterceptor = PrettyLoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .setCashDir(context.cacheDir)
                .log(VERBOSE)
            httpClient.addNetworkInterceptor(prettyInterceptor.build())

        }
        return Retrofit.Builder()
            .baseUrl("https://github.com/theRockAkash/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build().create(Api::class.java)
    }


}