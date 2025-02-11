package co.nz.tsb.interview.bankrecmatchmaker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchItem
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchRepository
import java.math.BigDecimal

class FindMatchViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _targetMatchValue = MutableLiveData<BigDecimal>()
    private val _matchItems = MutableLiveData<List<MatchItem>>()
    private val _selectedItems = MutableLiveData<MutableList<MatchItem>>(mutableListOf())

    val targetMatchValue: LiveData<BigDecimal> get() = _targetMatchValue
    val matchItems: LiveData<List<MatchItem>> get() = _matchItems
    val selectedItems: LiveData<MutableList<MatchItem>> get() = _selectedItems

    fun setTargetMatchValue(value: BigDecimal) {
        _targetMatchValue.value = value
    }

    fun loadMatchItems() {
        val items = matchRepository.getMatchItems()
        _matchItems.value = items
        selectExactMatch(items)
    }

    private fun selectExactMatch(items: List<MatchItem>) {
        val target = _targetMatchValue.value ?: return
        for (item in items) {
            if (item.total.compareTo(target) == 0) {
                addSelectedItem(item)
                break
            }
        }
    }

    fun addSelectedItem(item: MatchItem) {
        val current = _selectedItems.value?: mutableListOf()
        current.add(item)
        _selectedItems.value = current
        updateTargetTotal(item, true)
    }

    fun removeSelectedItem(item: MatchItem) {
        val current = _selectedItems.value ?: mutableListOf()
        current.remove(item)
        _selectedItems.value = current
        updateTargetTotal(item, false)
    }

    private fun updateTargetTotal(item: MatchItem, isAdded: Boolean) {
        val remainingTotal = calculateRemainingValue(
            _targetMatchValue.value ?: BigDecimal.ZERO,
            item, isAdded
        )
        _targetMatchValue.value = remainingTotal
    }

    private fun calculateRemainingValue(
        target: BigDecimal,
        selectedItems: MatchItem,
        isAdded: Boolean
    ): BigDecimal {
        return if (isAdded) target.subtract(selectedItems.total) else target.add(selectedItems.total)
    }

}