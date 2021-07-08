"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const schema_1 = require("./schema");
class TinyUrlService {
    createUser(tiny_url_params, callback) {
        const _session = new schema_1.default(tiny_url_params);
        _session.save(callback);
    }
    filterUser(query, callback) {
        schema_1.default.findOne(query, callback);
    }
    updateUser(tiny_url_params, callback) {
        const query = { _id: tiny_url_params._id };
        schema_1.default.findOneAndUpdate(query, tiny_url_params, callback);
    }
    deleteUser(_id, callback) {
        const query = { _id: _id };
        schema_1.default.deleteOne(query, callback);
    }
}
exports.default = TinyUrlService;
