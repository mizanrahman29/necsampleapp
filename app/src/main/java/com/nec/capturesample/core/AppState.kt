package com.nec.capturesample.core

import android.graphics.Bitmap
import com.nec.sdk.biometric.data.FaceAttributes

object AppState {
    private var frame: Bitmap? = null
    private var croppedFaceImage: Bitmap? = null
    private var faceAttributes: FaceAttributes? = null

    fun setFaceDetails(
        faceImage: Bitmap?,
        croppedFaceImage: Bitmap?,
        faceAttributes: FaceAttributes?
    ) {
        this.frame = faceImage
        this.croppedFaceImage = croppedFaceImage
        this.faceAttributes = faceAttributes
    }

    fun getFaceImage() = this.frame

    fun getCroppedFaceImage() = this.croppedFaceImage

    fun getFaceAttributes() = this.faceAttributes
}