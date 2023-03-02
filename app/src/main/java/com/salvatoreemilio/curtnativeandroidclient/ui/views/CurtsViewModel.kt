package com.salvatoreemilio.curtnativeandroidclient.ui.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.salvatoreemilio.curtnativeandroidclient.data.repository.remote.CurtRepository
import com.salvatoreemilio.curtnativeandroidclient.model.Curt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CurtsViewModelFactory(private val host: String, private val key: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CurtsViewModel(host, key) as T
}

class CurtsViewModel(host: String, key: String) : ViewModel() {
    private val _loading = MutableStateFlow<Boolean>(false)
    private var _host: String = host
    private var _key: String = key

    val loading = _loading.asStateFlow()

    var curtsResponse: List<Curt>? by mutableStateOf(null)
    var err: Exception? by mutableStateOf(null)


    init {
        getCurts()
    }

    private fun getCurts() {
        if (curtsResponse == null) {
            viewModelScope.launch {
                _loading.update { true }
                try {
                    val curtApi = CurtRepository()
                    val curts = curtApi.getCurts(_host, _key)
                    curtsResponse = curts
                } catch (e: Exception) {
                    err = e
                } finally {
                    _loading.update { false }
                }
            }
        }
    }

    fun updateHost(host: String, key: String){
        if (_host != host || _key != key){
            curtsResponse = null
            err = null
            _host = host
            _key = key
            getCurts()
        }
    }

    fun refresh() {
        err = null
        curtsResponse = null
        getCurts()
    }
}