const { default: axios } = require("axios");
const settings = require("../settings/settings.json");
const { urlBuilder } = require("./url");

const { time, credentials } = settings;
const { jwt_timeframe } = time;

/**
 * this is the time point util the token will work
 */

async function time_frame() {
    const valid_timestamp = Date.now();
    return await (valid_timestamp + jwt_timeframe) ;
}

/**
 * this returns the jwt login token
 */
async function login(){
    const auth_url = urlBuilder("auth");
    const timeframe = await time_frame();
    const res = await axios.post(await auth_url, credentials);
    let jwt = res.data.jwt
    return {jwt, timeframe};
}



/**
 * this checks if the token should still be valid
 */
async function isValidJwt(token){
    let timestamp_now = await Date.now();
    let timestamp_token = await token.timeframe;
    
    // when timestamp now is smaller than the token stamp
    // than the token should still be valid
    if (timestamp_now < timestamp_token) {
        return true;
    } else { 
        return false;
    }
}

module.exports.isValidJwt = isValidJwt;
module.exports.login = login;
module.exports.time_frame = time_frame;