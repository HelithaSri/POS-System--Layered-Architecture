package bo;

import bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBoImpl();
            case CUSTOMER:
                return new CustomerBoImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBoImpl();
            case MANAGE_ORDER:
                return new ManageOrderBoImpl();
            case USER:
                return new UserBoImpl();
            case LOGIN:
                return new LoginBoImpl();
            case Report:
                return new SystemReportsBoImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER,MANAGE_ORDER,USER,LOGIN,Report
    }

}
