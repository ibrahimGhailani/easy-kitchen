package io.kitchen.easy.easykitchen.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class KitchenDetailViewModel : ViewModel() {

    val meals = MutableLiveData<List<Meal>>()
    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val ref: CollectionReference by lazy {
        db.collection(COLLECTION_MEAL)
    }

    fun getMeals(id: String) {
        val meals = mutableListOf<Meal>()

        ref.whereEqualTo(FIELD_KITCHEN, id).get()
                .addOnSuccessListener {
                    it.forEach {
                        meals.add(Meal(
                                name = it.getString(FIELD_NAME) ?: "",
                                description = it.getString(FIELD_DESC) ?: "",
                                logo = it.getString(FIELD_IMAGE) ?: "",
                                price = it.getDouble(FIELD_PRICE) ?: 0.0
                        ))
                    }

                    this.meals.postValue(meals)
                }

    }

    companion object {
        const val COLLECTION_MEAL = "meal"

        const val FIELD_NAME = "name"
        const val FIELD_DESC = "description"
        const val FIELD_PRICE = "price"
        const val FIELD_IMAGE = "image"
        const val FIELD_KITCHEN = "kitchen"
    }
}