package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

        // setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * IMPORTANT NOTE:
     * The setHasOptionsMenu(true) has been depreciated, we will be using addMenuProvider():
     * https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // I will be using the LinearLayout mode for the RecyclerView
        binding.asteroidsRecycler.layoutManager = LinearLayoutManager(requireContext())

        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Defining and binding the AsteroidsAdapter to the RecyclerView
        asteroidAdapter = AsteroidsAdapter { asteroid ->
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        }
        binding.asteroidsRecycler.adapter = asteroidAdapter


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

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {

            @SuppressLint("RestrictedApi")
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                // This will display icons for the menu items
                if (menu is MenuBuilder) {
                    menu.setOptionalIconsVisible(true)
                }

                // Add menu items here
                menuInflater.inflate(R.menu.main_overflow_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                // Handle the menu selection
                viewModel.updateFilter(
                    when (menuItem.itemId) {
                        R.id.show_week -> AsteroidApiFilter.SHOW_CURRENT_WEEK_DATA
                        R.id.show_today -> AsteroidApiFilter.SHOW_TODAY_DATA
                        else -> AsteroidApiFilter.SHOW_OFFLINE_SAVED_DATA
                    }
                )

                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
