package no.iktdev.setting.model

import android.os.Bundle
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import java.io.*


internal class SettingComponentSettingDescriptorBaseTest {

    @Test
    fun testSerializable() {
     val obj = SettingComponentDescriptor(
         icon = 0,
         title = "Potetmos",
         description = null,
         type = SettingComponentType.SWITCH,
         groupName = 0,
         setting = null,
         payload = ComponentData(listOf("Potetmos"))
     )
        assertDoesNotThrow {
            isSerializable(obj)
            isSerializable(obj.description)
            isSerializable(obj.icon)
            isSerializable(obj.title)
            isSerializable(obj.type)
            isSerializable(obj.groupName)
            isSerializable(obj.setting)
            isSerializable(obj.payload)

            val bundle = Bundle().putSerializable("potet", obj)
            isSerializable(bundle)
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
            false
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}