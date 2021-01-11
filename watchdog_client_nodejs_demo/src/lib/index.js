const urlBuilder = require("./url");
const auth_module = require("./auth");
const Singleton = require("./client_manager");

main();

async function main() {
    // console.log(await urlBuilder.urlBuilder("auth"));   
    // console.log(await auth_module.time_frame());
    // let token = await auth_module.login();
    // console.log(token);
    // console.log(await auth_module.isValidJwt(token));
    console.log(await (await Singleton.getInstance()).jwtoken );
}
