package com.hadisyah.verticaliconbutton

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.card.MaterialCardView

class VerticalIconButton : RelativeLayout {
    private var mContext: Context
    private lateinit var attrs: AttributeSet
    private var styleAttr = 0
    private lateinit var view: View

    /** Core Views */
    private lateinit var rootLayout: MaterialCardView
    private lateinit var buttonText: TextView
    private lateinit var iconImage: ImageView
    private lateinit var alphaLayer: ConstraintLayout


    /** Attributes  */
    private var iconFile: Drawable? = null
    private var textInside: String? = "button"

    private var backgroundColor: Int? = 0

    private var unitTextSize: Float? = 12.0f

    private var strokeColor: Int? = 0
    private var strokeWidth: Int? = 0

    private var iconWeight: Int? = 0
    private var iconMargin: Float? = 0.0f

    @ColorInt
    var buttonTextColor: Int? = 0

    constructor(context: Context) : super(context) {
        this.mContext = context
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context!!
        this.attrs = attrs!!
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context!!
        this.attrs = attrs!!
        this.styleAttr = defStyleAttr
        initView()
    }


    @Suppress("DEPRECATION")
    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        this.view = this
        inflate(mContext, R.layout.button_layout, this)

        val arr: TypedArray =
            mContext.obtainStyledAttributes(attrs, R.styleable.VerticalIconButton, styleAttr, 0)
        iconFile = arr.getDrawable(R.styleable.VerticalIconButton_n_icon)
        textInside = arr.getString(R.styleable.VerticalIconButton_n_button_text)
        backgroundColor = arr.getColor(
            R.styleable.VerticalIconButton_n_button_background_color_normal,
            resources.getColor(R.color.default_background_color)
        )
        strokeWidth = arr.getInteger(R.styleable.VerticalIconButton_n_stroke_width, 0)
        strokeColor = arr.getColor(R.styleable.VerticalIconButton_n_stroke_color, Color.WHITE)
        unitTextSize = arr.getDimension(R.styleable.VerticalIconButton_n_button_text_size, 12.0f)
        buttonTextColor =
            arr.getResourceId(R.styleable.VerticalIconButton_n_button_text_color, Color.WHITE)
        iconWeight = arr.getInteger(R.styleable.VerticalIconButton_n_icon_weight, 90)
        iconMargin = arr.getDimension(R.styleable.VerticalIconButton_n_icon_margin_top, 10.0f)

        buttonText = findViewById(R.id.button_text_default)
        iconImage = findViewById(R.id.image_icon_default)
        alphaLayer = findViewById(R.id.alpha_layer_default)
        rootLayout = findViewById(R.id.root_layout_default)

        /** Check if icon added */
        if (iconFile != null) {
            setDrawableIcon(iconFile as Drawable)
            arr.recycle()
        } else {
            iconImage.visibility = View.GONE
            val mConstraintSet = ConstraintSet()
            mConstraintSet.clone(alphaLayer)
            mConstraintSet.connect(
                R.id.button_text_default,
                ConstraintSet.TOP,
                R.id.alpha_layer_default,
                ConstraintSet.TOP
            )
            mConstraintSet.applyTo(alphaLayer)
        }

        /** Check if text added */
        if (textInside.isNullOrEmpty()) {
            setText("Button")
        } else {
            setText(textInside!!)
        }

        /** Check if background color added */
        setBackground(backgroundColor as Int)

        setTextSize(unitTextSize as Float)

        /** Check if textColor added */
        setButtonTextColor(buttonTextColor as Int)

        /** Check if stroke added */
        setStrokeWeight(strokeWidth as Int)

        /** Check if stroke added */
        if (strokeColor != 0) {
            setStrokeColor(strokeColor as Int)
        }

        /** Check if icon weight added */
        setIconSize(iconWeight as Int)

        /** Check if icon weight added */
        setIconMarginTop(iconMargin as Float)

    }

    fun setStrokeWeight(weight: Int) {
        rootLayout.strokeWidth = weight
    }

    fun setIconMarginTop(margin: Float) {
        val layoutParams: ConstraintLayout.LayoutParams = iconImage.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(0,margin.toInt(),0,0)
        iconImage.layoutParams = layoutParams
    }

    fun setIconSize(weight: Int) {
        iconImage.layoutParams.height = weight
        iconImage.layoutParams.width = weight
        iconImage.requestLayout()
    }

    fun setStrokeColor(color: Int) {
        rootLayout.strokeColor = color
    }

    fun setButtonTextColor(color: Int) {
        buttonText.setTextColor(color)
    }

    fun setDrawableIcon(icon: Drawable) {
        iconImage.setImageDrawable(icon)
    }

    fun setText(text: String) {
        buttonText.text = text
    }

    fun setBackground(color: Int) {
        alphaLayer.setBackgroundColor(color)
    }

    fun setTextSize(floatTextSize: Float) {
        buttonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatTextSize)
    }

}
