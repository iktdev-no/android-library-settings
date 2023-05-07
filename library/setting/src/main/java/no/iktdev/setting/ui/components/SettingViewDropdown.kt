package no.iktdev.setting.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.appcompat.view.ContextThemeWrapper
import no.iktdev.setting.R
import no.iktdev.setting.access.ReactiveSettingDefined
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.databinding.AdapterSingleTextviewBinding
import no.iktdev.setting.databinding.SettingViewDropdownBinding
import no.iktdev.setting.model.*
import java.io.Serializable

class SettingViewDropdown(context: Context, attrs: AttributeSet? = null) :
    SettingViewBase(context, attrs) {


    override val binding: SettingViewDropdownBinding = SettingViewDropdownBinding.inflate(
        LayoutInflater.from(context), this, true)

    override fun onTypedArray(a: TypedArray) {
        applyTextAttr(a, binding.title, R.styleable.SettingViewDropdown_text)
        applyTextColorAttr(a, binding.title, R.styleable.SettingViewDropdown_textColor)
        applyBackgroundAttr(a, binding.root, R.styleable.SettingViewDropdown_background)
        binding.dropdown.setPopupBackgroundResource(a.getResourceId(R.styleable.SettingViewDropdown_spinnerBackground, defaultColor))
    }

    override fun applyAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingViewDropdown)
        onTypedArray(a)
        a.recycle()
    }


    private var adapter: DropdownAdapter? = null

    override fun setTheme(theme: ThemeItem) {
        val attr = ContextThemeWrapper(context, theme.theme).theme.obtainStyledAttributes(R.styleable.SettingViewDropdown)
        onTypedArray(attr)
        attr.recycle()
    }

    override fun setDescriptorValues(base: SettingComponentDescriptorBase) {
        if (base !is SettingComponentDescriptor)
            return
        binding.title.text = base.title
    }

    override fun onSettingAssigned(setting: SettingDefined) {
        val value: Any? = setting.getSettings(context)?.get(setting.key)
        val item = adapter?.items?.find { it.value == value }
        if (item != null) {
            val index = adapter?.items?.indexOf(item) ?: 0
            binding.dropdown.setSelection(index, false)
        }
        else {
            binding.dropdown.setSelection(0)
        }
        binding.dropdown.onItemSelectedListener = selectionChange
    }

    override fun setPayload(payload: ComponentData) {
        if (payload.value !is List<*> || !(payload.value as List<*>).all { it is DropdownItem }) {
            throw SettingComponentInvalidValueException("Payload does not contain " + DropdownItem::class.java.simpleName)
        }
        binding.dropdown.onItemSelectedListener = null // Resetting in order to prevent notification on drawing
        if (adapter == null) {
            adapter = DropdownAdapter(context, payload.value as List<DropdownItem>)
            binding.dropdown.adapter = adapter
        } else {
            adapter?.items = payload.value as List<DropdownItem>
            adapter?.notifyDataSetChanged()
        }
    }

    val selectionChange = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = adapter?.items?.get(position)
            setting?.let { setting ->
                val payload = if (item?.payload != null) item.payload else if (item?.value is Serializable) item.value else null
                if (setting is ReactiveSettingDefined && payload != null && payload is Serializable) {
                    (setting as ReactiveSettingDefined).setPayload(payload)
                }

                when (item?.value) {
                    is String -> setting.setString(context, item.value as String)
                    is Int -> setting.setInt(context, item.value as Int)
                    is Float -> setting.setFloat(context, item.value as Float)
                    is Boolean -> setting.setBoolean(context, item.value as Boolean)
                    else -> Log.e(this::class.simpleName, "Unsupported item provided!")
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

    class DropdownAdapter(val context: Context, var items: List<DropdownItem>): BaseAdapter() {
        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(p0: Int): Any {
            return items[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            var holder = view
            var binding: AdapterSingleTextviewBinding?
            if (holder == null) {
                binding = AdapterSingleTextviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                holder = binding.root
            } else {
                binding = holder.tag as AdapterSingleTextviewBinding
            }
            holder.tag = binding
            
            val item = items[position]
            binding.singleTextView.text = item.displayValue
            return holder
        }

    }

    init {
        if (attrs != null) this.applyAttrs(attrs)
    }

}