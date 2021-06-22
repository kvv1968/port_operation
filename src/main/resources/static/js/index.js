let berthList = $("#list-berths");
let shipList = $("#ship-list");

$(function (){
    getAllBerths();
    getAllShipsByRaid();
});

function getAllBerths() {
    $.get("/rest/berths", function (data) {
        for (let i = 0; i < data.length; i++) {
            let berth = data[i];
            let ship = berth.ships[0];
            let cargo = berth.typeCargo;
            let tr = $("<tr id='tr" + cargo.id + "'></tr>");
            let th = "";
            th += "<th>" + cargo.type + "</th>";
            th += "<th id='th" + cargo.id + "ship-id'>" + (ship.id === 0?0:ship.id) + "</th>";
            th += "<th id='th" + cargo.id + "ship-type-cargo'>" +(ship.cargo.type === null?"нет данных":ship.cargo.type) + "</th>";
            th += "<th id='th" + cargo.id + "ship-amount'>" + (ship.amountCargo ===0?0:ship.amountCargo) + "</th>";
            th += "<th id='th" + cargo.id + "ship-i'>" + i + "</th>";
            tr.html(th);
            berthList.append(tr);
        }
    });
}

function getAllShipsByRaid() {
    $.get("/rest/raids", function (data) {
        for (let i = 0; i < data.length; i++) {
            let ship = data[i];
            let tr = $("<tr id='tr" + ship.id + "'></tr>");
            let th = "";
            th += "<th id='th" + ship.id + "id'>" + (i+1) + "</th>";
            th += "<th id='th" + ship.id + "ship-id'>" + ship.id + "</th>";
            th += "<th id='th" + ship.id + "ship-type-cargo'>" + ship.cargo.type + "</th>";
            th += "<th id='th" + ship.id + "ship-amount'>" + ship.amountCargo + "</th>";
            tr.html(th);
            shipList.append(tr);
        }
    });
}