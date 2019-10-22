const express = require('express');
const router = express.Router();
const auth = require('../../lib/auth');
const ContributionModel = require('../../data/models/Contribution');


router.post('/add', auth.optional, async function(req, res, next) {
    const contribution = {...req.body};
    const model = new ContributionModel(contribution);
    return model.save().then(() => res.json(model));
});


// router.post('/add', auth.required, async function(req, res, next) {
//
//     const { payload: { id } } = req;
//     const Contribution = {...req.body};
//
//     const mContributions = new ContributionModel(Contribution);
//     mContributions.complete(id);
//
//     return mContributions.save().then(() => res.json(mContributions));
//
// });
//
// router.get('/validate/:id', auth.required, async function(req, res, next) {
//
//     const id = req.params.id;
//
//     const contribution = await ContributionModel.findById(id);
//     contribution.valider();
//     const out = await ContributionModel.replaceOne({ _id: id }, contribution);
//
//     return res.json(out)
// });
//
// router.get('/', auth.required, async function(req, res, next) {
//
//     const { payload: { id } } = req;
//
//     const user = await UserModel.findById(id);
//     if(!user) return res.json({status: 400, message : 'There is no user'});
//
//     const userContributions = await ContributionModel.find({ user_id : id });
//     if(!userContributions) return res.json({status: 400, message : 'There is no Contributions'});
//
//     return res.json(userContributions);
//
// });

module.exports = router;