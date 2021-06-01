package com.mariyer.taskmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mariyer.taskmanager.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        organizationButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_organizationsListFragment) }
        projectButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_projectsListFragment) }
    }
}