package com.codemobiles.android.cmauthenmvvm.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentCameraBinding
import com.codemobiles.android.cmauthenmvvm.viewmodel.AppViewModelFactory
import com.codemobiles.android.cmauthenmvvm.viewmodel.CameraViewModel
import com.codemobiles.android.cmauthenmvvm.viewmodel.ChartViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraFragment : Fragment(R.layout.fragment_camera){
    private lateinit var binding:FragmentCameraBinding
    private lateinit var viewModel: CameraViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater)

        // Setup view-model
        val factory = AppViewModelFactory(app = requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[CameraViewModel::class.java]

        setEventListener()
        return binding.root
    }

    private fun setEventListener() {
        binding.cameraBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.imagePreview.setImageURI(uri)

            lifecycleScope.launch(Dispatchers.IO) { viewModel.upload(uri) }



        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }
}