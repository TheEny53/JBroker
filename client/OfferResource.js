class OfferResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/jBroker/api/financeproducts/";
        this.username = "";
        this.password = "";
    }

    /**
     * Benutzername und Passwort für die Authentifizierung merken.
     * @param {String} username Benutzername
     * @param {String} password Passwort
     */
    setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Songs suchen.
     * @param {String} query Suchparameter (optional)
     * @returns {Promise} Gefundene Songs
     */
    async findProducts(query) {
        let url = this.url;

        if (query !== undefined) {
            url += "?query=" + encodeURI(query);
        }

        let response = await fetch(url, {
            headers: {
                "accept": "application/json"
            }
        });

        return await response.json();
    }


    async getProduct(id) {
        let response = await fetch(this.url + id + "/", {
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });

        return await response.json();
    }
}