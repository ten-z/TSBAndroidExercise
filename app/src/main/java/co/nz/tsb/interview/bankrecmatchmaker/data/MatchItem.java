package co.nz.tsb.interview.bankrecmatchmaker.data;

import java.math.BigDecimal;
import java.util.Objects;

public class MatchItem {

    private final String paidTo;
    private final String transactionDate;
    private final BigDecimal total;
    private final String docType;
    private String id;


    public MatchItem(String paidTo, String transactionDate, BigDecimal total, String docType, String id) {
        this.paidTo = paidTo;
        this.transactionDate = transactionDate;
        this.total = total;
        this.docType = docType;
        this.id = id;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getDocType() {
        return docType;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchItem matchItem = (MatchItem) o;
        return Objects.equals(id, matchItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
