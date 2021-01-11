const { default: axios } = require("axios");
const Singleton = require("./client_manager");
const { urlBuilder } = require("./url");

async function push(message) {
    let { logtype, logobject } = await message;
    let { jwt } = await ( await Singleton.getInstance() ).jwtoken;
    
    const config = {
        headers: { Authorization: `Bearer ${await jwt}` }
    };

    let url = await urlBuilder(logtype);
    // console.log(url);
    // console.log(logobject);
    // console.log(config);

    // let res = await axios.post( 
    //     url,
    //     await logobject,
    //     config
    // ).then(console.log).catch(console.log);
    
    let res = await axios.post(url, logobject, config);
    console.log(await res.data);
    return res.data;
}

async function sshlog(log) {
    log = log.replace("\\n","")
    let message = { logtype: "demolog", logobject: { sus: log } }
    push(message);
}

module.exports.sshlog = sshlog;
