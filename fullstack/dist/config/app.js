"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const environment_1 = require("../environment");
const common_routes_1 = require("../routes/common_routes");
const tinyurl_routes_1 = require("../routes/tinyurl_routes");
const user_routes_1 = require("../routes/user_routes");
class App {
    constructor() {
        this.mongoUrl = 'mongodb://localhost/' + environment_1.default.getDBName();
        this.common_routes = new common_routes_1.CommonRoutes();
        this.tinyurl_routes = new tinyurl_routes_1.TinyUrlRoutes();
        this.user_routes = new user_routes_1.UserRoutes();
        this.app = express();
        this.config();
        this.mongoSetup();
        this.tinyurl_routes.route(this.app);
        this.user_routes.route(this.app);
        this.common_routes.route(this.app);
    }
    config() {
        // support application/json type post data
        this.app.use(bodyParser.json());
        //support application/x-www-form-urlencoded post data
        this.app.use(bodyParser.urlencoded({ extended: false }));
    }
    mongoSetup() {
        mongoose.connect(this.mongoUrl, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
            useCreateIndex: true,
            useFindAndModify: false
        });
    }
}
exports.default = new App().app;
