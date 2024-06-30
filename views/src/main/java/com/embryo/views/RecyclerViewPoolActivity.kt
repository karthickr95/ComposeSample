package com.embryo.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.embryo.views.databinding.ActivityRecyclerViewPoolBinding
import com.embryo.views.databinding.ItemRecyclerViewBinding
import com.embryo.views.databinding.ItemViewPagerBinding
import java.util.concurrent.atomic.AtomicInteger

class RecyclerViewPoolActivity : BaseViewBindingActivity<ActivityRecyclerViewPoolBinding>() {

    override fun inflateBinding() = ActivityRecyclerViewPoolBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewPager.adapter = RVViewPagerAdapter(layoutInflater)

    }
}

private class RVViewPagerAdapter(
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<RVViewPagerAdapter.ViewHolder>() {

    private val viewPool = RecycledViewPool()

    private val rvItemInflationCount = AtomicInteger(0)

    class ViewHolder(
        private val binding: ItemViewPagerBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemViewPagerBinding.inflate(layoutInflater, parent, false).apply {
                recyclerView.setRecycledViewPool(viewPool)
                recyclerView.recycledViewPool.setMaxRecycledViews(10, 20)
                (recyclerView.layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
                recyclerView.adapter = RVAdapter(layoutInflater) {
                    Log.d(
                        "recyclerview",
                        "rvItemInflationCount " + rvItemInflationCount.incrementAndGet()
                    )
                }
            }
        )

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

}

private class RVAdapter(
    private val layoutInflater: LayoutInflater,
    private val onInflation: () -> Unit,
) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRecyclerViewBinding.inflate(layoutInflater, parent, false)
    ).also {
        onInflation()
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabel.text = position.toString()
    }

}