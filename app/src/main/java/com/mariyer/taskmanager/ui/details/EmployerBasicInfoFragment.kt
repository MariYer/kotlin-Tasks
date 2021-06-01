package com.mariyer.taskmanager.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mariyer.taskmanager.R
import kotlinx.android.synthetic.main.fragment_employer_basic_info.*

class EmployerBasicInfoFragment: Fragment(R.layout.fragment_employer_basic_info) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadAvatarButton.setOnClickListener {
            chooseImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CHOOSE_IMAGE_CODE){
            avatarImageView.setImageURI(data?.data)
            avatarUriTextView.text = data?.data.toString()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_IMAGE_CODE)
    }


    companion object {
        const val CHOOSE_IMAGE_CODE = 101
    }
}