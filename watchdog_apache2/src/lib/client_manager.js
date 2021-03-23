
/*++
Project Name:
watchdog_client

Author:
Maximilian Medlin (Meshstyles)

Description:
This manager handles having a currently valid jwt token and mitigatest the need to request a new token each request

--*/

const auth_module = require('./auth');

class PrivateSingleton {
    constructor() {
        // dummy data -- 0 is most likely before the current time
        this.jwtoken = { jwt: "lol", timeframe: 0 };
    }
}

class Singleton {
    constructor() {
        throw new Error('Use Singleton.getInstance()');
    }
    static getInstance = async () => {
        if (!Singleton.instance) {
            Singleton.instance = new PrivateSingleton();
        }
        // look up if the jwt should still be valid
        if (! (await auth_module.isValidJwt(Singleton.instance.jwtoken)) ) {
                Singleton.instance.jwtoken = await auth_module.login();
        }
        return Singleton.instance;
    }
}
module.exports = Singleton;
