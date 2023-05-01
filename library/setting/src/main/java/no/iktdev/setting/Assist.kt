package no.iktdev.setting

import android.content.Context
import android.util.TypedValue

class Assist {
    companion object {
        fun getDeviceDp(context: Context, size: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), context.resources.displayMetrics).toInt()
        }
    }
}