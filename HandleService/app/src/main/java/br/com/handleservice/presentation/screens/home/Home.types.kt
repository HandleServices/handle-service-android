package br.com.handleservice.presentation.screens.home

import br.com.handleservice.R
import br.com.handleservice.ui.components.searchbar.SearchableClass

data class JobsCategoriesType (
    val name: String,
    val iconResId: Int,
    val url: String
) : SearchableClass {
    override fun doesMatchSearchQuery(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }
}

object JobsCategories {
    val categories = listOf(
        JobsCategoriesType("Eletricista", R.drawable.electrical_service, "elet"),
        JobsCategoriesType("Professor", R.drawable.teacher, "elet"),
        JobsCategoriesType("Desenvolvedor", R.drawable.developer, "elet"),
        JobsCategoriesType("Personal Trainer", R.drawable.healthcheck, "elet"),
        JobsCategoriesType("Veterinário", R.drawable.paw, "elet"),
        JobsCategoriesType("Fotógrafo", R.drawable.color_patern, "elet"),
        JobsCategoriesType("Diarista", R.drawable.day_laborer, "elet"),
        JobsCategoriesType("Contador", R.drawable.safe_pig, "elet"),
        JobsCategoriesType("Cabeleireiro", R.drawable.hairdresser, "elet"),
    )
}
