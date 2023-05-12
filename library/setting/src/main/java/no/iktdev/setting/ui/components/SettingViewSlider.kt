package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import no.iktdev.setting.R
import no.iktdev.setting.access.SettingAccess
import no.iktdev.setting.databinding.SettingViewSliderBinding
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.ui.Theming

class SettingViewSlider(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    SettingViewBase(context, attrs, defStyleAttr) {

    override var binding = SettingViewSliderBinding.inflate(LayoutInflater.from(context), this, true)


    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.title, R.styleable.SettingViewSlider_text)
        applyTextAttr(a, binding.value, R.styleable.SettingViewSlider_current)

        applySrcAttr(a, binding.icon, R.styleable.SettingViewSlider_src)
        applySrcTintAttr(a, binding.icon, R.styleable.SettingViewSlider_srcTint)
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

    override fun setTheme(theme: Theming) {
        val attr = context.obtainStyledAttributes(theme.theme, R.styleable.SettingViewSlider)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        binding.title.text = base.title
    }

    override fun onSettingAssigned(setting: SettingAccess) {
        binding.seekbar.progress = setting.getInt(context, 0)
        binding.value.text = binding.seekbar.progress.toString()
    }


    var seekBarChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                setting?.setInt(context, progress)
            }
            binding.value.text = progress.toString()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    init {
        binding.seekbar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.seekbar.progress = setting?.getInt(context) ?: 0
        if (attrs != null) this.applyAttrs(attrs)

    }


}