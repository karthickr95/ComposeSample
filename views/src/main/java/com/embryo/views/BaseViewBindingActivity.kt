package com.embryo.views

import android.os.Bundle
import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<B : ViewBinding> : ComponentActivity() {

    protected lateinit var binding: B

    abstract fun inflateBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding.root)
    }
}
