package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import no.iktdev.setting.R
import no.iktdev.setting.access.ReactiveSettingDefined
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.databinding.SettingViewSwitchBinding
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.ThemeItem

class SettingViewSwitch(context: Context, attrs: AttributeSet? = null) : SettingViewBase(context, attrs) {

    override var binding: SettingViewSwitchBinding = SettingViewSwitchBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.viewSettingSwitchText, R.styleable.SettingViewSwitch_text)
        applyTextAttr(a, binding.viewSettingSwitchSubText, R.styleable.SettingViewSwitch_subtext)

        applyTextColorAttr(a, binding.viewSettingSwitchText, R.styleable.SettingViewSwitch_textColor)
        applyTextColorAttr(a, binding.viewSettingSwitchSubText, R.styleable.SettingViewSwitch_subTextColor)

        applySrcAttr(a, binding.viewSettingSwitchImage, R.styleable.SettingViewSwitch_src)
        applySrcTintAttr(a, binding.viewSettingSwitchImage, R.styleable.SettingViewSwitch_srcTint)

        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewSwitch_background)

        binding.viewSettingSwitch.isChecked = getBooleanAttrValue(a, R.styleable.SettingViewSwitch_defaultSwitchValue)
        if (binding.viewSettingSwitchSubText.text.isNullOrEmpty())
            binding.viewSettingSwitchSubText.visibility = GONE
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewSwitch)
        onTypedArray(a)
        a.recycle()
    }


    override fun setTheme(theme: ThemeItem) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewSwitch)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        if (base !is SettingComponentDescriptor)
            return
        if (base.icon != null)
            binding.viewSettingSwitchImage.setImageResource(base.icon)
        binding.viewSettingSwitchText.text = base.title
        if (base.description != null) {
            binding.viewSettingSwitchSubText.text = base.description
            binding.viewSettingSwitchSubText.visibility = VISIBLE
        } else binding.viewSettingSwitchSubText.visibility = GONE
    }

    override fun onSettingAssigned(settingDefined: SettingDefined) {
        val isChecked = settingDefined.getBoolean(context)
        binding.viewSettingSwitch.isChecked = isChecked
        settingDefined.reader(context).registerOnSharedPreferenceChangeListener { p0, p1 ->
            binding.viewSettingSwitch.isChecked = settingDefined.getBoolean(context, false)
        }
    }

    override fun setPayload(payload: ComponentData) {

    }

    private val switchValueChanged = CompoundButton.OnCheckedChangeListener { _, value ->
        if (settingDefined is ReactiveSettingDefined) {
            val rsd = settingDefined as ReactiveSettingDefined
            rsd.reactivePayload = value
            rsd.setBoolean(context, value)
        } else {
            settingDefined?.setBoolean(context, value)
        }
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        binding.viewSettingSwitch.setOnCheckedChangeListener(listener)
    }

    init {
        setOnCheckedChangeListener(switchValueChanged)
        binding.root.setOnClickListener { binding.viewSettingSwitch.toggle() }
        binding.viewSettingSwitch.isChecked = settingDefined?.getBoolean(context) ?: false
        if (attrs != null) this.applyAttrs(attrs)
    }

}