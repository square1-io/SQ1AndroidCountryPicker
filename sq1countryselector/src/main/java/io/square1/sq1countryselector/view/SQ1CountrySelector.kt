package io.square1.sq1countryselector.view

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.square1.sq1countryselector.R
import io.square1.sq1countryselector.SQ1CountrySelectorUtil
import io.square1.sq1countryselector.model.CountryModel
import kotlinx.android.synthetic.main.sq1_country_selector.view.*
import java.util.*

class SQ1CountrySelector(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val countryUtils = SQ1CountrySelectorUtil(context)
    private var countryList = ArrayList<CountryModel>()
    private var filteredCountries = ArrayList<CountryModel>()
    private var listener: ItemClickListener? = null

    private var mShowFlag: Boolean = false
    private var roundedFlag: Boolean = false
    private var mFlagPosition: Int = 0
    private var itemTextSize: Float = 0.toFloat()
    private lateinit var tvCountryName : TextView

    fun setListener(listener: ItemClickListener) {
        this.listener = listener
    }

    init {
        inflate(context, R.layout.sq1_country_selector, this)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SQ1CountrySelector,
            0, 0
        ).apply {
            try {

                countryList = countryUtils.getAllCountries(context)
                mShowFlag = getBoolean(R.styleable.SQ1CountrySelector_showFlag, false)
                roundedFlag = getBoolean(R.styleable.SQ1CountrySelector_roundedFlag, false)
                val filter = getBoolean(R.styleable.SQ1CountrySelector_filter, false)
                when (filter) {
                    true -> cvCountrySearchSv.visibility = View.VISIBLE
                    else -> cvCountrySearchSv.visibility = View.GONE
                }
                val mHint = getString(R.styleable.SQ1CountrySelector_hint)
                mFlagPosition = getInteger(R.styleable.SQ1CountrySelector_flagPosition, 0)
                itemTextSize = getDimension(
                    R.styleable.SQ1CountrySelector_itemTextSize,
                    resources.getDimension(R.dimen.default_item_text_sizd)
                )
                mHint?.let { svCountrySearch.queryHint = it }
                setQueryListener()
                setupCountriesList()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun itemClick(country: CountryModel) {
        this.listener?.onClick(country)
    }


    private fun setQueryListener() {
        svCountrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if (newText.isNotBlank()) {
                        filterPlaces(newText)
                    } else {
                        filteredCountries.addAll(countryList)
                        rvCountrySelector.adapter?.notifyDataSetChanged()
                    }
                }, 1200)
                return true
            }
        })
    }

    private fun filterPlaces(newText: String) {
        filteredCountries.clear()
        for (country in countryList) {
            when {
                country.name.toLowerCase().contains(newText.toLowerCase()) -> filteredCountries.add(country)
            }
        }
        rvCountrySelector.adapter?.notifyDataSetChanged()
    }

    private fun setupCountriesList() {
        filteredCountries.addAll(countryList)
        val adapter = SQ1CountrySelectorAdapter(
            context,
            filteredCountries,
            isShowFlag(),
            flagPostion(),
            itemTextSize,
            roundedFlag
        )
        adapter.setItemClickListener(object :
            SQ1CountrySelectorAdapter.ItemClickListener {
            override fun onClick(country: CountryModel) {
                itemClick(country)
            }
        })
        val rvCountrySelector = findViewById<RecyclerView>(R.id.rvCountrySelector)
        rvCountrySelector.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rvCountrySelector.layoutManager = LinearLayoutManager(context)
        rvCountrySelector.adapter = adapter
    }

    interface ItemClickListener {
        fun onClick(country: CountryModel)
    }

    fun isShowFlag(): Boolean {
        return mShowFlag
    }

    fun flagPostion(): Int {
        return mFlagPosition
    }
}
