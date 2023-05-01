package no.iktdev.setting

import no.iktdev.setting.model.ActionableComponentData
import no.iktdev.setting.model.LaunchableComponentData
import no.iktdev.setting.model.builder.LaunchableComponentDataBuilder
import org.apache.commons.lang3.SerializationUtils
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow


class TestComponentData {


    @Test
    fun testSerialization() {
        val testData = LaunchableComponentDataBuilder()
            .setUri("https://iktdev.no")
            .build()
        val emptyActionable = ActionableComponentData(value = null, target = null)
        assertDoesNotThrow {
            val result = SerializationUtils.serialize(testData)
            val result2 = SerializationUtils.serialize(emptyActionable)
            val back = SerializationUtils.deserialize<LaunchableComponentData>(result)
        }
    }
}