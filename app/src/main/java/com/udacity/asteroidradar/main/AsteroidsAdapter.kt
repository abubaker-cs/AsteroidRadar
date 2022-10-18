package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding


class AsteroidsAdapter(private val onClickListener: (asteroid: Asteroid) -> Unit) :
    RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    private lateinit var asteroids: List<Asteroid>
    private lateinit var binding: ListItemAsteroidBinding


    fun setAsteroids(data: List<Asteroid>) {
        this.asteroids = data
        notifyDataSetChanged()
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Asteroid]
     * has been updated.
     */
//    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
//        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }

    // Bind data using the list_item_asteroid.xml file
    inner class AsteroidViewHolder(private val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: (asteroid: Asteroid) -> Unit, asteroid: Asteroid) {

            //
            binding.asteroid = asteroid

            //
            binding.root.setOnClickListener {
                clickListener(asteroid)
            }

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        binding =
            ListItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AsteroidViewHolder(binding)

    }


    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        //
        val asteroid = asteroids[position]

        //
        holder.bind(onClickListener, asteroid)

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {

        val totalSize = asteroids.size

        // Printout List of Total Fetched Records from the API or Offline Room Database
        Log.i("Total Records: ", totalSize.toString())

        return asteroids.size
    }
}