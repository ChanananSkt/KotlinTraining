package com.codemobiles.android.cmauthenmvvm.viewmodel

import android.app.Application
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codemobiles.android.cmauthenmvvm.R
import com.codemobiles.android.cmauthenmvvm.model.ChartLineResult
import com.codemobiles.android.cmauthenmvvm.model.Datum
import com.codemobiles.android.cmauthenmvvm.model.User
import com.codemobiles.android.cmauthenmvvm.model.Youtube
import com.codemobiles.android.cmauthenmvvm.utils.RetrofitClient1
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow

class ChartViewModel(private val app: Application) : ViewModel() {
    val dataSets = MutableStateFlow<ArrayList<ILineDataSet>>(arrayListOf<ILineDataSet>())
    val mSelectedIndex = MutableLiveData<Float>(3f);
    var priceArray: List<Datum> = arrayListOf()

    fun loadData(data: String, isSecond: Boolean = false) {
        // Step 2
        val gson = Gson()
        val mPriceResult = gson.fromJson<ChartLineResult>(
            data,
            ChartLineResult::class.java
        )
        priceArray = mPriceResult.data
        val yVals = arrayListOf<Entry>()

        for (i in 0 until priceArray.size - 2) {
            val price = priceArray[i].prices.toString() + ""
            yVals.add(Entry(i.toFloat(), Integer.valueOf(price).toFloat()))
        }

        var title = ""
        if (isSecond) {
            title = "Chart 1#"
        } else {
            title = "Chart 2#"
        }


        val dataSet = LineDataSet(yVals, title)

        dataSet.lineWidth = 3f
        dataSet.fillColor =
            if (isSecond) Color.parseColor("#E91E63") else Color.parseColor("#EFB528")
        dataSet.fillAlpha = 20 //0-100
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(true)

        // Fill Gradient
        val fillGradient =
            if (isSecond) AppCompatResources.getDrawable(
                app.applicationContext,
                R.drawable.red_gradient1
            ) else AppCompatResources.getDrawable(
                app.applicationContext,
                R.drawable.red_gradient2
            )
        dataSet.fillDrawable = fillGradient


        dataSet.circleColors = arrayListOf(Color.parseColor("#FF0000"))
        dataSet.circleHoleColor =
            if (isSecond) Color.parseColor("#E91E63") else Color.parseColor("#491E73")

        dataSet.circleRadius = 5f
        dataSet.circleHoleRadius = 4f
        dataSet.setDrawCircleHole(true)
        dataSet.valueTextSize = 10f
        dataSet.valueTypeface = Typeface.DEFAULT
        dataSet.valueTextColor = Color.parseColor("#FBFBFB")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        dataSets.value.add(dataSet)
    }
}