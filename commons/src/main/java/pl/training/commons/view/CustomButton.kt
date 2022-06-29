package pl.training.commons.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import pl.training.commons.R
import pl.training.commons.databinding.ViewCustomButtonBinding
import kotlin.math.roundToInt

class CustomButton(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {

    private val binding = ViewCustomButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private val animators = mutableListOf<ValueAnimator>()

    init {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val padding = dpToPx(16)
        setPadding(padding, 0, padding, padding)
    }

    fun disable(onDisabled: () -> Unit = {}) {
        binding.button.apply {
            icon = null
            text = ""
            isClickable = false
            isFocusable = false
            shrinkButton(onDisabled)
        }
        binding.progressBar.isVisible = true
    }

    private fun shrinkButton(onDisabled: () -> Unit) {
        val button = binding.button
        val width = button.measuredWidth
        val height = button.measuredHeight

        val widthAnimator = ValueAnimator.ofInt(width, height).apply {
            duration = 800
            addUpdateListener {
                button.updateLayoutParams { this.width = it.animatedValue as Int }
            }
        }

        val animatorsList = listOf(widthAnimator)
        animators.addAll(animatorsList)
        animatorsList.forEach { it.start() }
    }

    fun enable() {
        binding.button.apply {
            extend()
            icon = getDrawable(context, R.drawable.ic_cloud_sun_rain)
            text = "Check"
            isClickable = true
            isFocusable = true
        }
        binding.progressBar.isVisible = false
    }

    fun setOnClickListener()

    private fun dpToPx(dp: Int) = (resources.displayMetrics.density * dp).roundToInt()

}