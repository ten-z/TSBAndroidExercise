package co.nz.tsb.interview.bankrecmatchmaker.data

import java.math.BigDecimal

open class MatchRepository {

    open fun getMatchItems(): List<MatchItem> {
        return listOf(
            MatchItem(
                "City Limousines",
                "30 Aug",
                BigDecimal("249.00"),
                "Sales Invoice",
                "0"
            ),
            MatchItem(
                "Ridgeway University",
                "12 Sep",
                BigDecimal("618.50"),
                "Sales Invoice",
                "1"
            ),
            MatchItem(
                "Cube Land",
                "22 Sep",
                BigDecimal("495.00"),
                "Sales Invoice",
                "2"
            ),
            MatchItem(
                "Bayside Club",
                "23 Sep",
                BigDecimal("234.00"),
                "Sales Invoice",
                "3"
            ),
            MatchItem(
                "SMART Agency",
                "12 Sep",
                BigDecimal("250.00"),
                "Sales Invoice",
                "4"
            ),
            MatchItem(
                "PowerDirect",
                "11 Sep",
                BigDecimal("108.60"),
                "Sales Invoice",
                "5"
            ),
            MatchItem(
                "PC Complete",
                "17 Sep",
                BigDecimal("216.99"),
                "Sales Invoice",
                "6"
            ),
            MatchItem(
                "Truxton Properties",
                "17 Sep",
                BigDecimal("181.25"),
                "Sales Invoice",
                "7"
            ),
            MatchItem(
                "MCO Cleaning Services",
                "17 Sep",
                BigDecimal("170.50"),
                "Sales Invoice",
                "8"
            ),
            MatchItem(
                "Gateway Motors",
                "18 Sep",
                BigDecimal("411.35"),
                "Sales Invoice",
                "9"
            )
        )
    }

}