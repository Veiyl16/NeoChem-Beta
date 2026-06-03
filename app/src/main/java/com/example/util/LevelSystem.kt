package com.example.util

object LevelSystem {
    /**
     * Cumulative XP required to start level `level`.
     * Range values based on:
     * - Level 1: 0 - 99 XP (size = 100)
     * - Level 2: 100 - 249 XP (size = 150)
     * - Level 3: 250 - 449 XP (size = 200)
     * - Level 4: 450 - 699 XP (size = 250)
     * CumulativeXP(L) = 25 * (L - 1) * (L + 2)
     */
    fun getCumulativeXpForLevel(level: Int): Int {
        if (level <= 1) return 0
        return 25 * (level - 1) * (level + 2)
    }

    /**
     * Calculates the level on-the-fly from total lifetime XP.
     */
    fun calculateLevel(totalXp: Int): Int {
        if (totalXp <= 0) return 1
        var level = 1
        while (totalXp >= getCumulativeXpForLevel(level + 1)) {
            level++
        }
        return level
    }

    /**
     * Calculates user's progress XP within their current active level.
     * This represents the current relative progress of the current level.
     */
    fun calculateCurrentXp(totalXp: Int): Int {
        val lvl = calculateLevel(totalXp)
        val startXp = getCumulativeXpForLevel(lvl)
        return (totalXp - startXp).coerceAtLeast(0)
    }

    /**
     * Total XP span required to finish the given level and progress to level `level + 1`.
     */
    fun calculateRequiredXp(level: Int): Int {
        return 50 * (level + 1)
    }
}
