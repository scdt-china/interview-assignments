//
//  Core.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation
import Combine

protocol AppCommand {
    func execute(in store: Store)
}

enum AppAction {
    case login(name: String, password: String)
    case accountBehaviorDone(result: Result<User, AppError>)
    case register(name: String, password: String, verifyPassword: String)
    case registerDone(result: Result<User, AppError>)
    case logout
    case navRegister
}

class Store: ObservableObject {
    
    @Published var appState = AppState()
    
    static func reduce (
        state: AppState,
        action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand?
        
        switch action {
        case .login(let name, let password):
            guard !appState.settings.loginRequesting else {
                break
            }
            appState.settings.loginRequesting = true
            appCommand = LoginAppCommand(name: name, password: password, registerUsers: appState.settings.registerUsers)
        case .register(let name, let password, let verifyPassword):
            guard !appState.settings.registerRequesting else {
                break
            }
            appState.settings.registerRequesting = true
            appCommand = RegisterAppCommand(name: name, password: password, verifyPassword: verifyPassword)
        case .accountBehaviorDone(let result):
            appState.settings.loginRequesting = false
            switch result {
            case .success(let user):
                appState.settings.loginUser = user
                appState.settings.accountBehavior = .postLogin
            case .failure(let error):
                appState.settings.loginError = error
            }
            
        case .registerDone(let result):
            appState.settings.registerRequesting = false
            switch result {
            case .success(let user):
                appState.settings.registerUser = user
                appState.settings.registerUsers.append(user)
                appState.settings.accountBehavior = .login
            case .failure(let error):
                appState.settings.registerError = error
            }
        case .logout:
            appState.settings.name = ""
            appState.settings.password = ""
            appState.settings.accountBehavior = .login
        case .navRegister:
            appState.settings.registerName = ""
            appState.settings.registerPassword = ""
            appState.settings.registerVerifyPassword = ""
            appState.settings.accountBehavior = .register
        }

        return (appState, appCommand)
    }
    
    func dispatch(_ action: AppAction) {
        let result = Store.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            command.execute(in: self)
        }
    }
    
}
