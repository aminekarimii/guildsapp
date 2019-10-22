const express = require('express');
const router = express.Router();
const auth = require('../../lib/auth');
const SubmissionModel = require('../../data/models/Submission');
const UserModel = require('../../data/models/User');
const GuildeModel = require('../../data/models/Guilde');
const ContributionModel = require('../../data/models/Contribution');

const submissionPopulateConfig = {
    path : 'contribution createdBy',
    populate : {
        path : 'guilde'
    }
};

router.post('/add', auth.required, async function(req, res, next) {
    const { payload: { id } } = req;
    const submission = {...req.body};
    submission.createdBy = id;
    const model = new SubmissionModel(submission);
    return model.save().then((doc) => res.json(doc));
});

router.get('/details/:id', auth.required, async function(req, res, next) {
    const id = req.params.id;
    const submission = await SubmissionModel.findById(id).populate(submissionPopulateConfig);
    return res.json(submission)
});

router.get('/validate/:id', auth.required, async function(req, res, next) {
    // check admin
    const submissionId = req.params.id;
    const oldSubmission = await SubmissionModel.findOneAndUpdate({_id : submissionId}, {validated : true});

    if(!oldSubmission.validated) {
        const {createdBy : userId} = await SubmissionModel.findById(submissionId);
        const {guilde : guildeId} = await UserModel.findById(userId);
        const {points : contributionPoints} =  await ContributionModel.findById(oldSubmission.contribution);
        const oldGuilde = await GuildeModel.findOneAndUpdate({_id : guildeId}, { $inc : {points : contributionPoints}});
    }

    const newSubmission = await SubmissionModel.findById(submissionId).populate(submissionPopulateConfig);
    return res.json(newSubmission)
});

router.get('/me', auth.required, async function(req, res, next) {
    const { payload: { id } } = req;
    const submission = await SubmissionModel.find({createdBy : id}).populate(submissionPopulateConfig);
    return res.json(submission)
});

module.exports = router;