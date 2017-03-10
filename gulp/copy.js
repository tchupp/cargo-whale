'use strict';

var gulp = require('gulp'),
    rev = require('gulp-rev'),
    flatten = require('gulp-flatten'),
    plumber = require('gulp-plumber'),
    es = require('event-stream'),
    changed = require('gulp-changed');

var handleErrors = require('./handle-errors');
var config = require('./config');

module.exports = {
    html: html,
    fonts: fonts,
    common: common,
    deps: deps
};


function html() {
    return gulp.src([config.app + '/**/*.html', '!' + config.bower + '/**'])
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist))
        .pipe(gulp.dest(config.dist));
}

function fonts() {
    return es.merge(
        gulp.src(config.app + 'content/**/*.{woff,woff2,svg,ttf,eot,otf}')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.dist + 'content/fonts/'))
            .pipe(flatten())
            .pipe(rev())
            .pipe(gulp.dest(config.dist + 'content/fonts/'))
            .pipe(rev.manifest(config.revManifest, {
                base: config.dist,
                merge: true
            }))
            .pipe(gulp.dest(config.dist))
    );
}

function common() {
    return gulp.src([config.app + 'robots.txt', config.app + 'favicon.ico', config.app + '.htaccess'], { dot: true })
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist))
        .pipe(gulp.dest(config.dist));
}

function deps() {
    return gulp.src([
        'node_modules/core-js/client/shim.min.js',
        'node_modules/zone.js/dist/zone.js',
        'node_modules/reflect-metadata/Reflect.js',
        'node_modules/systemjs/dist/system.js',
        'node_modules/@angular/**/*.js',
        'node_modules/@ng-bootstrap/ng-bootstrap/**/*.js',
        'node_modules/rxjs/**/*.js',
        'node_modules/ng2-webstorage/bundles/**/*.js',
        'node_modules/ts-md5/dist/**/*.js',
        'node_modules/jquery/dist/*.js'
    ], {base: 'node_modules'})
        .pipe(gulp.dest(config.dist + 'vendor'));
}
