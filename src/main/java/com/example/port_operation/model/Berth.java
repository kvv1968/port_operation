package com.example.port_operation.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class Berth extends ApplicationEvent  {
    private final Log logger = LogFactory.getLog(Berth.class);
    private TypeCargo typeCargo;
    private ShipUnload shipUnload;
    private boolean isFreeBerth;
    private String threadNames;
    private  int indicator;
    private long  shipId;
    private int amountMax;


    public Berth(TypeCargo typeCargo, boolean isFreeBerth) {
        super(typeCargo);
        this.typeCargo = typeCargo;
        this.isFreeBerth = isFreeBerth;
    }

    public boolean isFreeBerth() {
        isFreeBerth = shipUnload == null;
        logger.info(String.format("Свободен причал %s", isFreeBerth));
        return isFreeBerth;
    }

    @Override
    public String toString() {
        return "Berth{" +
                "typeCargo=" + typeCargo +
                ", shipUnload=" + shipUnload +
                ", isFreeBerth=" + isFreeBerth +
                '}';
    }

    public int getIndicator() {
        int i = 0;
        if (shipUnload != null){
            i = shipUnload.getIndicator() != null ? shipUnload.getIndicator().get() : 0;
        }
        return i;
    }

    public long getShipId() {
        long id = 0L;
        if (shipUnload != null){
            id = shipUnload.getShip().getId();
        }
        return id;
    }

    public int getAmountMax() {
        int amount = 0;
        if (shipUnload != null){
            amount = shipUnload.getShip().getAmountCargo();
        }
        return amount;
    }
}
