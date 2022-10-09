package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidsAdapter(private val clickListener: (asteroid: Asteroid) -> Unit) :
    RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    private lateinit var asteroids: List<Asteroid>
    private lateinit var binding: ListItemAsteroidBinding


    fun setAsteroids(data: List<Asteroid>) {
        this.asteroids = data
        notifyDataSetChanged()
    }

    // Bind data using the list_item_asteroid.xml file
    inner class AsteroidViewHolder(private val binding: ListItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindAsteroid(clickListener: (asteroid: Asteroid) -> Unit, asteroid: Asteroid) {

            //
            binding.asteroid = asteroid

            //
            binding.root.setOnClickListener {
                clickListener(asteroid)
            }

            //
            binding.executePendingBindings()
        }

    }

    /**
     * Called when RecyclerView needs a new [AsteroidViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new AsteroidViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new AsteroidViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new AsteroidViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        binding =
            ListItemAsteroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AsteroidViewHolder(binding)

    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [AsteroidViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [AsteroidViewHolder.getBindingAdapterPosition] which
     * will have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The AsteroidViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        //
        val asteroid = asteroids[position]

        //
        holder.bindAsteroid(clickListener, asteroid)

    }

//    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
//        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
//    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() = asteroids.size
}
