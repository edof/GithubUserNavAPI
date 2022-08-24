package com.edo.githubusernavapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edo.githubusernavapi.DBModule

class FavoriteViewModel(private val dbModule: DBModule) : ViewModel(){

    fun getFavUsers() = dbModule.userDao.load()
    class Factory(private val dbModule: DBModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FavoriteViewModel(dbModule) as T
    }
}