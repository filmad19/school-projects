onload = () => {
    getAllPlayers();
};
const getAllPlayers = () => {
    let start = parseInt(document.getElementById("start").value);
    let end = parseInt(document.getElementById("end").value);
    const url = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player?start=${start}&end=${end}`;
    fetch(url)
        .then(response => {
        if (!response.ok) {
            throw new Error("error getting all players");
        }
        return response.json();
    })
        .then(json => {
        displayAllPlayers(json);
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const getPlayerById = () => {
    let id = parseInt(document.getElementById("getPlayerId").value);
    const url = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player/` + id;
    fetch(url)
        .then(response => {
        if (!response.ok) {
            throw new Error("error getting player by id: player not found");
        }
        return response.json();
    })
        .then(json => {
        displayPlayer(json);
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const addPlayer = () => {
    let name = document.getElementById("addPlayerName").value;
    let team = document.getElementById("addPlayerTeam").value;
    let age = parseInt(document.getElementById("addPlayerAge").value);
    const url = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player`;
    const playerJson = {
        id: 0,
        name: name,
        team: team,
        age: age
    };
    const options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(playerJson)
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw new Error("error adding player: player already exists");
        }
        getAllPlayers();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const removePlayer = () => {
    let id = parseInt(document.getElementById("removePlayerId").value);
    const url = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player/` + id;
    const options = {
        method: "DELETE"
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw new Error("error removing player: player not found");
        }
        getAllPlayers();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
const updatePlayer = () => {
    let id = parseInt(document.getElementById("updatePlayerId").value);
    let name = document.getElementById("updatePlayerName").value;
    let team = document.getElementById("updatePlayerTeam").value;
    let age = parseInt(document.getElementById("updatePlayerAge").value);
    if (!(id > 0)) {
        id = 0;
    }
    const url = `http://localhost:8080/lernen_player-1.0-SNAPSHOT/api/player`;
    const playerJson = {
        id: id,
        name: name,
        team: team,
        age: age
    };
    console.log(playerJson);
    const options = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(playerJson)
    };
    fetch(url, options)
        .then(response => {
        if (!response.ok) {
            throw new Error("error updating player: player already exists");
        }
        getAllPlayers();
        displayErrors("");
    })
        .catch(err => displayErrors(err));
};
//OUTPUT
const displayAllPlayers = (json) => {
    let output = "";
    json.forEach(player => output += `<tr><td>${player.id}</td><td>${player.name}</td><td>${player.team}</td><td>${player.age}</td></tr>`);
    document.getElementById("getAllPlayersList").innerHTML = output;
};
const displayPlayer = (player) => {
    let output = `<tr><td>${player.id}</td><td>${player.name}</td><td>${player.team}</td><td>${player.age}</td></tr>`;
    document.getElementById("getPlayersList").innerHTML = output;
};
const displayErrors = (err) => {
    document.getElementById("error").innerHTML = err;
};
