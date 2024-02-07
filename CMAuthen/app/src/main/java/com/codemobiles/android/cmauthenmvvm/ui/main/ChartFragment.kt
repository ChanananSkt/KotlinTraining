package com.codemobiles.android.cmauthenmvvm.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentChartBinding
import com.codemobiles.android.cmauthenmvvm.databinding.FragmentFeedBinding
import com.codemobiles.android.cmauthenmvvm.viewmodel.AppViewModelFactory
import com.codemobiles.android.cmauthenmvvm.viewmodel.ChartViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.time.Duration

class ChartFragment : Fragment(R.layout.fragment_chart){
    private lateinit var binding: FragmentChartBinding
    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater)

        // Setup view-model
        val factory = AppViewModelFactory(app = requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[ChartViewModel::class.java]

        drawChart()
        observe()
        loadData()

        return binding.root
    }

    private fun loadData(){
        lifecycleScope.launch {
            // load data
            viewModel.loadData(requireContext().getString(R.string.dummy_line_data))
            viewModel.loadData(requireContext().getString(R.string.dummy_line_data2), true)

        }
    }

    private fun observe() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dataSets.collect(){
                val data = LineData(it)
                data.setValueFormatter(Formatter())
                binding.mChart.data = data
                binding.mChart.invalidate()
            }
        }
    }

    private fun drawChart() {
        // 1. Configure Chart Global properties (Line Color, Grid Color, Animation Label, Legend)
        // 2. Create data-entry from raw data that you feed server
        // 3. Create dataset (one dataset standing for one graph) combines both data-entry and visual properties
        // 4. Insert all dataset(s) into Chart-Data object
        // 5. Set chart-data into chart object via mChart.setData = ...
        // 6. Call invalidate to draw graphs

        val mChart = binding.mChart

        // 1#
        mChart.setBackgroundColor(Color.parseColor("#252934"))
        val desc = Description()
        desc.text = "SiamGold App."
        desc.textColor = Color.parseColor("#FFFFFF")
        desc.setPosition(420f, 80f)
        desc.textSize = 20f
        mChart.description = desc
        mChart.setExtraOffsets(5f, 30f, 5f, 5f)
        mChart.dragDecelerationFrictionCoef = 0.95f
        mChart.isHighlightPerTapEnabled = true

        // Detect click value circle
        mChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                viewModel.mSelectedIndex.value = e!!.x;
                Toast.makeText(activity, e.x.toString(), Toast.LENGTH_LONG).show()
            }
        })


        // axis left
        val leftAxis = mChart.axisLeft
        leftAxis.setDrawLabels(true)
        leftAxis.textColor = Color.parseColor("#FFFFFF")
        leftAxis.setDrawAxisLine(false)
        leftAxis.gridColor = Color.parseColor("#008D8A9A") // from left to right line
        leftAxis.setDrawGridLines(false)

        // axis right
        val rightAxis = mChart.axisRight
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawLabels(false)
        rightAxis.gridColor = Color.parseColor("#008D8A9A") // from right to left line

        mChart.xAxis.setDrawLabels(true)
        mChart.xAxis.textColor = Color.parseColor("#FFFFFF")
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        mChart.legend.isEnabled = true // label under chart
        mChart.legend.textColor = Color.parseColor("#FFFFFF")
        mChart.legend.textSize = 15f

        val xAxis = mChart.xAxis
        xAxis.gridColor = Color.parseColor("#009d9272") // From bottom to top
        xAxis.setDrawGridLines(true)
    }


    inner class Formatter() : ValueFormatter() {
        private val mFormat = DecimalFormat("###,###")

        override fun getFormattedValue(value: Float): String {
            return mFormat.format(value) + " Baht"
        }
    }

}