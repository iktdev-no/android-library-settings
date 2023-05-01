package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import no.iktdev.setting.R
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.databinding.SettingViewSliderBinding
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.ThemeItem

class SettingViewSlider(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    SettingViewBase(context, attrs, defStyleAttr) {

    override var binding = SettingViewSliderBinding.inflate(LayoutInflater.from(context), this, true)


    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.title, R.styleable.SettingViewSlider_text)
        applyTextAttr(a, binding.value, R.styleable.SettingViewSlider_current)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.seekbar.min = getIntAttrValue(a, R.styleable.SettingViewSlider_min, 0)
        }
        binding.seekbar.max = getIntAttrValue(a, R.styleable.SettingViewSlider_max, 100)
        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewSlider_background)
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewSlider)
        onTypedArray(a)
        a.recycle()
    }

    override fun setTheme(theme: ThemeItem) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewSlider)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        if (base !is SettingComponentDescriptor)
            return
        binding.title.text = base.title
    }

    override fun onSettingAssigned(settingDefined: SettingDefined) {
        binding.seekbar.progress = settingDefined.getInt(context)
        binding.value.text = binding.seekbar.progress.toString()
    }

    override fun setPayload(payload: ComponentData) {

    }

    var seekBarChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                settingDefined?.setInt(context, progress)
            }
            binding.value.text = progress.toString()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    init {
        binding.seekbar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekbar.progress = settingDefined?.getInt(context) ?: 0
        if (attrs != null) this.applyAttrs(attrs)

    }


}