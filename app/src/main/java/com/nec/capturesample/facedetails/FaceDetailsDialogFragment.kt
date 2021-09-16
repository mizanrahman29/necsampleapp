package com.nec.capturesample.facedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nec.capturesample.core.AppState
import com.nec.capturesample.databinding.FragmentFaceDetailsBinding

class FaceDetailsDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentFaceDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.faceImg.setImageBitmap(AppState.getFaceImage())
        binding.croppedFaceImg.setImageBitmap(AppState.getCroppedFaceImage())
        val faceAttributes = AppState.getFaceAttributes()
        StringBuffer().apply {
            this.append("pan: ${faceAttributes?.facePan}\n")
            this.append("roll: ${faceAttributes?.faceRoll}\n")
            this.append("tilt: ${faceAttributes?.faceTilt}\n")
            this.append("quality: ${faceAttributes?.faceQuality}\n")
            this.append("sharpness: ${faceAttributes?.faceSharpnessScore}\n")
            this.append("frontalScore: ${faceAttributes?.frontalFaceScore}\n")
            this.append("eyeDistance: ${faceAttributes?.eyeDistance}\n")
            binding.faceDetails.text = this
        }
    }
}