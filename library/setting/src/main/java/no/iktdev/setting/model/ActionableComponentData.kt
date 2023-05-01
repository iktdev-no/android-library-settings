package no.iktdev.setting.model

import android.os.Bundle

class ActionableComponentData(
    val target: Class<*>?,
    override var value: Bundle?
) : ComponentData() {
}