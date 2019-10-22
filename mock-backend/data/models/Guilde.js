const mongoose = require('mongoose');

const { Schema } = mongoose;

let GuildeSchema = new Schema({
    name : String,
    representative: {
        type: Schema.Types.ObjectId,
        ref: 'User'
    },
    points : Number
});

module.exports = mongoose.model('Guilde', GuildeSchema);