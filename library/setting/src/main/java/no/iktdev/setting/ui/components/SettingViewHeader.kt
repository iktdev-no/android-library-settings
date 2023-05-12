package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import no.iktdev.setting.R
import no.iktdev.setting.databinding.SettingViewHeaderBinding
import no.iktdev.setting.ui.Theming

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


    override fun setTheme(theme: Theming) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewHeader)
        onTypedArray(attr)
        attr.recycle()
    }


    init {
        if (attrs != null) this.applyAttrs(attrs)
    }


}