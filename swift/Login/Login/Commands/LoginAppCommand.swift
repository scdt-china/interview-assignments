//
//  LoginAppCommand.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation

struct LoginAppCommand: AppCommand {
    let name: String
    let password: String
    let registerUsers: [User]
    
    func execute(in store: Store) {
        LoginRequest(name: name, password: password, registerUsers: registerUsers)
            .publisher
            .sink { complete in
                if case .failure(let error) = complete {
                    store.dispatch(.accountBehaviorDone(result: .failure(error)))
                }
            } receiveValue: { user in
                store.dispatch(.accountBehaviorDone(result: .success(user)))
            }
            .store(in: &store.appState.subscriptions)
    }
}



