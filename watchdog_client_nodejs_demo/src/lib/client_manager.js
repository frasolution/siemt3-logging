const auth_module = require('./auth');

class PrivateSingleton {
    constructor() {
        this.jwtoken = { jwt: "lol", timeframe: 0 };
        this.firstread = false;
    }
}
class Singleton {
    constructor() {
        throw new Error('Use Singleton.getInstance()');
    }
    static getInstance = async () => {
        if (!Singleton.instance) {
            Singleton.instance = new PrivateSingleton();
            // console.log(Singleton.instance);
            if (! (await auth_module.isValidJwt(Singleton.instance.jwtoken)) ) {
                Singleton.instance.jwtoken = await auth_module.login();
            }
        }
        return Singleton.instance;
    }
    set firstread(state) {
        this.instance.firstread = state;
    }
}
module.exports = Singleton;