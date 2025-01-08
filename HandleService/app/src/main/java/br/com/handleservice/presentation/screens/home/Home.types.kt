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
        ServicesCategoriesType("Eletricista", R.drawable.electrical_service, "eletricista"),
        ServicesCategoriesType("Professor", R.drawable.teacher, "professor"),
        ServicesCategoriesType("Desenvolvedor", R.drawable.developer, "desenvolvedor"),
        ServicesCategoriesType("Personal Trainer", R.drawable.healthcheck, "personal_trainer"),
        ServicesCategoriesType("Veterinário", R.drawable.paw, "veterinario"),
        ServicesCategoriesType("Fotógrafo", R.drawable.color_patern, "fotografo"),
        ServicesCategoriesType("Diarista", R.drawable.day_laborer, "diarista"),
        ServicesCategoriesType("Contador", R.drawable.safe_pig, "contador"),
        ServicesCategoriesType("Cabeleireiro", R.drawable.hairdresser, "cabeleireiro"),
    )
}
