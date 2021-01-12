
/*++
Project Name:
watchdog_client

Author:
Maximilian M (Meshstyles)

Description:
builds the request url
can be more complicated if complexity is needed

--*/

const settings = require("../settings/settings.json");

/**
 * Summary. Builds url with this specific endpoint
 * 
 * @function
 * 
 * @param {string} endpoint 
 * 
 * @return Promise<String>:url
 */
async function urlBuilder(endpoint) {
    const { baseurl, port, baseapi } = settings.address;
    return `${baseurl}:${port}${baseapi}/${endpoint}`
}

module.exports.urlBuilder = urlBuilder;
