const express = require('express');
const router = express.Router();

router.get('/', function(req, res, next) {
    res.send('respond with a resource');
});

router.use('/users', require('./users'));

router.use('/guildes', require('./guildes'));

router.use('/contributions', require('./contributions'));

router.use('/submissions', require('./submissions'));

module.exports = router;