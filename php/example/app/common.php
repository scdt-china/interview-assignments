<?php
// 应用公共文件

function show($code, $msg = 'error', $data = [], $httpStatus = 200) {
    $result = [
        'code' => $code,
        'msg' => $msg,
        'data' => $data
    ];
    return json($result, $httpStatus);
}


function encrypt($str){
    return md5(config("setting.AUTH_CODE").$str);
}