package pl.training.commons.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import pl.training.commons.R
import pl.training.commons.databinding.ViewLabeledImageBinding
import pl.training.commons.databinding.ViewLabeledImageBinding.inflate

class LabeledImage(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {

    private val binding: ViewLabeledImageBinding = inflate(LayoutInflater.from(context), this, true)

    init {
        val settings = context.obtainStyledAttributes(attributeSet, R.styleable.LabeledImage)
        binding.image.setImageDrawable(settings.getDrawable(R.styleable.LabeledImage_image))
        binding.label.text = settings.getString(R.styleable.LabeledImage_label)
        settings.recycle()
    }

    fun setOnClickListener(handler: () -> Unit) {
        binding.image.setOnClickListener { handler() }
    }

}