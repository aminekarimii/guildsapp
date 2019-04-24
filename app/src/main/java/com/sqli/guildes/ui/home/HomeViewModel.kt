package com.sqli.guildes.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.ui.base.BaseViewModel

class HomeViewModel(dataManager : DataManager) : BaseViewModel(dataManager) {

    private val _guildes = MutableLiveData<Resource<List<Guilde>>>()
    val guildes: LiveData<Resource<List<Guilde>>>
        get() = _guildes

    fun loadGuildes(less : Boolean) {
        val guildes : List<Guilde> = listOf(
                Guilde(name = "Aaaaa", description = "Alter accolas ducunt ad brabeuta. The pit is full of light.", points = 100),
                Guilde(name = "Bbbbbb", description = "Alter accolas ducunt ad brabeuta. The pit is full of light.", points = 100),
                Guilde(name = "Cbbbbbb", description = "Alter accolas ducunt ad brabeuta. The pit is full of light.", points = 100),
                Guilde(name = "Dddddd", description = "Alter accolas ducunt ad brabeuta. The pit is full of light.", points = 100),
                Guilde(name = "Eeeeee", description = "Alter accolas ducunt ad brabeuta. The pit is full of light.", points = 100)
        )

        val res = Resource.Success(if(less) guildes.take(3) else guildes)
        _guildes.postValue(res)
    }
}
