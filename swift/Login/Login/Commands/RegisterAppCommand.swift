//
//  RegisterAppCommand.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation

struct RegisterAppCommand: AppCommand {
    let name: String
    let password: String
    let verifyPassword: String
    
    func execute(in store: Store) {
        RegisterRequest(name: name, password: password, verifyPassword: verifyPassword)
            .publisher
            .sink { complete in
                if case .failure(let error) = complete {
                    store.dispatch(.registerDone(result: .failure(error)))
                }
            } receiveValue: { user in
                store.dispatch(.registerDone(result: .success(user)))
            }
            .store(in: &store.appState.subscriptions)
    }
}
