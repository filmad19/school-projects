const getAllTrains = () => {
    var start: number = parseInt((<HTMLInputElement>document.getElementById("start")).value);
    var end: number = parseInt((<HTMLInputElement>document.getElementById("end")).value);

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains?start=${start}&end=${end}`

    fetch(url)
        .then(response => {
            if(!response.ok){
                throw Error("error fetching all trains! ");
            }

            return response.json();
        })
        .then(json => {
            displayAllTrains(json);
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const getStations = () => {
    var trainId: number = parseInt((<HTMLInputElement>document.getElementById("listOfStationsId")).value);

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}`;

    fetch(url)
        .then(response => {
            if(!response.ok){
                throw Error("error fetching all stations of train " + trainId + "! ");
            }

            return response.json();
        })
        .then(json => {
            displayAllStations(json);
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const addTrain = () => {
    var trainType: string = (<HTMLInputElement>document.getElementById("addTrainType")).value;
    var trainStations: string = (<HTMLInputElement>document.getElementById("addTrainStations")).value;

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains`;

    let trainJson = {
        id: 0,
        stations: [

        ],
        type: trainType
    }

    var stations: string[] = trainStations.split(",")
    for(let station of stations){
        trainJson.stations.push(station);
    }

    const options = {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(trainJson)
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw Error("error adding train!");
            }

            let url: string = response.headers.get("Location");
            alert(url);
            getAllTrains();
            displayErrors("");
        })
        .catch(err => displayErrors(err))

}

const removeTrain = () => {
    var trainId: number = parseInt((<HTMLInputElement>document.getElementById("removeTrainId")).value);

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}`;

    const options = {
        method: "DELETE"
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw Error("error removing train with id " + trainId + "!");
            }

            getAllTrains();
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const updateTrain = () => {
    var trainId: number = parseInt((<HTMLInputElement>document.getElementById("updateTrainId")).value);
    var trainType: string = (<HTMLInputElement>document.getElementById("updateTrainType")).value;
    var trainStations: string = (<HTMLInputElement>document.getElementById("updateTrainStations")).value;

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains`;

    console.log(trainId)
    if(!(trainId > 0)){
        trainId = 0;
    }
    console.log(trainId)


    let trainJson = {
        id: trainId,
        stations: [

        ],
        type: trainType
    }

    var stations: string[] = trainStations.split(",")
    for(let station of stations){
        trainJson.stations.push(station);
    }

    const options = {
        method: "PUT",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(trainJson)
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw Error("error updating train!");
            }

            let url: string = response.headers.get("Location");
            alert(url);
            getAllTrains();
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const addStations = () => {
    var trainId: number = parseInt((<HTMLInputElement>document.getElementById("addStationId")).value);
    var trainStation: string = (<HTMLInputElement>document.getElementById("addStationStation")).value;


    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/${trainId}?station=${trainStation}`;


    const options = {
        method: "POST"
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw Error("error adding station to train!");
            }

            getAllTrains();
            displayErrors("");
        })
        .catch(err => displayErrors(err))


}

const removeStations = () => {
    var trainId: number = parseInt((<HTMLInputElement>document.getElementById("removeStationId")).value);

    const url: string = `http://localhost:8080/lernen_trains-1.0-SNAPSHOT/api/trains/stations/${trainId}`;

    const options = {
        method: "DELETE"
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw Error("error removing stations of train with id " + trainId + "!");
            }

            getAllTrains();
            displayErrors("");
        })
        .catch(err => displayErrors(err))

}

    //OUTPUT

const displayAllTrains = (json) => {
    let output: string = "";
    console.log("trains")
    console.log(json)

    for(let train of json){
        output += `<tr><td>${train.id}</td><td>${train.type}</td><td>`;

        for(let station of train.stations){
            output += station + " - ";
        }

        output += "</td></tr>";
    }

    document.getElementById("allTrains").innerHTML = output;
}

const displayAllStations = (json) => {
    let output: string = "";
    console.log("stations")
    console.log(json)

    for(let station of json){
        output += `<tr><td>${station}</td></tr>`;
    }

    document.getElementById("listOfStations").innerHTML = output;
}

const displayErrors = (err) => {
    document.getElementById("errors").innerHTML = err;
}