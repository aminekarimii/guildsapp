let mongoose = require('mongoose');
const server = '127.0.0.1:27017'; // REPLACE WITH YOUR DB SERVER
const database = 'sqli_guildes_db';      // REPLACE WITH YOUR DB NAME
class Database {
    constructor() {
        this._connect()
    }
    _connect() {
        mongoose.connect(`mongodb://${server}/${database}`, { useNewUrlParser: true })
            .then(() => {
                console.log('Database connection successful');
            })
            .catch(err => {
                console.error('Database connection error')
            })
    }
}



// const initDb = () => {
//     const user = new UserModel({
//         username: 'akherbouch'
//     });
//     user.setPassword('123456789');
//     user.save().then(console.log).catch(console.log);
// };

module.exports = new Database();