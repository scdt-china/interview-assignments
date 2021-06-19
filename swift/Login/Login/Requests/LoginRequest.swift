//
//  LoginRequest.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation
import Combine

struct LoginRequest {
    let name: String
    let password: String
    let registerUsers: [User]
    
    var publisher: AnyPublisher<User, AppError> {
        Future<User, AppError> { promise in
            DispatchQueue.global().asyncAfter(deadline: .now() + 1) {
                guard registerUsers.count > 0 else {
                    promise(.failure(.emptyRegisteredUsers))
                    return
                }
                
                guard let findUser = registerUsers.first(where: { (user) -> Bool in
                    user.name == name
                }) else {
                    promise(.failure(.noMatchRegisteredUsers))
                    return
                }
                
                guard self.password == findUser.password else {
                    promise(.failure(.passwordWrong))
                    return
                }
                
                let user = User(name: self.name, password: self.password)
                promise(.success(user))
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}
