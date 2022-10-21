package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
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

    @SuppressLint("NotifyDataSetChanged")
    fun setAsteroids(data: List<Asteroid>) {

        this.asteroids = data

        // Notify any registered observers that the data set has changed.
        notifyDataSetChanged()

    }

    // Bind data using the list_item_asteroid.xml file
    inner class AsteroidViewHolder(private val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: (asteroid: Asteroid) -> Unit, asteroid: Asteroid) {

            // Bind data with asteroid variable stored in the list_item_asteroid.xml file
            binding.asteroid = asteroid

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

        // Inflate list_item_asteroid.xml file
        binding =
            ListItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AsteroidViewHolder(binding)

    }


    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        // Get current position of the asteroid's record
        val asteroid = asteroids[position]

        holder.bind(onClickListener, asteroid)

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {

        // Get Total Size of the List
        val totalSize = asteroids.size

        // Printout List of Total Fetched Records from the API or Offline Room Database
        Log.i("Total Records: ", totalSize.toString())

        // Return Total Size of the List
        return asteroids.size
    }
}