package com.example.pioneerstask.ui.display

import androidx.lifecycle.ViewModel
import com.example.pioneerstask.data.api.ApiService
import com.example.pioneerstask.data.api.ApiServiceFactory
import com.example.pioneerstask.data.model.GitModel

import io.reactivex.Observable

class DisplayViewModel : ViewModel() {
    private val service: ApiService by lazy {
        ApiServiceFactory.getInstance()
    }

    fun getData(
        created: String,
        sort: String,
        order: String,
        per_page: String,
        lang: String
    ): Observable<GitModel> {
        return service.getData(
            created,
            sort,
            order,
            per_page,lang)
    }


}