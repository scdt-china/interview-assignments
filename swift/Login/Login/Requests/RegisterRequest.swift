//
//  RegisterRequest.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import Foundation
import Combine

struct RegisterRequest {
    let name: String
    let password: String
    let verifyPassword: String
    
    var publisher: AnyPublisher<User, AppError> {
        Future<User, AppError> { promise in
            DispatchQueue.global().asyncAfter(deadline: .now() + 1) {
                guard name.count > 0 else {
                    promise(.failure(.nameWrong))
                    return
                }
                
                guard password.count > 0 else {
                    promise(.failure(.passwordLessThanLimit))
                    return
                }
                
                guard self.password == self.verifyPassword else {
                    promise(.failure(.passwordNotMatch))
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
