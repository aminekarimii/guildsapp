const passport = require('passport');
const LocalStrategy = require('passport-local');


const UserModel = require('../data/models/User');

const option = {
    usernameField: 'username',//'user[username]',
    passwordField: 'password',
};

const verifyCallback = (username, password, done) => {
    UserModel.findOne({ username })
        .then((user) => {
            if(!user || !user.validatePassword(password)) {
                return done(null, false, { errors: { 'username or password': 'is invalid' } });
            }

            return done(null, user);
        }).catch(done);
};

passport.use(new LocalStrategy(option, verifyCallback));