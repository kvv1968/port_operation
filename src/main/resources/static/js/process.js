let berthList = $("#berths");
let shipList = $("#ships");


$(document).ready(function () {
    getAllShipsByRaid();
    getAllBerths();
});

function getAllBerths() {
    $.get("/rest/berths", function (data) {
        if (data !== null) {

                for (let i = 0; i < data.length; i++) {
                    let berth = data[i];
                    let cargo = berth.typeCargo;
                    let tr = $("<tr id='tr" + cargo.id + "'></tr>");
                    let th = "";
                    th += "<th>" + cargo.type + "</th>";
                    th += "<th id='th" + cargo.id + "ship-id'>" + berth.nameThread + "</th>";
                    th += "<th id='th" + cargo.id + "ship-i'>" + berth.processIndicator + "</th>";
                    tr.html(th);
                    berthList.append(tr);
                }
            }


    });
}

function getAllShipsByRaid() {
    $.get("/rest/raids", function (data) {
        if (data !== null) {

            for (let i = 0; i < data.length; i++) {
                let ship = data[i];
                let tr = $("<tr id='tr" + ship.id + "'></tr>");
                let th = "";
                th += "<th id='th" + ship.id + "id'>" + (i + 1) + "</th>";
                th += "<th id='th" + ship.id + "ship-id'>" + ship.id + "</th>";
                th += "<th id='th" + ship.id + "ship-type-cargo'>" + ship.cargo.type + "</th>";
                th += "<th id='th" + ship.id + "ship-amount'>" + ship.amountCargo + "</th>";
                tr.html(th);
                shipList.append(tr);
            }
        }
    });
}