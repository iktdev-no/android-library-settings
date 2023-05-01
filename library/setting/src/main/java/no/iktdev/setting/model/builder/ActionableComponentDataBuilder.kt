package no.iktdev.setting.model.builder

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import no.iktdev.setting.model.ActionableComponentData
import no.iktdev.setting.ui.activities.SettingsRenderActivity
import java.io.Serializable

class ActionableComponentDataBuilder() {
    private var bundle: Bundle = Bundle()
    private var target: Class<*>? = null
    private var title: String? = null

    fun setBundle(bundle: Bundle): ActionableComponentDataBuilder {
        this.bundle = bundle
        return this
    }

    fun setTitle(title: String): ActionableComponentDataBuilder {
        this.title = title
        return this
    }

    fun setTitle(context: Context, @StringRes title: Int): ActionableComponentDataBuilder {
        this.title = context.getString(title)
        return this
    }

    fun setActivityClass(target: Class<*>?): ActionableComponentDataBuilder {
        this.target = target
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

    fun build(): ActionableComponentData {
        if (title != null)
            bundle.putString(SettingsRenderActivity.titlePassKey, title)
        return ActionableComponentData(
            target,
            value = if (!bundle.isEmpty && bundle.size() > 0) bundle else null
        )
    }


}