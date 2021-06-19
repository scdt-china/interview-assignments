//
//  Domain.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation
import Combine

enum AppError: Error, Identifiable {
    case passwordWrong
    case passwordLessThanLimit
    case passwordNotMatch
    case nameWrong
    case emptyRegisteredUsers
    case noMatchRegisteredUsers

    var id: String {
        localizedDescription
    }
}

extension AppError: LocalizedError {
    var localizedDescription: String {
        switch self {
        case .nameWrong:
            return "用户名必须大于0"
        case .passwordWrong:
            return "密码错误"
        case .passwordLessThanLimit:
            return "密码必须大于8"
        case .passwordNotMatch:
            return "密码不匹配"
        case .emptyRegisteredUsers:
            return "请先注册"
        case .noMatchRegisteredUsers:
            return "用户名不存在"
        }
    }
}

struct User {
    var name: String
    var password: String
}

struct AppState {
    struct Settings {
        enum AccountBehavior: CaseIterable {
            case register
            case login
            case postLogin
        }
        
        var accountBehavior = AccountBehavior.login
        var name = ""
        var password = ""
        var loginUser: User?
        var loginRequesting = false
        var loginError: AppError?
        
        
        var registerName = ""
        var registerPassword = ""
        var registerVerifyPassword = ""
        var registerRequesting = false
        var registerUser: User?
        var registerError: AppError?
        
        var registerUsers: [User] = []
    }
    
    var settings = Settings()
    var subscriptions = Set<AnyCancellable>()
}

