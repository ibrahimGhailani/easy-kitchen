package io.kitchen.easy.easykitchen.order

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.kitchen.easy.easykitchen.R

import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.content_order_list.*
import kotlinx.android.synthetic.main.item_meals.view.*

class OrderListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        setSupportActionBar(toolbar)

        orderList.adapter = OrderAdapter(this, getOrders(this).toMutableList())
        orderList.layoutManager = LinearLayoutManager(this)
    }

}

class OrderAdapter(private val context: Context,
                   private val orders: MutableList<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meals, parent, false)
        return OrderAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, position: Int) {
        val order = orders[position]
        holder.nameTextView.text = order.meal.name
        holder.description.text = order.meal.description
        holder.price.text = order.meal.price.toString()

        Glide.with(context).load(order.meal.logo).apply(RequestOptions().circleCrop()).into(holder.logo)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView.rootView
        val nameTextView = itemView.nameTextView
        val description = itemView.descriptionTextView
        val price = itemView.priceTextView
        val logo = itemView.imageView

    }
}
