package io.kitchen.easy.easykitchen.kitchen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.kitchen.easy.easykitchen.detail.KitchenDetailViewModel.Companion.FIELD_NAME

class KitchenViewModel : ViewModel() {
    val kitchens = MutableLiveData<List<Kitchen>>()
    private val allKitchens = mutableListOf<Kitchen>()
    val loading = MutableLiveData<Boolean>()
    val selectedCategory = mutableListOf<String>()
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val ref: CollectionReference by lazy {
        db.collection(COLLECTION_KITCHEN)
    }

    fun getKitchens() {
        val kitchens = mutableListOf<Kitchen>()
        loading.postValue(true)
        ref.get().addOnSuccessListener {
            it.forEach {
                val id = it.id
                val name = it.getString(FIELD_NAME) ?: ""
                val max = it.getLong(FIELD_MAX_CAPACITY) ?: 0
                val min = it.getLong(FIELD_MIN_CAPACITY) ?: 0
                val logo = it.getString(FIELD_LOGO) ?: ""
                val location = it.getGeoPoint(FIELD_LOCATION)
                val categories = it.get(FIELD_CATEGORY) as List<String>

                Log.d(TAG, "$name $max $min $logo")
                kitchens.add(Kitchen(
                        id = id,
                        name = name,
                        maxCapacity = max,
                        minCapacity = min,
                        location = Location(location?.latitude ?: 0.0, location?.longitude ?: 0.0),
                        logo = logo,
                        category = categories
                ))
            }
            allKitchens.addAll(kitchens)
            this.kitchens.postValue(kitchens)
            loading.postValue(false)

        }
    }

    fun filter(
            name: String = "",
            minCapacity: Int = 0,
            maxCapacity: Int = 0,
            categories: List<String> = listOf()
    ) {
        this.kitchens.value =
                when {
                    name.isNotEmpty() && minCapacity != maxCapacity && categories.isNotEmpty() -> {
                        allKitchens.filter {
                            it.name.contains(name, true) && if (maxCapacity != 0) {
                                it.maxCapacity <= maxCapacity && it.minCapacity >= minCapacity

                            } else {
                                it.minCapacity >= minCapacity
                            }

                            it.category.containsAll(categories)
                        }
                    }
                    name.isNotEmpty() -> allKitchens.filter {
                        it.name.contains(name, true)
                    }
                    minCapacity != maxCapacity -> allKitchens.filter {
                        if (maxCapacity != 0) {
                            it.maxCapacity <= maxCapacity && it.minCapacity >= minCapacity

                        } else {
                            it.minCapacity >= minCapacity
                        }
                    }
                    categories.isNotEmpty() -> {
                        allKitchens.filter {
                            it.category.containsAll(categories)
                        }
                    }
                    else -> allKitchens
                }
    }

    fun filterCategory(selected: Boolean, title: String) {
        if (selected) selectedCategory.add(title)
        else selectedCategory.remove(title)
        filter(categories = selectedCategory)
    }


    companion object {
        const val TAG = "KitchenViewModel"
        const val COLLECTION_KITCHEN = "kitchen"

        const val FIELD_NAME = "name"
        const val FIELD_MAX_CAPACITY = "maxCapacity"
        const val FIELD_MIN_CAPACITY = "minCapacity"
        const val FIELD_LOGO = "image"
        const val FIELD_LOCATION = "location"
        const val FIELD_CATEGORY = "category"
    }
}