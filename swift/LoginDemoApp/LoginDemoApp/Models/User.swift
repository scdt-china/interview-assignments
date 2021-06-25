//
//  User.swift
//  LoginDemoApp
//
//  Created by kim on 2021/6/3.
//

import Foundation

struct User : Decodable, Encodable, Equatable{
    let username: String
    let password: String
}
