package com.nec.capturesample.buffered

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nec.capturesample.R
import com.nec.capturesample.core.AppState
import com.nec.capturesample.databinding.FragmentSampleBufferedBinding
import com.nec.sdk.biometric.data.FaceAttributes
import com.nec.sdk.biometric.facecapture.BufferedFaceFragment
import com.nec.sdk.biometric.facecapture.FaceDetectionProgress

class SampleBufferedFragment : Fragment() {

    private lateinit var binding: FragmentSampleBufferedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSampleBufferedBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.faceCaptureFragment) as BufferedFaceFragment).apply {
            this.setNavDelegateListener(object : BufferedFaceFragment.NavDelegate {
                override fun onFaceDetectionCompleted(
                    frame: Bitmap,
                    croppedFaceImage: Bitmap,
                    faceAttributes: FaceAttributes
                ) {
                    binding.instructionTv.text = getString(R.string.success)
                    AppState.setFaceDetails(frame, croppedFaceImage, faceAttributes)
                }

                override fun onFaceDetectionFailed() {
                    findNavController().popBackStack()
                }

                override fun onFaceDetectionProgressChanged(faceDetectionProgress: FaceDetectionProgress) {
                    showMessageByProgress(faceDetectionProgress)
                    Log.e("Mizan", faceDetectionProgress.toString())
                }
            })
        }
    }

    private fun showMessageByProgress(faceDetectionProgress: FaceDetectionProgress) {
        val message = when (faceDetectionProgress) {
            FaceDetectionProgress.JUDGING -> getString(R.string.face_still)
            FaceDetectionProgress.FACE_NOT_FOUND -> getString(R.string.msg_face_not_found)
            FaceDetectionProgress.SHAKE_HEAD -> getString(R.string.label_shaking)
            FaceDetectionProgress.TOO_CLOSE -> getString(R.string.msg_too_large_face)
            FaceDetectionProgress.TOO_FAR -> getString(R.string.msg_too_small_face)
            FaceDetectionProgress.MOVING -> getString(R.string.msg_too_moving)
            FaceDetectionProgress.TOO_RIGHT -> getString(R.string.face_shift_left)
            FaceDetectionProgress.TOO_LEFT -> getString(R.string.face_shift_right)
            FaceDetectionProgress.TOO_LOW -> getString(R.string.face_shift_higher)
            FaceDetectionProgress.TOO_HIGH -> getString(R.string.face_shift_lower)
            FaceDetectionProgress.FAKE -> getString(R.string.face_error)
            FaceDetectionProgress.BAD_POSE -> getString(R.string.bad_face_pose)
            FaceDetectionProgress.FACE_OBSTRUCTED -> getString(R.string.face_feedback_not_obstructed)
            FaceDetectionProgress.NOT_SINGLE_PERSON -> getString(R.string.make_sure_you_re_the_only_person_in_the_frame)
            FaceDetectionProgress.EYES_NOT_OPEN -> getString(R.string.face_feedback_eyes_open)
            FaceDetectionProgress.KEEP_IN_CENTER -> getString(R.string.msg_position_your_face_within_the_center)
            FaceDetectionProgress.BAD_QUALITY_FACE -> getString(R.string.bad_face_quality)
            else -> getString(com.nec.sdk.R.string.capturing)
        }
        binding.instructionTv.text = message
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.menu_info_action)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info_action -> {
                if (AppState.getFaceImage() != null && AppState.getCroppedFaceImage() != null && AppState.getFaceAttributes() != null) {
                    findNavController().navigate(R.id.action_sampleBufferedFragment_to_faceDetailsDialogFragment)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
