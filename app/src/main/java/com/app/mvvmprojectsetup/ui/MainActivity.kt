package com.app.mvvmprojectsetup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.app.mvvmprojectsetup.R

import com.therockakash.shaketrace.ShakeTrace
import com.app.mvvmprojectsetup.retrofit.DataHelper
import com.app.mvvmprojectsetup.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =ViewModelProvider(this)[MainViewModel::class.java]

        ShakeTrace.clearLogs()
        viewModel.isUserActive(1)
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.commonResponseLiveData.observe(this){
            when(it){
                is DataHelper.Error -> {
                    Utils.toasty(this,it.msg)
                }
                is DataHelper.Loading -> {

                }
                is DataHelper.Success -> {

                }
            }
        }
    }
}