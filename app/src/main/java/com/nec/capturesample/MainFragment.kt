package com.nec.capturesample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nec.capturesample.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.captureBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sampleBufferedFragment)
        }

        (requireContext().applicationContext as SampleApp).sdkInitCallback.subscribe {
            binding.isSdkReady = it
        }
    }

    override fun onResume() {
        super.onResume()
        binding.isSdkReady = (requireContext().applicationContext as SampleApp).isSDKInit
    }
}