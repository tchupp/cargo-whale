'use strict';

var gulp = require('gulp'),
    expect = require('gulp-expect-file'),
    sass = require('gulp-sass'),
    templateCache = require('gulp-angular-templatecache'),
    htmlmin = require('gulp-htmlmin'),
    ngConstant = require('gulp-ng-constant'),
    rename = require('gulp-rename'),
    eslint = require('gulp-eslint'),
    es = require('event-stream'),
    flatten = require('gulp-flatten'),
    del = require('del'),
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    plumber = require('gulp-plumber'),
    changed = require('gulp-changed'),
    ts = require('gulp-typescript'),
    sourcemaps = require('gulp-sourcemaps'),
    tslint = require('gulp-tslint');

var handleErrors = require('./gulp/handle-errors'),
    serve = require('./gulp/serve'),
    util = require('./gulp/utils'),
    copy = require('./gulp/copy'),
    inject = require('./gulp/inject');

var tsProject = ts.createProject('tsconfig.json');
var config = require('./gulp/config');


gulp.task('clean', function () {
    return del([config.dist], {dot: true});
});


gulp.task('copy', [
    'copy:fonts',
    'copy:html',
    'copy:common',
    'copy:deps'
]);

gulp.task('copy:html', copy.html);

gulp.task('copy:fonts', copy.fonts);

gulp.task('copy:deps', copy.deps);

gulp.task('copy:temp', function () {
    return gulp.src([config.app + '/**/*', '!' + config.app + '/**/*.ts', '!' + config.sassSrc])
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(changed(config.dist))
        .pipe(gulp.dest(config.dist));
});


gulp.task('sass', function () {
    return es.merge(
        gulp.src(config.sassSrc)
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(expect(config.sassSrc))
            .pipe(changed(config.cssDir, {extension: '.css'}))
            .pipe(sass({includePaths: config.bower}).on('error', sass.logError))
            .pipe(gulp.dest(config.cssDir)),
        gulp.src(config.bower + '**/fonts/**/*.{woff,woff2,svg,ttf,eot,otf}')
            .pipe(plumber({errorHandler: handleErrors}))
            .pipe(changed(config.app + 'content/fonts'))
            .pipe(flatten())
            .pipe(gulp.dest(config.app + 'content/fonts'))
    );
});


gulp.task('styles', ['sass'], function () {
    return gulp.src(config.app + 'content/css')
        .pipe(browserSync.reload({stream: true}));
});


gulp.task('tscompile', function () {
    return gulp.src([config.app + 'app/**/*.ts'])
        .pipe(sourcemaps.init())
        .pipe(tsProject())
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(config.dist + 'app'))
        .pipe(browserSync.reload({stream: true}));
});


gulp.task('inject', ['tscompile', 'inject:dep']);

gulp.task('inject:dep', [/*'inject:test', */'inject:vendor']);

gulp.task('inject:vendor', inject.vendor);

// gulp.task('inject:test', inject.test);


gulp.task('html', function () {
    return gulp.src(config.app + 'app/**/*.html')
    .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(templateCache({
            module: 'cargoWhaleDockerApp',
            root: 'app/',
            moduleSystem: 'IIFE'
        }))
        .pipe(gulp.dest(config.tmp));
});


gulp.task('ngconstant:dev', function () {
    return ngConstant({
        name: 'cargoWhaleDockerApp.common',
        constants: {
            VERSION: util.parseVersion(),
            DEBUG_INFO_ENABLED: true
        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.ts'))
        .pipe(gulp.dest(config.app + 'app/'));
});

gulp.task('ngconstant:prod', function () {
    return ngConstant({
        name: 'cargoWhaleDockerApp.common',
        constants: {
            VERSION: util.parseVersion(),
            DEBUG_INFO_ENABLED: false
        },
        template: config.constantTemplate,
        stream: true
    })
        .pipe(rename('app.constants.ts'))
        .pipe(gulp.dest(config.app + 'app/'));
});


gulp.task('eslint', function () {
    return gulp.src(['gulpfile.js', config.app + 'app/**/*.js'])
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(eslint())
        .pipe(eslint.format())
        .pipe(eslint.failOnError());
});

gulp.task('eslint:fix', function () {
    return gulp.src(config.app + 'app/**/*.js')
        .pipe(plumber({errorHandler: handleErrors}))
        .pipe(eslint({
            fix: true
        }))
        .pipe(eslint.format())
        .pipe(gulpIf(util.isLintFixed, gulp.dest(config.app + 'app')));
});


gulp.task('tslint', function () {
    return gulp.src('app/**/*.ts')
        .pipe(tslint())
        .pipe(tslint.report('verbose'));
});


gulp.task('watch', function () {
    gulp.watch('bower.json', ['install']);
    gulp.watch(['gulpfile.js', 'pom.xml'], ['ngconstant:dev']);
    gulp.watch(config.sassSrc, ['styles']);
    gulp.watch(config.app + 'content/images/**', ['images']);
    gulp.watch(config.app + 'app/**/*.ts', ['tscompile']);
    gulp.watch(config.app + 'app/**/*.html', ['copy:html']);
    gulp.watch([config.dist + '*.html', config.dist + 'app/**']).on('change', browserSync.reload);
});

gulp.task('install', ['clean'], function () {
    runSequence(
        'copy:temp',
        ['inject:dep', 'ngconstant:dev', 'copy:deps'],
        'sass',
        'tscompile');
});

gulp.task('serve', ['install'], serve);

gulp.task('build', ['clean'], function (cb) {
    runSequence(['copy', 'inject:vendor', 'ngconstant:prod'], 'tscompile', 'assets:prod', cb);
});

gulp.task('default', ['serve']);
