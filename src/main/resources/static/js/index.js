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
            let tr = $("<tr id='tr" + berth.id + "'></tr>");
            let th = "";
            th += "<th>" + berth.id + "</th>";
            th += "<th id='th" + berth.id + "type'>" + berth.type + "</th>";
            th += "<th id='th" + berth.id + "ship-id'>" + berth.ship.id + "</th>";
            th += "<th id='th" + berth.id + "ship-type-cargo'>" + berth.ship.cargo.type + "</th>";
            th += "<th id='th" + berth.id + "ship-amount'>" + berth.ship.amount + "</th>";
            tr.html(td);
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
            th += "<th id='th" + ship.id + "id'>" + i + "</th>";
            th += "<th id='th" + ship.id + "id'>" + ship.id + "</th>";
            th += "<th id='th" + ship.id + "ship-type-cargo'>" + ship.cargo.type + "</th>";
            th += "<th id='th" + ship.id + "ship-amount'>" + ship.amount + "</th>";
            tr.html(td);
            shipList.append(tr);
        }
    });
}