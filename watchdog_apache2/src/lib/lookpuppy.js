/*++
Project Name:
watchdog_client

Author:
Maximilian M (Meshstyles)

Description:
module that handles making requests to the watchdog api log server 
and related functions

--*/

const { default: axios } = require("axios");
const Singleton = require("./client_manager");
const { urlBuilder } = require("./url");


/**
 * Summary. send the premade object to specified endpoint
 * @function
 * @param {object} message a object with log array/object and logtype
 */
async function push(message) {
    let { logtype, logobject } = await message;
    let { jwt } = await ( await Singleton.getInstance() ).jwtoken;
    
    const config = {
        headers: { Authorization: `Bearer ${await jwt}` }
    };

    let url = await urlBuilder(logtype);
    
    let res = await axios.post(url, logobject, config).catch(console.log);

    // DO NOT REMOVE --DEV
    console.log(await res.data);
    return res.data;
}

/**
 * 
 * Summary. Function to send an log.
 * Description. This function is used as an abstraction between the component that actually sends logs
 * 
 * @function
 * 
 * @param {object} log[] array of logs
 * 
 * @returns void
 */

async function apache2log(log) {
    // use combined object to with logtype("endpoint") and the actual log "object"
    let message = { logtype: "apache2", logobject: { log: log } }
    push(message);
}

module.exports.apache2log = apache2log;