package com.codemobiles.android.cmauthenmvvm.ui.main.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentAlertDialogBinding

class AlertDialogFragment : DialogFragment(R.layout.fragment_alert_dialog) {

    val args = navArgs<AlertDialogFragmentArgs>()
    private lateinit var binding: FragmentAlertDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertDialogBinding.inflate(inflater)
        binding.title.text = args.value.title
        binding.content.text = args.value.subtitle
        return binding.root
    }


    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val width = ViewGroup.LayoutParams.MATCH_PARENT // or dpToPx(300) for specific dp width
            val height = dpToPx(200) // Convert 250dp to pixels
            window.setLayout(width, height)
            window.setGravity(Gravity.CENTER)
        }
    }
}