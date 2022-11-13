onload = () => {
    getAllPlayers();
}


const getAllPlayers = () => {
    let start: number = parseInt((<HTMLInputElement>document.getElementById("start")).value);
    let end: number = parseInt((<HTMLInputElement>document.getElementById("end")).value);

    const url: string = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player?start=${start}&end=${end}`;

    fetch(url)
        .then(response => {
            if(!response.ok){
                throw new Error("error getting all players")
            }
            return response.json();
        })
        .then(json => {
            displayAllPlayers(json);
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const getPlayerById = () => {
    let id: number = parseInt((<HTMLInputElement>document.getElementById("getPlayerId")).value);

    const url: string = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player/` + id;

    fetch(url)
        .then(response => {
            if(!response.ok){
                throw new Error("error getting player by id: player not found")
            }
            return response.json();
        })
        .then(json => {
            displayPlayer(json);
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const addPlayer = () => {
    let name: string = (<HTMLInputElement>document.getElementById("addPlayerName")).value;
    let team: string = (<HTMLInputElement>document.getElementById("addPlayerTeam")).value;
    let age: number = parseInt((<HTMLInputElement>document.getElementById("addPlayerAge")).value);

    const url: string = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player`;

    const playerJson = {
        id: 0,
        name: name,
        team: team,
        age: age
    }

    const options = {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(playerJson)
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw new Error("error adding player: player already exists")
            }

            getAllPlayers();
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const removePlayer = () => {
    let id: number = parseInt((<HTMLInputElement>document.getElementById("removePlayerId")).value);

    const url: string = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player/` + id;

    const options = {
        method: "DELETE"
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw new Error("error removing player: player not found")
            }

            getAllPlayers();
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}

const updatePlayer = () => {
    let id: number = parseInt((<HTMLInputElement>document.getElementById("updatePlayerId")).value);
    let name: string = (<HTMLInputElement>document.getElementById("updatePlayerName")).value;
    let team: string = (<HTMLInputElement>document.getElementById("updatePlayerTeam")).value;
    let age: number = parseInt((<HTMLInputElement>document.getElementById("updatePlayerAge")).value);

    if(!(id > 0)){
        id = 0;
    }

    const url: string = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player`;

    const playerJson = {
        id: id,
        name: name,
        team: team,
        age: age
    }

    console.log(playerJson)

    const options = {
        method: "PUT",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(playerJson)
    }

    fetch(url, options)
        .then(response => {
            if(!response.ok){
                throw new Error("error updating player: player already exists")
            }

            getAllPlayers();
            displayErrors("");
        })
        .catch(err => displayErrors(err))
}



//OUTPUT

const displayAllPlayers = (json) => {
    let output: string = "";

    json.forEach(player => output += `<tr><td>${player.id}</td><td>${player.name}</td><td>${player.team}</td><td>${player.age}</td></tr>`);

    document.getElementById("getAllPlayersList").innerHTML = output;
}

const displayPlayer = (player) => {
    let output: string = `<tr><td>${player.id}</td><td>${player.name}</td><td>${player.team}</td><td>${player.age}</td></tr>`;

    document.getElementById("getPlayersList").innerHTML = output;
}

const displayErrors = (err) => {
    document.getElementById("error").innerHTML = err;
}