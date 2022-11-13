const getAllTrains = () => {
    var start = parseInt(document.getElementById("start").value);
    var end = parseInt(document.getElementById("end").value);
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains?start=${start}&end=${end}`;
    fetch(url)
        .then(response => {
        if (!response.ok) {
            throw Error("error fetching all trains! ");
        }
        return response.json();
    })
        .then(json => {
        displayAllTrains(json);
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const getStations = () => {
    var trainId = parseInt(document.getElementById("listOfStationsId").value);
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}`;
    fetch(url)
        .then(response => {
        if (!response.ok) {
            throw Error("error fetching all stations of train " + trainId + "! ");
        }
        return response.json();
    })
        .then(json => {
        displayAllStations(json);
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const addTrain = () => {
    var trainType = document.getElementById("addTrainType").value;
    var trainStations = document.getElementById("addTrainStations").value;
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains`;
    let trainJson = {
        id: 0,
        stations: [],
        type: trainType
    };
    var stations = trainStations.split(",");
    for (let station of stations) {
        trainJson.stations.push(station);
    }
    const options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(trainJson)
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw Error("error adding train!");
        }
        let url = response.headers.get("Location");
        alert(url);
        getAllTrains();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const removeTrain = () => {
    var trainId = parseInt(document.getElementById("removeTrainId").value);
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}`;
    const options = {
        method: "DELETE"
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw Error("error removing train with id " + trainId + "!");
        }
        getAllTrains();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const updateTrain = () => {
    var trainId = parseInt(document.getElementById("updateTrainId").value);
    var trainType = document.getElementById("updateTrainType").value;
    var trainStations = document.getElementById("updateTrainStations").value;
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains`;
    console.log(trainId);
    if (!(trainId > 0)) {
        trainId = 0;
    }
    console.log(trainId);
    let trainJson = {
        id: trainId,
        stations: [],
        type: trainType
    };
    var stations = trainStations.split(",");
    for (let station of stations) {
        trainJson.stations.push(station);
    }
    const options = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(trainJson)
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw Error("error updating train!");
        }
        let url = response.headers.get("Location");
        alert(url);
        getAllTrains();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const addStations = () => {
    var trainId = parseInt(document.getElementById("addStationId").value);
    var trainStation = document.getElementById("addStationStation").value;
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}?station=${trainStation}`;
    const options = {
        method: "POST"
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw Error("error adding station to train!");
        }
        getAllTrains();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const removeStations = () => {
    var trainId = parseInt(document.getElementById("removeStationId").value);
    const url = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/stations/${trainId}`;
    const options = {
        method: "DELETE"
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw Error("error removing stations of train with id " + trainId + "!");
        }
        getAllTrains();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
//OUTPUT
const displayAllTrains = (json) => {
    let output = "";
    console.log("trains");
    console.log(json);
    for (let train of json) {
        output += `<tr><td>${train.id}</td><td>${train.type}</td><td>`;
        for (let station of train.stations) {
            output += station + " - ";
        }
        output += "</td></tr>";
    }
    document.getElementById("allTrains").innerHTML = output;
};
const displayAllStations = (json) => {
    let output = "";
    console.log("stations");
    console.log(json);
    for (let station of json) {
        output += `<tr><td>${station}</td></tr>`;
    }
    document.getElementById("listOfStations").innerHTML = output;
};
const displayErrors = (err) => {
    document.getElementById("errors").innerHTML = err;
};
