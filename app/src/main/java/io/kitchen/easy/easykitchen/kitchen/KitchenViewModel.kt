package io.kitchen.easy.easykitchen.kitchen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class KitchenViewModel : ViewModel() {

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val ref: CollectionReference by lazy {
        db.collection(COLLECTION_KITCHEN)
    }

    fun getKitchens() {
        val kitchens = mutableListOf<Kitchen>()
        ref.get().addOnSuccessListener {
            it.forEach {
                val name = it.getString(FIELD_NAME) ?: ""
                val max = it.getLong(FIELD_MAX_CAPACITY) ?: 0
                val min = it.getLong(FIELD_MIN_CAPACITY) ?: 0
                val logo = it.getString(FIELD_LOGO) ?: ""
                val location = it.getGeoPoint(FIELD_LOCATION)

                Log.d(TAG, "$name $max $min $logo")
                kitchens.add(Kitchen(
                        name = name,
                        maxCapacity = max,
                        minCapacity = min,
                        location = Location(location?.latitude ?: 0.0, location?.longitude ?: 0.0),
                        logo = logo
                ))
            }
            this.kitchens.postValue(kitchens)

        }

    }

    val kitchens = MutableLiveData<List<Kitchen>>()

    companion object {
        const val TAG = "KitchenViewModel"
        const val COLLECTION_KITCHEN = "kitchen"

        const val FIELD_NAME = "name"
        const val FIELD_MAX_CAPACITY = "maxCapacity"
        const val FIELD_MIN_CAPACITY = "minCapacity"
        const val FIELD_LOGO = "image"
        const val FIELD_LOCATION = "location"
    }
}