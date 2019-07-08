package com.itzcoatl.me.photosearchapp.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.itzcoatl.me.photosearchapp.R
import kotlinx.android.synthetic.main.paginator_layout.view.*

class PaginatorView : LinearLayoutCompat {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.
                        paginator_layout, this, true)
    }

    interface CustomListener {
        fun switchToPage(page: Int)
    }

    // Local variables for calculation of visibility on buttons of PaginationView
    val maxPages: Int = 3
    var page: Int = 0
    var pages: Int = 0
    var customListener: CustomListener? = null

    fun setData(page: Int, pages: Int) {
        this.page = page
        this.pages = pages

        setLeftPaginators()
        setPaginators()
        setRightPaginators()
    }

    private fun setLeftPaginators() {
        if (page <= maxPages) {
            previous_paginator.visibility = View.INVISIBLE
        } else {
            previous_paginator.visibility = View.VISIBLE
            previous_paginator.setOnClickListener {
                customListener?.switchToPage(page - 1)
            }
        }
        if (page <= 2 * maxPages) {
            first_paginator.visibility = View.INVISIBLE
        } else {
            first_paginator.visibility = View.VISIBLE
            first_paginator.setOnClickListener {
                customListener?.switchToPage(1)
            }
        }
    }

    private fun setPaginators() {
        var index: Int = (page -1) / 3
        // We need to have the Int cast precision lost
        index *= 3
        setUpPageButton(++index, left_page, "" + index)
        setUpPageButton(++index, midle_page, "" + index)
        setUpPageButton(++index, right_page, "" + index)
    }

    private fun setRightPaginators() {
        // maxCalculatedPages is the pages' previous (n % maxPages == 0) number
        var maxCalculatedPages = pages - 1
        maxCalculatedPages -= (maxCalculatedPages % maxPages)
        if (page > maxCalculatedPages) {
            next_paginator.visibility = View.INVISIBLE
        } else {
            next_paginator.visibility = View.VISIBLE
            next_paginator.setOnClickListener {
                customListener?.switchToPage(page + 1)
            }
        }
        if (page > maxCalculatedPages - maxPages) {
            last_paginator.visibility = View.INVISIBLE
        } else {
            last_paginator.visibility = View.VISIBLE
            last_paginator.setOnClickListener {
                customListener?.switchToPage(pages)
            }
        }
    }

    private fun setUpPageButton(index: Int, view: TextView, text: String) {
        view.text = text
        view.visibility = View.VISIBLE
        when {
            index > pages -> view.visibility = View.INVISIBLE
            page == index -> view.setBackgroundResource(R.color.colorPaginatorGray)
            else -> view.setBackgroundResource(R.color.colorPaginatorGreyLight)
        }
        view.setOnClickListener {
            customListener?.switchToPage(index)
        }
    }

}
