package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the [DetailFragment]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate XML file using DataBinding
        val binding = FragmentDetailBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = viewLifecycleOwner

        // Giving the binding access to the Bundled Arguments
        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid
        binding.asteroid = asteroid

        // Initialize Custom Dialog for providing information about the Astronomical Unit (au)
        binding.helpButton.setOnClickListener {
            infoDialog()
        }

        return binding.root
    }

    // Custom Dialog for explaining Astronomical Unit (au)
    private fun infoDialog() {

        // Configurations for the Custom Dialog
        val builder = AlertDialog.Builder(requireActivity())

            // Description of the Astronomical Unit
            .setMessage(getString(R.string.astronomical_unit_explanation))

            // Ok Button
            .setPositiveButton(android.R.string.ok, null)

        // Initialize the Custom Dialog
        builder.create().show()

    }

}
