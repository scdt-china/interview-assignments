"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.response_status_codes = exports.ModificationNote = void 0;
exports.ModificationNote = {
    modified_on: Date,
    modified_by: String,
    modification_note: String
};
var response_status_codes;
(function (response_status_codes) {
    response_status_codes[response_status_codes["success"] = 200] = "success";
    response_status_codes[response_status_codes["bad_request"] = 400] = "bad_request";
    response_status_codes[response_status_codes["internal_server_error"] = 500] = "internal_server_error";
})(response_status_codes = exports.response_status_codes || (exports.response_status_codes = {}));
