const urlBuilder = require("./url");
const auth_module = require("./auth");
const Singleton = require("./client_manager");

main();

async function main() {
    // get auth url
    console.log(await urlBuilder.urlBuilder("auth"));   
    // timepoint
    console.log(await auth_module.time_frame());
    // login
    let token = await auth_module.login();
    console.log(token);
    // is valid token?
    console.log(await auth_module.isValidJwt(token));
    // get local unified token
    console.log(await (await Singleton.getInstance()).jwtoken );
}
