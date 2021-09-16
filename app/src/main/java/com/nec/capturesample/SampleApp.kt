package com.nec.capturesample

import android.app.Application
import android.util.Log
import com.nec.sdk.biometric.BiometricCore
import com.nec.sdk.wallet.DigitalWallet
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class SampleApp : Application() {

    val sdkInitCallback = PublishSubject.create<Boolean>()
    var isSDKInit = false
    override fun onCreate() {
        super.onCreate()
        BiometricCore.newInstance(this)
            .initialize(isCentralizedFlow = true, progressHandler = { progressCount ->
                Log.d(TAG, progressCount.toString())
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.error) {
                        Log.d(TAG, "SDK init Failed $it")
                        sdkInitCallback.onNext(false)
                        isSDKInit = false
                    } else {
                        Log.d(TAG, "SDK init Success $it")
                        initDependencies()
                        sdkInitCallback.onNext(true)
                        isSDKInit = true
                    }
                },
                {
                    Log.d(TAG, "SDK Error $it")
                    sdkInitCallback.onNext(false)
                    isSDKInit = false
                }
            )
    }

    private fun initDependencies() {
        DigitalWallet.init(this)
    }

    companion object {
        const val TAG = "SampleApp"
    }
}