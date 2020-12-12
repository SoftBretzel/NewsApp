package com.example.newsapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CustomAdapter(private val dataSet: ArrayList<ArticlesDto>,
                    private val listener: RecyclerViewClickListener,
                    private val onBottomReachedListener: OnBottomReachedListener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtTitle: TextView
        val txtAuthor: TextView
        val txtDate: TextView
        val imgAvatar: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d("TAG", "Element $adapterPosition clicked.") }
            txtTitle = v.findViewById(R.id.txtTitle)
            txtAuthor = v.findViewById(R.id.txtAuthor)
            txtDate= v.findViewById(R.id.txtDate)
            imgAvatar= v.findViewById(R.id.imgAvatar)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_user, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("TAG", "Element $position set.")

        viewHolder.itemView.setOnClickListener {  listener.recyclerViewListClicked(position)}

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.txtTitle.text = dataSet[position].title
        viewHolder.txtAuthor.text = dataSet[position].author
        viewHolder.txtDate.text = dataSet[position].date
        if(dataSet[position].linkImg == "null"){
            Picasso.get().load("https://cdn3.iconfinder.com/data/icons/ballicons-reloaded-free/512/icon-70-512.png").into(viewHolder.imgAvatar)
        }
        else{
            Picasso.get().load(dataSet[position].linkImg).into(viewHolder.imgAvatar)}

        if (position == (itemCount - 1)){

            onBottomReachedListener.onBottomReached(position);

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun addArticles(dataset2: ArrayList<ArticlesDto>) {
        dataSet.addAll(dataset2);
        notifyDataSetChanged();
    }

    interface RecyclerViewClickListener {
        fun recyclerViewListClicked(position: Int)
    }

    interface OnBottomReachedListener {
        fun onBottomReached(position: Int)
    }


}