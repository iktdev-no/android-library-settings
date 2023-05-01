package no.iktdev.setting.model

import android.os.Bundle

class LaunchableComponentData(
    val uri: String?,
    override var value: Bundle?
): ComponentData() {
}