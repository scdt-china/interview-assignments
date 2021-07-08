"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.TinyUrlRoutes = void 0;
const tinyUrlController_1 = require("../controllers/tinyUrlController");
class TinyUrlRoutes {
    constructor() {
        this.tiny_url_controller = new tinyUrlController_1.TinyUrlController();
    }
    route(app) {
        app.get('/api/tiny_url/:tiny_url', (req, res) => {
            this.tiny_url_controller.get_tiny_url(req, res);
        });
        app.post('/api/tiny_url', (req, res) => {
            this.tiny_url_controller.create_tiny_url(req, res);
        });
    }
}
exports.TinyUrlRoutes = TinyUrlRoutes;
