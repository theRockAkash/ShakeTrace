package com.app.mvvmprojectsetup.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.therockakash.shaketrace.utils.DataHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Created by akash on 10/20/2023.
 * Know more about author on <a href="https://akash.cloudemy.in">...</a>
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: UserRepo) : ViewModel() {

    private val _commonResponseLiveData by lazy { MutableLiveData<DataHelper<CommonResponse>>() }
    val commonResponseLiveData: LiveData<DataHelper<CommonResponse>> get() = _commonResponseLiveData

    fun isUserActive(req: Long) {
        viewModelScope.launch {
            repo.isUserActive(req) {
                _commonResponseLiveData.postValue(it)
            }
        }
    }
}