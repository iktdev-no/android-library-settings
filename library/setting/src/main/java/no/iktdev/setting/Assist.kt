package no.iktdev.setting

import android.content.Context
import android.util.TypedValue
import java.io.*

class Assist {
    companion object {
        fun getDeviceDp(context: Context, size: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size.toFloat(), context.resources.displayMetrics).toInt()
        }
    }

    fun isSerializable(obj: Any?): Boolean {
        return try {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(obj)
            oos.close()
            val bais = ByteArrayInputStream(baos.toByteArray())
            val ois = ObjectInputStream(bais)
            ois.readObject()
            ois.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            false
        }
    }

}