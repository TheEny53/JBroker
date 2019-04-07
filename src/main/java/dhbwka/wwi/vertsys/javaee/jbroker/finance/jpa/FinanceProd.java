/*
 * Copyright Â© 2019 Anika Schmidt, Enzo Hilzinger, Marvin GÃ¶ckel
 * 
 * E-Mail: enzo.hilzinger@sap.com
 * Webseite: https://www.sap.com/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jbroker.finance.jpa;

import dhbwka.wwi.vertsys.javaee.jbroker.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author D070512
 */
@Entity
public class FinanceProd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "prod_ids")
    @TableGenerator(name = "prod_ids", initialValue = 0, allocationSize = 500)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Finanzprodukt muss einem Besitzer zugeordnet werden.")
    private User owner;

    @ManyToOne
    private FinanceProdCat category;

    @Column(length = 12)
    @NotNull(message = "Die ISIN darf nicht leer sein.")
    @Size(min = 12, max = 12, message = "Die ISIN muss genau zwölfstellig angegeben werden.")
    private String isin;

    @Column(length = 55)
    @Size(min = 1, max = 55)
    private String exchange_name;

    @NotNull(message = "Die Anzahl darf nicht leer sein.")
    private int amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus status = ProductStatus.ORDER_PLACED;

    @NotNull(message = "Klarname darf nicht leer sein.")
    private String product_name;

    @Column(length = 1024)
    private String notes;

    private Date due_date;
    private double order_course;
    private double sell_course;

    public User getOwner() {
        return owner;
    }

    public FinanceProdCat getCategory() {
        return category;
    }

    public String getIsin() {
        return isin;
    }

    public String getExchange_name() {
        return exchange_name;
    }

    public int getAmount() {
        return amount;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getNotes() {
        return notes;
    }

    public Date getDueDate() {
        return due_date;
    }

    public double getOrder_course() {
        return order_course;
    }

    public double getSell_course() {
        return sell_course;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCategory(FinanceProdCat category) {
        this.category = category;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public void setExchange_name(String exchange_name) {
        this.exchange_name = exchange_name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public void setOrder_course(double order_course) {
        this.order_course = order_course;
    }

    public void setSell_course(double sell_course) {
        this.sell_course = sell_course;
    }

    public FinanceProd(User owner, FinanceProdCat category, String isin, String exchange_name, int amount, String product_name, String notes, Date due_date, double order_course, double sell_course) {
        this.owner = owner;
        this.category = category;
        this.isin = isin;
        this.exchange_name = exchange_name;
        this.amount = amount;
        this.product_name = product_name;
        this.notes = notes;
        this.due_date = due_date;
        this.order_course = order_course;
        this.sell_course = sell_course;
    }

    public FinanceProd() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
