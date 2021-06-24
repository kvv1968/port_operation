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


    public Berth(TypeCargo typeCargo, boolean isFreeBerth) {
        super(isFreeBerth);
        this.typeCargo = typeCargo;
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


}
