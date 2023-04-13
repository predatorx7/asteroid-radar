package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.databinding.FragmentDetailBindingImpl

class DetailFragment : Fragment() {
    private fun displayAstronomicalUnitExplanationAlertDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = com.udacity.asteroidradar.databinding.FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationAlertDialog()
        }

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid

        return binding.root
    }

}
