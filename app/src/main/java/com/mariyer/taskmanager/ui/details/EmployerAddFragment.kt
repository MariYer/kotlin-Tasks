package com.mariyer.taskmanager.ui.details

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Employer
import com.mariyer.taskmanager.ui.details.adapters.EmployerPagerAdapter
import com.mariyer.taskmanager.ui.details.adapters.EmployerStaffListAdapter
import com.mariyer.taskmanager.ui.details.viewmodels.EmployerDetailViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import com.mariyer.taskmanager.utils.stringToInstant
import kotlinx.android.synthetic.main.fragment_department_detail.*
import kotlinx.android.synthetic.main.fragment_employer_basic_info.*
import kotlinx.android.synthetic.main.fragment_employer_carier.*
import kotlinx.android.synthetic.main.fragment_employer_detail.*
import kotlinx.android.synthetic.main.fragment_employer_detail.saveButton
import java.time.*
import java.util.*

class EmployerAddFragment : Fragment(R.layout.fragment_employer_detail) {

    private val viewModel: EmployerDetailViewModel by activityViewModels()
    private var staffAdapter: EmployerStaffListAdapter by AutoClearedValue<EmployerStaffListAdapter>()
    private val args: EmployerAddFragmentArgs by navArgs()

    private lateinit var adapter: EmployerPagerAdapter

    private val pageCaptions = listOf<String>(
        "Основная информация",
        "Карьера"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setOrgId(args.orgId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

        saveButton.setOnClickListener {
            viewModel.saveEmployerWithStaff(
                Employer(
                    id = 0L,
                    organizationId = args.orgId,
                    photoUrl = avatarUriTextView.text.toString().orEmpty(),
                    surname = surNameEditText?.text.toString().orEmpty(),
                    name = nameEditText?.text.toString().orEmpty(),
                    middleName = middleNameEditText?.text.toString().orEmpty(),
                    birthday = stringToInstant(birthdayEditText.text.toString()),
                    sex = if (womanSexRadioButton.isChecked) 'W' else 'M',
                    phone = phoneEditText.text.toString(),
                    email = emailEditText.text.toString(),
                    address = addressEditText.text.toString()
                )
            )

            findNavController().popBackStack()
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                saveButton.isVisible = (position == 0)
            }
        })

    }

    private fun init() {
        adapter = EmployerPagerAdapter(requireActivity())
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(pageCaptions[position])
        }.attach()

    }

}