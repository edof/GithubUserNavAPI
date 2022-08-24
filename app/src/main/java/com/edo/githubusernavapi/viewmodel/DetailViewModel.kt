package com.edo.githubusernavapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edo.githubusernavapi.DBModule
import com.edo.githubusernavapi.GitResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val dbModule: DBModule) : ViewModel() {

    val resultOkFav = MutableLiveData<Boolean>()
    val resultDelFav = MutableLiveData<Boolean>()
    private var isFav = false

    fun setFav(item: GitResponse?) {
        viewModelScope.launch {
            item?.let {
                if (isFav) {
                    dbModule.userDao.delete(item)
                    resultDelFav.value = true
                } else {
                    dbModule.userDao.insert(item)
                    resultOkFav.value = true
                }
            }
            isFav = !isFav
        }
    }

    fun checkFav(id: Int, listenFav: () -> Unit){
        viewModelScope.launch {
            val user = dbModule.userDao.searchId(id)
            if (user != null){
                listenFav()
                isFav = true
            }
        }
    }

    class Factory(private val dbModule: DBModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            DetailViewModel(dbModule) as T
    }
}