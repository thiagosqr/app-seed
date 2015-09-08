package com.github.thiagosqr.representation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name=DataTableResponse.schemaName)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DataTableResponse.schemaName,propOrder = {}
)
public class DataTableResponse {
    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List data;
    private String error;

    public void setDraw(final Integer draw) {
        this.draw = draw;
    }

    public void setRecordsTotal(final Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public void setRecordsFiltered(final Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public void setData(final List data) {
        this.data = data;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Integer getDraw() {
        return draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public List getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public final static String schemaName = "datatableresponse";

    public final static String json ="application/com.github.thiagosqr.representation."+schemaName+"+json";

}