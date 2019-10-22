const mongoose = require('mongoose');
const crypto = require('crypto');
const jwt = require('jsonwebtoken');

const { Schema } = mongoose;

let UserSchema = new Schema({
    username: String,
    hash: String,
    salt: String,
    firstname : String,
    lastname : String,
    site : String,
    hired_date : String,
    isAdmin: {
        type: Boolean,
        default: false
    },
    guilde: {
        type: Schema.Types.ObjectId,
        ref: 'Guilde'
    }
});

UserSchema.methods.setPassword = function(password) {
    this.salt = crypto.randomBytes(16).toString('hex');
    this.hash = crypto.pbkdf2Sync(password, this.salt, 10000, 512, 'sha512').toString('hex');
};

UserSchema.methods.validatePassword = function(password) {
    const hash = crypto.pbkdf2Sync(password, this.salt, 10000, 512, 'sha512').toString('hex');
    return this.hash === hash;
};

UserSchema.methods.generateJWT = function() {
    const today = new Date();
    const expirationDate = new Date(today);
    expirationDate.setDate(today.getDate() + 60);

    return jwt.sign({
        username: this.username,
        id: this._id,
        exp: parseInt(expirationDate.getTime() / 1000, 10),
    }, 'secret');
};

UserSchema.methods.toAuthJSON = function() {
    return {
        _id: this._id,
        username: this.username,
        token: this.generateJWT(),
    };
};

UserSchema.methods.toJSON = function() {
    return Object.assign({}, this._doc, {
        hash: undefined,
        salt: undefined
    });
};

module.exports = mongoose.model('User', UserSchema);