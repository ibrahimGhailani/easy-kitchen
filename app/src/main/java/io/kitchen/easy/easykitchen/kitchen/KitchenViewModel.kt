package io.kitchen.easy.easykitchen.kitchen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class KitchenViewModel : ViewModel() {
    val kitchens = MutableLiveData<List<Kitchen>>()
    private val allKitchens = mutableListOf<Kitchen>()
    val loading = MutableLiveData<Boolean>()
    private val selectedCategory = mutableListOf<String>()
    private var minCapacity = 0
    private var maxCapacity = 0
    private var searchText: String = ""
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
            name: String = searchText,
            minCapacity: Int = this.minCapacity,
            maxCapacity: Int = this.maxCapacity
    ) {
        searchText = name
        this.minCapacity = minCapacity
        if (maxCapacity != 0) this.maxCapacity = maxCapacity

        this.kitchens.value = allKitchens.filter {
            var filter = true
            if (name.isNotEmpty()) {
                filter = it.name.contains(searchText, true)
            }
            if (minCapacity != 0) {
                this.minCapacity = maxCapacity
                filter = filter && it.minCapacity >= this.minCapacity

            }
            if (maxCapacity != 0) {
                this.maxCapacity = maxCapacity
                filter = filter && it.maxCapacity <= this.maxCapacity
            }

            if (selectedCategory.isNotEmpty()) {
                filter = filter && it.category.containsAll(selectedCategory)
            }
            filter
        }
    }

    fun filterCategory(selected: Boolean, title: String) {
        if (selected) selectedCategory.add(title)
        else selectedCategory.remove(title)
        filter(searchText, minCapacity, maxCapacity)
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