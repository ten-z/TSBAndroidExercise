package co.nz.tsb.interview.bankrecmatchmaker.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchItem
import co.nz.tsb.interview.bankrecmatchmaker.data.MatchRepository
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class FakeMatchRepository : MatchRepository() {
    var fakeItems: List<MatchItem> = listOf()

    override fun getMatchItems(): List<MatchItem> {
        return fakeItems
    }
}

class FindMatchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FindMatchViewModel
    private lateinit var fakeRepository: FakeMatchRepository

    @Before
    fun setUp() {
        fakeRepository = FakeMatchRepository()
        viewModel = FindMatchViewModel(fakeRepository)
    }

    @Test
    fun testSetTargetMatchValue() {
        val target = BigDecimal("100.00")
        viewModel.setTargetMatchValue(target)

        assertEquals(target, viewModel.targetMatchValue.value)
    }

    @Test
    fun testAddSelectedItem() {
        viewModel.setTargetMatchValue(BigDecimal("1200.00"))

        val item = MatchItem(
            "Test",
            "30 Aug",
            BigDecimal("1000.00"),
            "Sales Invoice",
            "100"
        )
        viewModel.addSelectedItem(item)

        val selectedItems = viewModel.selectedItems.value
        assertTrue(selectedItems?.contains(item) ?: false)

        val expectedTarget = BigDecimal("200.00")
        assertEquals(expectedTarget, viewModel.targetMatchValue.value)
    }

    @Test
    fun testRemoveSelectedItem() {
        viewModel.setTargetMatchValue(BigDecimal("1200.00"))

        val item = MatchItem(
            "Test",
            "30 Aug",
            BigDecimal("1000.00"),
            "Sales Invoice",
            "101"
        )
        viewModel.addSelectedItem(item)

        val addSelectedItems = viewModel.selectedItems.value
        assertTrue(addSelectedItems?.contains(item) ?: false)

        val addExpectedTarget = BigDecimal("200.00")
        assertEquals(addExpectedTarget, viewModel.targetMatchValue.value)

        viewModel.removeSelectedItem(item)

        val selectedItems = viewModel.selectedItems.value
        assertFalse(selectedItems?.contains(item) ?: true)

        val expectedTarget = BigDecimal("1200.00")
        assertEquals(expectedTarget, viewModel.targetMatchValue.value)
    }

    @Test
    fun testLoadMatchItems() {
        viewModel.setTargetMatchValue(BigDecimal("1200.00"))

        val item1 = MatchItem("Test1", "30 Aug", BigDecimal("100.00"), "Sales Invoice", "200")
        val item2 = MatchItem("Test2", "6 Aug", BigDecimal("1200.00"), "Sales Invoice", "201")
        val item3 = MatchItem("Test3", "1 Aug", BigDecimal("2100.00"), "Sales Invoice", "202")

        fakeRepository.fakeItems = listOf(item1, item2, item3)

        viewModel.loadMatchItems()

        val selectedItems = viewModel.selectedItems.value
        assertTrue(selectedItems?.contains(item2) ?: false)

        assertTrue(BigDecimal.ZERO.compareTo(viewModel.targetMatchValue.value) == 0)

    }


}