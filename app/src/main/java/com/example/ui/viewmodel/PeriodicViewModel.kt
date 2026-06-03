package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.NeoChemApplication
import com.example.data.dummy.PeriodicTableData
import com.example.data.model.ChemicalElement
import com.example.data.model.ElementFavorite
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PeriodicViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = (application as NeoChemApplication).repository

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    val favoriteElements: StateFlow<List<ElementFavorite>> = userRepository.favoriteElements
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Derived list of elements based on search query, filter category, and bookmarked states
    private val _displayedElements = MutableStateFlow(PeriodicTableData.elements)
    
    val filteredElements: StateFlow<List<ChemicalElement>> = combine(
        _searchQuery,
        _selectedCategory,
        favoriteElements
    ) { query, category, favorites ->
        var list = PeriodicTableData.elements
        
        if (query.isNotEmpty()) {
            list = list.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.symbol.contains(query, ignoreCase = true) ||
                it.number.toString() == query
            }
        }
        
        if (category != null) {
            if (category == "Favorit") {
                val favIds = favorites.map { it.atomicNumber }
                list = list.filter { it.number in favIds }
            } else {
                list = list.filter { it.category.lowercase() == category.lowercase() }
            }
        }
        
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PeriodicTableData.elements
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun toggleFavorite(elementNumber: Int) {
        viewModelScope.launch {
            userRepository.toggleFavoriteElement(elementNumber)
        }
    }

    fun isElementFavorite(number: Int): Boolean {
        return favoriteElements.value.any { it.atomicNumber == number }
    }
}
