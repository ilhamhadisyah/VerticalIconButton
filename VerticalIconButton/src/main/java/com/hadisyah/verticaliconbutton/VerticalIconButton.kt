package com.hadisyah.verticaliconbutton

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class VerticalIconButton : LinearLayout, View.OnTouchListener {
    private var mContext: Context
    private lateinit var attrs: AttributeSet
    private var styleAttr = 0
    private lateinit var view: View

    /** Core Views */
    private lateinit var cardView: CardView
    private lateinit var buttonText: TextView
    private lateinit var iconImage: ImageView
    private lateinit var alphaLayer: ConstraintLayout


    /** Attributes  */
    private var iconFile: Drawable? = null
    private var textInside: String? = "button"

    private var backgroundColorNormal: Int? = 0
    private var backgroundColorPressed: Int? = 0

    private var iconBackground: Int? = 0
    private var unitTextSize: Float? = 12.0f

    private var strokeColor: Int? = 0
    private var strokeWidth: Float? = 0.0f

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
        iconFile = arr.getDrawable(R.styleable.VerticalIconButton_icon)
        textInside = arr.getString(R.styleable.VerticalIconButton_button_text)
        backgroundColorNormal = arr.getColor(
            R.styleable.VerticalIconButton_button_background_color_normal,
            resources.getColor(R.color.default_background_color)
        )
        backgroundColorPressed = arr.getColor(
            R.styleable.VerticalIconButton_button_background_color_pressed,
            resources.getColor(R.color.default_background_color_pressed)
        )
        iconBackground = arr.getColor(
            R.styleable.VerticalIconButton_icon_background_color, Color.WHITE
        )
        strokeWidth = arr.getDimension(R.styleable.VerticalIconButton_stroke_width, 0.0f)
        strokeColor = arr.getColor(R.styleable.VerticalIconButton_stroke_color, Color.WHITE)
        unitTextSize = arr.getDimension(R.styleable.VerticalIconButton_button_text_size, 12.0f)
        buttonTextColor =
            arr.getResourceId(R.styleable.VerticalIconButton_button_text_color, Color.WHITE)

        cardView = findViewById(R.id.cardView)
        buttonText = findViewById(R.id.button_text)
        iconImage = findViewById(R.id.image_icon)
        alphaLayer = findViewById(R.id.alpha_layer)
        setOnTouchListener(this)

        /** Check if icon added */
        if (iconFile != null) {
            setDrawableIcon(iconFile as Drawable)
            arr.recycle()
        } else {
            cardView.visibility = View.GONE
            val mConstraintSet = ConstraintSet()
            mConstraintSet.clone(alphaLayer)
            mConstraintSet.connect(R.id.button_text,ConstraintSet.TOP,R.id.alpha_layer,ConstraintSet.TOP)
            mConstraintSet.applyTo(alphaLayer)


        }

        /** Check if text added */
        if (textInside.isNullOrEmpty()) {
            setText("Button")
        } else {
            setText(textInside!!)
        }

        /** Check if background color added */
        setBackground(backgroundColorNormal as Int)

        setTextSize(unitTextSize as Float)

        /** Check if icon background added */
        setIconBackground(iconBackground as Int)

        /** Check if textColor added */
        setButtonTextColor(buttonTextColor as Int)

        alphaLayer.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> setBackground(backgroundColorPressed as Int)
                MotionEvent.ACTION_UP -> setBackground(backgroundColorNormal as Int)
            }
            false
        }
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

    fun setIconBackground(color: Int) {
        cardView.setCardBackgroundColor(color)
    }

    fun setTextSize(floatTextSize: Float) {
        buttonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, floatTextSize)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_BUTTON_PRESS -> {
                setBackground(Color.RED)
                return true
            }
        }
        return false
    }
}
