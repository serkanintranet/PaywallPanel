package com.intranet.paywallpanel.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentTestBinding


class TestFragment : BaseFragment<TestViewModel, FragmentTestBinding>() {
    override fun getViewModel(): Class<TestViewModel> = TestViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestBinding = FragmentTestBinding.inflate(inflater, container, false)

}