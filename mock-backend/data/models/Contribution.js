const mongoose = require('mongoose');

const { Schema } = mongoose;

let ContributionSchema = new Schema({
    type : String,
    description : String,
    points : Number
});

module.exports = mongoose.model('Contribution', ContributionSchema);