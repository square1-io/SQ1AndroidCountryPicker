package io.square1.sq1countryselector.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.square1.sq1countryselector.R
import io.square1.sq1countryselector.model.CountryModel
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.util.*


class SQ1CountrySelectorAdapter(
    val context: Context,
    val countryList: ArrayList<CountryModel>,
    var showFlag: Boolean,
    var flagPostion: Int,
    val itemTextSize: Float,
    val roundedFlag: Boolean
) :
    RecyclerView.Adapter<SQ1CountrySelectorAdapter.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
//        return when (flagPostion) {
//            1 -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.country_list_item_right_flag, parent, false))
//            else -> ViewHolder(
//                LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false)
//            )
//        }
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun dpToPx(dip: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
        return px.toInt()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flagParams = RelativeLayout.LayoutParams(dpToPx(32.toFloat()), dpToPx(32.toFloat()))
        val textParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        when (flagPostion) {
            1 -> {
                flagParams.addRule(RelativeLayout.ALIGN_PARENT_END)
                flagParams.addRule(RelativeLayout.CENTER_VERTICAL)
                textParams.addRule(RelativeLayout.START_OF, holder.ivCountryFlag.id)
            }
            else -> {
                flagParams.addRule(RelativeLayout.ALIGN_PARENT_START)
                flagParams.addRule(RelativeLayout.CENTER_VERTICAL)
                textParams.addRule(RelativeLayout.END_OF, holder.ivCountryFlag.id)
            }
        }
        // Setting params to views
        holder.ivCountryFlag.layoutParams = flagParams
        holder.tvCountryName.layoutParams = textParams
        holder.tvCountryName.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemTextSize)
        holder.itemView.setOnClickListener { v ->
            itemClickListener.onClick(countryList[position])
        }
        holder.tvCountryName.text = getCountryName(countryList[position].alpha2Code)
        when {
            showFlag ->
                loadFlag(holder.ivCountryFlag, countryList[position], roundedFlag)
            else -> holder.ivCountryFlag.visibility = View.GONE
        }
    }

    private fun loadFlag(ivCountryFlag: ImageView, countryModel: CountryModel, rounded: Boolean) {
        when (rounded) {
            true -> Glide.with(context).load(getFlag(countryModel)).apply(RequestOptions.circleCropTransform()).into(
                ivCountryFlag
            )
            false -> Glide.with(context).load(getFlag(countryModel)).into(ivCountryFlag)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountryName: TextView = itemView.tvCountryName
        val ivCountryFlag: ImageView = itemView.ivCountryFlag
    }

    private fun getFlag(country: CountryModel): Int {
        return try {
            context.resources.getIdentifier("flag_" + country.alpha2Code.toLowerCase(), "drawable", context.packageName)
        } catch (e: Exception) {
            context.resources.getIdentifier("flag_es", "drawable", context.packageName)
        }
    }

    private fun getCountryName(code: String): String {
        val loc = Locale("", code)
        return loc.displayCountry
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onClick(country: CountryModel)
    }

}