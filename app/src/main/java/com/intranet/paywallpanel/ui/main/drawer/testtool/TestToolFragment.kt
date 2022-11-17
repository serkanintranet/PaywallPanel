package com.intranet.paywallpanel.ui.main.drawer.testtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentTestToolBinding


class TestToolFragment : BaseFragment<TestToolViewModel, FragmentTestToolBinding>() {
    override fun getViewModel(): Class<TestToolViewModel> = TestToolViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestToolBinding = FragmentTestToolBinding.inflate(inflater, container, false)

}