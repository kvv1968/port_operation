package com.example.port_operation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ship")
public class Ship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = TypeCargo.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "typecargo_id")
    private TypeCargo cargo;

    @Column
    private int amountCargo;

    public Ship(TypeCargo cargo, int amountCargo) {
        this.cargo = cargo;
        this.amountCargo = amountCargo;
    }
}
