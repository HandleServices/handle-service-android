package br.com.handleservice.presentation.screens.home

import br.com.handleservice.R
import br.com.handleservice.ui.components.searchbar.SearchableClass

data class ServicesCategoriesType (
    val name: String,
    val iconResId: Int,
    val url: String
) : SearchableClass {
    override fun doesMatchSearchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

object ServicesCategories {
    val categories = listOf(
        ServicesCategoriesType("Eletricista", R.drawable.electrical_service, "elet"),
        ServicesCategoriesType("Professor", R.drawable.teacher, "elet"),
        ServicesCategoriesType("Desenvolvedor", R.drawable.developer, "elet"),
        ServicesCategoriesType("Personal Trainer", R.drawable.healthcheck, "elet"),
        ServicesCategoriesType("Veterinário", R.drawable.paw, "elet"),
        ServicesCategoriesType("Fotógrafo", R.drawable.color_patern, "elet"),
        ServicesCategoriesType("Diarista", R.drawable.day_laborer, "elet"),
        ServicesCategoriesType("Contador", R.drawable.safe_pig, "elet"),
        ServicesCategoriesType("Cabeleireiro", R.drawable.hairdresser, "elet"),
    )
}
