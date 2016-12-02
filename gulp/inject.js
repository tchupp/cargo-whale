'use strict';

var gulp = require('gulp'),
    plumber = require('gulp-plumber'),
    inject = require('gulp-inject'),
    es = require('event-stream'),
    bowerFiles = require('main-bower-files');

var handleErrors = require('./handle-errors');
var config = require('./config');


module.exports = {
    vendor: vendor/*,
    test: test*/
};


function vendor() {
    var stream = gulp.src(config.dist + 'index.html')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(inject(gulp.src(bowerFiles(), {read: false}), {
            name: 'bower',
            relative: false,
            transform: function (filepath) {
                if (filepath.indexOf('.css') !== -1) {
                    return '<link rel="stylesheet" href="' + filepath.replace('/src/main/webapp/', '') + '"/>'; // TODO temp hack
                } else {
                    return '<script src="' + filepath.replace('/src/main/webapp/', '') + '"></script>'; // TODO temp hack
                }
            }
        }))
        .pipe(gulp.dest(config.dist));

    return es.merge(stream, gulp.src(config.sassVendor)
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(inject(gulp.src(bowerFiles({filter: ['**/*.{scss,sass}']}), {read: false}), {
            name: 'bower',
            relative: true
        }))
        .pipe(gulp.dest(config.scssMainDir)));
}