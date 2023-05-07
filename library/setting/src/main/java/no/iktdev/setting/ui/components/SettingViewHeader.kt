package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import no.iktdev.setting.R
import no.iktdev.setting.databinding.SettingViewHeaderBinding
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.ThemeItem

class SettingViewHeader(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    SettingViewBase(context, attrs) {

    override var binding: SettingViewHeaderBinding = SettingViewHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.header, R.styleable.SettingViewHeader_title)
        applyTextColorAttr(a, binding.header, R.styleable.SettingViewHeader_textColor)
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewHeader)
        onTypedArray(a)
        a.recycle()
    }


    override fun setTheme(theme: ThemeItem) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewHeader)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {

    }


    override fun setPayload(payload: ComponentData) {
    }

    init {
        if (attrs != null) this.applyAttrs(attrs)
    }


}