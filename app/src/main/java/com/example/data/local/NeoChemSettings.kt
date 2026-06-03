package com.example.data.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NeoChemSettings {
    private const val PREFS_NAME = "neochem_settings_prefs"
    private const val KEY_LOW_PERFORMANCE_MODE = "low_performance_mode"
    private const val KEY_SOUND_ENABLED = "sound_enabled"
    private const val KEY_THEME_MODE = "theme_mode"

    private val _lowPerformanceMode = MutableStateFlow(false)
    val lowPerformanceMode: StateFlow<Boolean> = _lowPerformanceMode

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled

    private val _themeMode = MutableStateFlow(0) // 0: Light (Default), 1: Dark, 2: Follow System
    val themeMode: StateFlow<Int> = _themeMode

    private var initialized = false

    fun init(context: Context) {
        if (initialized) return
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _lowPerformanceMode.value = prefs.getBoolean(KEY_LOW_PERFORMANCE_MODE, false)
        _soundEnabled.value = prefs.getBoolean(KEY_SOUND_ENABLED, true)
        _themeMode.value = prefs.getInt(KEY_THEME_MODE, 0)
        
        // Force Indonesian locale to guarantee Bahasa Indonesia on all devices
        try {
            val locale = java.util.Locale("in")
            java.util.Locale.setDefault(locale)
            val resources = context.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        initialized = true
    }

    fun setLowPerformanceMode(context: Context, enabled: Boolean) {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_LOW_PERFORMANCE_MODE, enabled).apply()
        _lowPerformanceMode.value = enabled
    }

    fun setSoundEnabled(context: Context, enabled: Boolean) {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
        _soundEnabled.value = enabled
    }

    fun setThemeMode(context: Context, mode: Int) {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_THEME_MODE, mode).apply()
        _themeMode.value = mode
    }
}
