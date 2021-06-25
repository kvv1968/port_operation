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
    private static long indicator;
    private long  shipId;


    public Berth(TypeCargo typeCargo, boolean isFreeBerth) {
        super(typeCargo);
        this.typeCargo = typeCargo;
        this.isFreeBerth = isFreeBerth;
    }

    public boolean isFreeBerth() {
        isFreeBerth = shipUnload == null;
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

    public static long getIndicator() {
        return ShipUnload.indicator;
    }

    public long getShipId() {
        long id = 0L;
        if (shipUnload != null){
            id = shipUnload.getShip().getId();
        }
        return id;
    }
}
