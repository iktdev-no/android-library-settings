package no.iktdev.setting.factory

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import no.iktdev.setting.Assist
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.SettingComponentType
import no.iktdev.setting.ui.ThemeType
import no.iktdev.setting.ui.Theming
import no.iktdev.setting.ui.components.*

class ComponentFactory(private val context: Context, val items: List<SettingComponentDescriptorBase>, val themes: List<Theming> = emptyList()) {



    fun generate(header: Boolean = true): List<View> {
        val title = SettingViewHeader(context)
        val titleId = items.first().groupName
        title.binding.header.setText(titleId)

        val mapped = items.mapNotNull {
            val view = when (it.type) {
                SettingComponentType.CLICKABLE -> SettingViewClickable(context)
                SettingComponentType.LAUNCH -> SettingViewLaunch(context)
                SettingComponentType.SWITCH -> SettingViewSwitch(context)
                SettingComponentType.DROPDOWN -> SettingViewDropdown(context)
                SettingComponentType.SLIDER -> SettingViewSlider(context)
                SettingComponentType.POPOUT_SELECT -> SettingViewPopoutSelect(context)
                else -> null
            }
            if (view != null && it is SettingComponentDescriptor && it.payload != null)
                view.setPayload(it.payload)
            val theme = viewPlacement(it)
            if (view != null && theme != null)
                view.setTheme(theme)
            view?.setDescriptorValues(it)
            setOffset(items.indexOf(it), view)
            if (it is SettingComponentDescriptor) {
                it.setting?.let { setting ->
                    view?.setSetting(setting)
                }
            }
            view
        }
        return if (header)
            listOf(title) + mapped
        else
            mapped
    }

    private fun setOffset(position: Int, view: SettingViewBase?) {
        if (view == null)
            return
        if (isLast(position)) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, Assist.getDeviceDp(context, 1), 0, Assist.getDeviceDp(context,24))
            view.layoutParams = params
        } else {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, Assist.getDeviceDp(context,1), 0, 0)
            view.layoutParams = params
        }
    }

    protected fun viewPlacement(item: SettingComponentDescriptorBase): Theming? {
        return when {
            items.size == 1 -> themes.find { it.themeType == ThemeType.SINGLE }
            isFirst(items.indexOf(item)) -> themes.find { it.themeType == ThemeType.START }
            isLast(items.indexOf(item)) -> themes.find { it.themeType == ThemeType.END }
            else -> themes.find { it.themeType == ThemeType.NORMAL }
        }
    }

    protected fun isFirst(position: Int): Boolean { return position == 0 }
    protected fun isLast(position: Int): Boolean { return position == items.size - 1 }
}