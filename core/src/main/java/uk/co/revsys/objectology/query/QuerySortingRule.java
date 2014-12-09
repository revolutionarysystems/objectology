package uk.co.revsys.objectology.query;

public class QuerySortingRule {

    private String field;
    private SortOrder order = SortOrder.ASCENDING;

    public QuerySortingRule(String field) {
        this.field = field;
    }

    public QuerySortingRule(String field, SortOrder order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "QuerySortingRule{" + "field=" + field + ", order=" + order + '}';
    }

}
