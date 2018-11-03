
package es.upm.miw.bookshop.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SaleInfo {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("listPrice")
    @Expose
    private ListPrice listPrice;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("country", country).append("listPrice", listPrice).toString();
    }

}
