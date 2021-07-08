"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PublicRoutes = void 0;
class PublicRoutes {
    route(app) {
        app.get('/tiny_url', (req, res) => {
            res.status(200).json({ message: "Get request successfull" });
        });
        app.post('/tiny_url', (req, res) => {
            res.status(200).json({ message: "Post request successfull" });
        });
    }
}
exports.PublicRoutes = PublicRoutes;
