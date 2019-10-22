const mongoose = require('mongoose');
const { Schema } = mongoose;

let SubmissionSchema = new Schema({
        subject : String,
        validated :{
            type: Boolean,
            default: false
        },
        contribution: {
            type: Schema.Types.ObjectId,
            ref: 'Contribution'
        },
        createdBy: {
            type: Schema.Types.ObjectId,
            ref: 'User'
        },
        startAt : Date,
    },
    { timestamps: true }
);

module.exports = mongoose.model('Submission', SubmissionSchema);

