package io.kitchen.easy.easykitchen.kitchen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseApp
import io.kitchen.easy.easykitchen.R
import kotlinx.android.synthetic.main.activity_kitchen.*
import kotlinx.android.synthetic.main.item_kitchen.view.*

class KitchenActivity : AppCompatActivity() {

    private val viewModel: KitchenViewModel by lazy {
        ViewModelProviders.of(this)[KitchenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kitchen)
        FirebaseApp.initializeApp(this)

        initializeObservers()

        viewModel.getKitchens()


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = KitchenAdapter(this, mutableListOf()) {

            TODO("Start Activity details")
        }
    }

    private fun initializeObservers() {
        viewModel.kitchens.observe(this, Observer {
            it?.let {
                (recyclerView.adapter as KitchenAdapter).insertKitchens(it)
            }
        })
    }
}

data class Location(
        val latitude: Double,
        val longitude: Double
)

data class Kitchen(
        val name: String,
        val location: Location,
        val minCapacity: Long,
        val maxCapacity: Long,
        val logo: String
)

class KitchenAdapter(
        private val context: Context,
        private val kitchens: MutableList<Kitchen>,
        private val doOnKitchenClicked: () -> Unit
) : RecyclerView.Adapter<KitchenAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kitchen, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = kitchens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitchen = kitchens[position]
        holder.nameTextView.text = kitchen.name
        holder.location.text = "(${kitchen.location.latitude}, ${kitchen.location.longitude})"
        holder.capacity.text = "${kitchen.minCapacity} - ${kitchen.maxCapacity} orders"
        holder.rootView.setOnClickListener {
            doOnKitchenClicked.invoke()
        }

        Glide.with(context).load(kitchen.logo).into(holder.logo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView.rootView
        val nameTextView = itemView.nameTextView
        val location = itemView.locationTextView
        val capacity = itemView.capacityTextView
        val logo = itemView.imageView

    }

    fun insertKitchens(kitchens: List<Kitchen>) {
        this.kitchens.clear()
        this.kitchens.addAll(kitchens)
        notifyDataSetChanged()
    }

}