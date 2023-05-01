package no.iktdev.setting.model.builder

import android.os.Bundle
import no.iktdev.setting.model.LaunchableComponentData
import java.io.Serializable

class LaunchableComponentDataBuilder() {
    private var bundle: Bundle = Bundle()
    private var target: String? = null

    fun setBundle(bundle: Bundle): LaunchableComponentDataBuilder {
        this.bundle = bundle
        return this
    }

    fun setUri(uri: String): LaunchableComponentDataBuilder {
        this.target = uri
        return this
    }

    fun applyExtra(key: String, value: String) = apply {
        bundle.putString(key, value)
    }

    fun applyExtra(key: String, value: Int) = apply {
        bundle.putInt(key, value)
    }

    fun applyExtra(key: String, value: Float) = apply {
        bundle.putFloat(key, value)
    }

    fun applyExtra(key: String, value: Boolean) = apply {
        bundle.putBoolean(key, value)
    }

    fun applyPayload(key: String, value: Serializable) = apply {
        bundle.putSerializable(key, value)
    }

    fun build(): LaunchableComponentData {
        return LaunchableComponentData(
            uri = target,
            value = if (!bundle.isEmpty && bundle.size() > 0) bundle else null
        )
    }


}