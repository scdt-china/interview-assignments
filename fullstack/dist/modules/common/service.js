"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.mongoError = exports.insufficientParameters = exports.failureResponse = exports.successResponse = void 0;
const model_1 = require("./model");
function successResponse(message, DATA, res) {
    res.status(model_1.response_status_codes.success).json({
        STATUS: 'SUCCESS',
        MESSAGE: message,
        DATA
    });
}
exports.successResponse = successResponse;
function failureResponse(message, DATA, res) {
    res.status(model_1.response_status_codes.success).json({
        STATUS: 'FAILURE',
        MESSAGE: message,
        DATA
    });
}
exports.failureResponse = failureResponse;
function insufficientParameters(res) {
    res.status(model_1.response_status_codes.bad_request).json({
        STATUS: 'FAILURE',
        MESSAGE: 'Insufficient parameters',
        DATA: {}
    });
}
exports.insufficientParameters = insufficientParameters;
function mongoError(err, res) {
    res.status(model_1.response_status_codes.internal_server_error).json({
        STATUS: 'FAILURE',
        MESSAGE: 'MongoDB error',
        DATA: err
    });
}
exports.mongoError = mongoError;
