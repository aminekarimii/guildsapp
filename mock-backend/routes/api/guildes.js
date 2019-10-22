const express = require('express');
const router = express.Router();
const auth = require('../../lib/auth');
const UserModel = require('../../data/models/User');
const GuildeModel = require('../../data/models/Guilde');

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

router.post('/add', auth.optional, async function(req, res, next) {
    const guilde = {...req.body};
    //if(!guilde.name) return res.status(422).json({status: 422, message : 'Name is required'});
    const model = new GuildeModel(guilde);
    return model.save().then((doc) => res.json(doc));
});

router.get('/details/:id', auth.required, async function(req, res, next) {
    const id = req.params.id;
    const guilde = await GuildeModel.findById(id);
    return res.json(guilde)
});

router.get('/current', auth.required, async function(req, res, next) {
    const { payload: { id } } = req;
    const user = await UserModel.findById(id).populate('guilde');
    return res.json(user.guilde)
});

router.get('/top', auth.required, async function(req, res, next) {
    await sleep(1000);
    const topGuildes = await GuildeModel.find({}).sort("-points");
    return res.json(topGuildes);
});

module.exports = router;