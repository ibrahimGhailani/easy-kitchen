package io.kitchen.easy.easykitchen.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.kitchen.easy.easykitchen.R
import io.kitchen.easy.easykitchen.kitchen.Order.OrderFormActivity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_kitchen_detail.*
import kotlinx.android.synthetic.main.item_meals.view.*


class KitchenDetailActivity : AppCompatActivity() {
    private val viewModel: KitchenDetailViewModel by lazy {
        ViewModelProviders.of(this)[KitchenDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Meals"


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MealAdapter(this, mutableListOf()) {
            val intent = Intent(this,OrderFormActivity::class.java)
            intent.putExtra("meal",it)
            startActivity(intent)
        }
        initializeObservers()

        viewModel.getMeals(intent.getStringExtra("id"))
    }

    private fun initializeObservers() {
        viewModel.loading.observe(this, Observer {
            it?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
                if (it) emptyView.visibility = View.INVISIBLE
            }
        })

        viewModel.meals.observe(this, Observer {
            it?.let {
                (recyclerView.adapter as MealAdapter).insertMeals(it)
                emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
        })
    }
}

@Parcelize
data class Meal(
        val name: String,
        val price: Double,
        val description: String,
        val logo: String
) : Parcelable


class MealAdapter(
        private val context: Context,
        private val meals: MutableList<Meal>,
        private val doOnMealClicked: (meal:Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meals, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.nameTextView.text = meal.name
        holder.description.text = meal.description
        holder.price.text = meal.price.toString()
        holder.rootView.setOnClickListener {
            doOnMealClicked.invoke(meal)
        }

        Glide.with(context).load(meal.logo).apply(RequestOptions().circleCrop()).into(holder.logo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView.rootView
        val nameTextView = itemView.nameTextView
        val description = itemView.descriptionTextView
        val price = itemView.priceTextView
        val logo = itemView.imageView

    }

    fun insertMeals(meals: List<Meal>) {
        this.meals.clear()
        this.meals.addAll(meals)
        notifyDataSetChanged()
    }

}