
/*++
Project Name:
watchdog_client

Author:
Maximilian M (Meshstyles)

Description:
main program, actually working with the log

--*/

const TailingReadableStream = require('tailing-stream');
const { sshlog, apache2log } = require('./lib/lookpuppy');
const settings = require('./settings/settings.json');
const { apache2, ssh } = settings.env;

if (apache2) {
    const apache2Stream = TailingReadableStream.createReadStream("/var/log/apache2/access.log", { timeout: 0 });

    apache2Stream.on('data', buffer => {
        let log = buffer.toString();

        // split on newline => log array
        let args = log.split("\n");

        //filter out file beginning
        if (args.length < 10) {

            // removes last empty line /n/n
            args.pop();

            // for each log
            args.forEach(elog => {
                // check if there is a empty line in the log
                if (!(elog === "")) {
                    console.log("apache2: " + log)
                    apache2log(elog)
                }
            });
        }
    });

    apache2Stream.on('close', () => {
        console.log("close");
    });
}

if (ssh) {
    const sshStream = TailingReadableStream.createReadStream("./log.log", { timeout: 0 });

    sshStream.on('data', buffer => {
        let log = buffer.toString();

        // split on newline => log array
        let args = log.split("\n");

        //filter out file beginning
        if (args.length < 10) {

            // removes last empty line /n/n
            args.pop();

            // for each log
            args.forEach(elog => {
                // check if there is a empty line in the log
                if (!(elog === "")) {
                    // added filter to not send sshd unrealted messages
                    if (elog.includes("sshd[")) {
                        // send log to matching method in lib/lookpuppy
                        sshlog(elog)
                    }
                }
            });
        }
    });


    sshStream.on('close', () => {
        console.log("close");
    });
}

