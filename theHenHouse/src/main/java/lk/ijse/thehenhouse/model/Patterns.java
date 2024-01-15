package lk.ijse.thehenhouse.model;

import java.util.regex.Pattern;

public class Patterns {
///////////////////////////////// Customer Pater ///////////////////////////////////////////////////////
    private static final Pattern customerIdPattern = Pattern.compile("^(C)(\\d{0,3})?$");
    private static final Pattern customerNamePattern = Pattern.compile("^[a-zA-Z]([  a-z  A-Z]{1,30})?$");
    private static final Pattern customerMobilePattern = Pattern.compile("^([0-9]{10,10})?$");
    private static final Pattern customerAddressPattern = Pattern.compile("^[a-zA-Z]([  a-z  A-Z]{1,30})?$");

    public static Pattern getCustomerIdPattern() {return customerIdPattern;}
    public static Pattern getCustomerNamePattern() {return customerNamePattern;}
    public static Pattern getCustomerMobilePattern() {return customerMobilePattern;}
    public static Pattern getCustomerAddressPattern() {return customerAddressPattern;}

////////////////////////////// Item Partern ///////////////////////////////////////////////////////////////

    private static final Pattern itemIdPattern = Pattern.compile("^(I)(\\d{0,3})?$");
    private static final Pattern itemDescriptionPattern = Pattern.compile("^[a-zA-Z]([  a-z  A-Z]{1,30})?$");
    private static final Pattern itemUnitPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    private static final Pattern itemQtyPattern= Pattern.compile("^(0|[1-9][0-9]*)$");

    public static Pattern getItemIdPattern() {return itemIdPattern;}
    public static Pattern getItemDescriptionPattern() {return itemDescriptionPattern;}
    public static Pattern getItemUnitPricePattern() {return itemUnitPricePattern;}
    public static Pattern getItemQtyPattern() {return itemQtyPattern;}

////////////////////////////////// Employee Patern ///////////////////////////////////////////////////////

    private static final Pattern employeeIdPattern = Pattern.compile("^(E)(\\d{0,3})?$");
    private static final Pattern employeeNamePatern = Pattern.compile("^[a-zA-Z]([  a-z  A-Z]{1,30})?$");
    private static final Pattern employeeContactPatern = Pattern.compile("^([0-9]{10,10})?$");
    private static final Pattern employeeAdressPatern= Pattern.compile("^[a-zA-Z]([  a-z  A-Z]{1,30})?$");

    public static Pattern getEmployeeIdPattern() {return employeeIdPattern;}
    public static Pattern getEmployeeNamePatern() {return employeeNamePatern;}
    public static Pattern getEmployeeContactPatern() {return employeeContactPatern;}
    public static Pattern getEmployeeAdressPatern() {return employeeAdressPatern;}

////////////////////////////////// Supplier Patern ///////////////////////////////////////////////////////

    private static final Pattern supplierIdPattern = Pattern.compile("^(S)(\\d{0,3})?$");
    private static final Pattern supplierNamePatern = Pattern.compile("^[a-z A-Z]([  a-z  A-Z]{1,30})?$");
    private static final Pattern supllierContactPatern = Pattern.compile("^([0-9]{10,10})?$");


    public static Pattern getSupplierIdPattern() {return supplierIdPattern;}
    public static Pattern getSupplierNamePatern() {return supplierNamePatern;}
    public static Pattern getSupllierContactPatern() {return supllierContactPatern;}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Pattern orderQty= Pattern.compile("^(0|[1-9][0-9]*)$");
    public static Pattern getOrderQty() {return orderQty;}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static final Pattern email = Pattern.compile("^([a-z]{0,22})(\\d{0,20})([@gmail.com]{10,10})?$");
    private static final Pattern password =Pattern.compile("([a-z]{0,22})(\\d{0,20})([-/*#^]{0,11})?$");
    public static Pattern getEmailPattern() {return email; }
    public static Pattern getPassword(){return password;}

}
