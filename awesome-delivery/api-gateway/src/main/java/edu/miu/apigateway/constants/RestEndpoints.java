package edu.miu.apigateway.constants;

public class RestEndpoints {

    // Account Endpoints
    public static final String ACCOUNT_PREFIX =  "api/accounts/";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String CURRENT = "current";

    // By ID postfix
    public static final String BY_ID = "/{id}";
    public static final String RES_ID = "/{res_id}/";
    public static final String DRI_ID = "/{dri_id}/";
    public static final String CUS_ID = "/{cus_id}/";
    public static final String MENU_ID = "/{menu_id}";
    public static final String ORD_ID = "/{ord_id}";

    // Restaurant Endpoints
    public static final String RESTAURANT_PREFIX = "api/restaurants/";
    public static final String MENUS = "menus";
    public static final String RES_ORDERS = "orders";
    public static final String ACCEPT = "/accept";
    public static final String REJECT = "/reject";
    public static final String READY = "/ready";

    // Customer Endpoints
    public static final String CUSTOMER_PREFIX = "api/customers/";
    public static final String SEARCH = "/search";
    public static final String RESTAURANTS = "restaurants";
    public static final String CUS_ORDERS = "orders";

    // Driver Endpoints
    public static final String DRIVER_PREFIX = "api/drivers/";
    public static final String DRI_ORDERS = "orders";
    public static final String PICK = "/pick";
    public static final String DELIVER = "/deliver";

}
