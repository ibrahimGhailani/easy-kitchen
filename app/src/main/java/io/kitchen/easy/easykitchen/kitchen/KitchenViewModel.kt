package io.kitchen.easy.easykitchen.kitchen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class KitchenViewModel: ViewModel() {
    fun getKitchens() {
        val newKitchens= listOf(

                Kitchen(
                        name = "First Kitchen",
                        location = Location(
                                23.333,
                                45.45555
                        ),
                        minCapacity = 0,
                        maxCapacity = 100,
                        logo = "http://www.arabnews.com/sites/default/files/styles/n_670_395/public/2017/10/04/1007341-1450603817.jpg"

                ),
                Kitchen(
                        name = "Second Kitchen",
                        location = Location(
                                23.333,
                                45.45555
                        ),
                        minCapacity = 0,
                        maxCapacity = 100,
                        logo = "http://www.arabnews.com/sites/default/files/styles/n_670_395/public/2017/10/04/1007341-1450603817.jpg"

                ),
                Kitchen(
                        name = "Third Kitchen",
                        location = Location(
                                23.333,
                                45.45555
                        ),
                        minCapacity = 0,
                        maxCapacity = 100,
                        logo = "http://www.arabnews.com/sites/default/files/styles/n_670_395/public/2017/10/04/1007341-1450603817.jpg"

                )
        )

        kitchens.postValue(newKitchens)
    }

    val kitchens = MutableLiveData<List<Kitchen>>()
}