package com.telex.utils

import android.util.Log
import com.bumptech.glide.load.engine.GlideException
import com.crashlytics.android.Crashlytics
import java.io.IOException
import timber.log.Timber

/**
 * @author Sergey Petrov
 */
class DebugTree : Timber.DebugTree()

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR) {
            if (throwable !is GlideException || throwable.rootCauses.find { it is IOException } == null) {
                if (throwable == null) {
                    Crashlytics.logException(UnknownError(message))
                } else {
                    Crashlytics.logException(throwable)
                }
            }
        }
    }
}
