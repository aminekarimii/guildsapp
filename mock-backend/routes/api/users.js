const express = require('express');
const router = express.Router();
const passport = require('passport');
const auth = require('../../lib/auth');
const UserModel = require('../../data/models/User');


router.post('/', auth.optional, function(req, res, next) {
    const { body: { user } } = req;
    if(!user.username) return res.json({status: 422, message : 'username is required'});
    if(!user.password) return res.json({status: 422, message : 'password is required'});
    const model = new UserModel(user);
    model.setPassword(user.password);
    return model.save().then(() => res.json(model.toAuthJSON()));
});

router.post('/login', auth.optional, function (req, res, next) {
    const user = {...req.body};
    console.log(req.body);
    console.log(user);
    if(!user.username) return res.status(422).json({status: 422, message : 'Username is required'});
    if(!user.password) return res.status(422).json({status: 422, message : 'Password is required'});

    const authCallback = (err, passportUser, info) => {
        if(err) return res.status(422).json({status: 422, message : "Username or Password are incorrect"});

        if(!passportUser) return status(400).info;

        console.log(passportUser);
        const outUser = passportUser;
        outUser.token = passportUser.generateJWT();
        return res.json(outUser.toAuthJSON());
    };

    const authenticate = passport.authenticate('local', { session: false }, authCallback);

    return authenticate(req, res, next);
});

router.get('/current', auth.required, async (req, res, next) => {
    const { payload: { id } } = req;
    const user = await UserModel.findById(id).populate('guilde');
    return res.json(user)
});

router.get('/guilde/:guildeId', auth.optional, async (req, res, next) => {
    const guildeId = req.params.guildeId;
    const users = await UserModel.find({guilde: guildeId}).populate('guilde');
    return res.json(users);
});

module.exports = router;