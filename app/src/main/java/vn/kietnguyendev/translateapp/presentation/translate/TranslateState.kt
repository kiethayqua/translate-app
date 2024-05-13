package vn.kietnguyendev.translateapp.presentation.translate

import javax.annotation.concurrent.Immutable

@Immutable
data class TranslateState(
    val from: String = "",
    val to: String = "",
    val fromText: String = "",
    val toText: String = ""
)
