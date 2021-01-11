const settings = require("../settings/settings.json");

async function urlBuilder(endpoint) {
    const { baseurl, port, baseapi } = settings.address;
    return `${baseurl}:${port}${baseapi}/${endpoint}`
}

module.exports.urlBuilder = urlBuilder;
