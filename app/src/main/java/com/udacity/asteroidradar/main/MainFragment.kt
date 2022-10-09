package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.main.enums.AsteroidApiFilter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    /**
     * Lazily initialize our [MainViewModel].
     */
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    // Reference to the AsteroidsAdapter
    private lateinit var asteroidAdapter: AsteroidsAdapter

    // Reference for the fragment_main.xml which I will later on inflate in the onCreateView()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the MainFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate Layout: @layout/fragment_main.xml
        _binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the MainViewModel
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // I will be using the LinearLayout mode for the RecyclerView
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())

        // Defining and binding the AsteroidsAdapter to the RecyclerView
        asteroidAdapter = AsteroidsAdapter { asteroid ->
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        }
        binding.asteroidRecycler.adapter = asteroidAdapter


        /**
         * Asteroid
         */
        viewModel.status.onEach { asteroidState ->
            asteroidAdapter.setAsteroids(asteroidState.asteroids)
        }.launchIn(lifecycleScope)


        /**
         * Downloading
         */
        viewModel.downloadingState.onEach { isDownloading ->
            binding.downloadingProgressBar.isVisible = isDownloading
        }.launchIn(lifecycleScope)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * We need to provide a menu option so the user can change the filter
     */
    @Deprecated("Deprecated in Java", ReplaceWith("true"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_week -> AsteroidApiFilter.SHOW_WEEK
                R.id.show_today -> AsteroidApiFilter.SHOW_TODAY
                else -> AsteroidApiFilter.SHOW_SAVED
            }
        )

        return true
    }

}
