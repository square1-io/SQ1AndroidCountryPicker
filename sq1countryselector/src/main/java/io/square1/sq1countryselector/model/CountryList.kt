package io.square1.sq1countryselector.model

data class CountryList(val countries: ArrayList<CountryModel>)

data class CountryModel(var name: String, val alpha2Code: String, val callingCodes: ArrayList<String>)
