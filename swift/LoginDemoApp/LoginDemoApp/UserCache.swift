//
//  UserCache.swift
//  LoginDemoApp
//
//  Created by kim on 2021/6/3.
//

import Foundation
import Combine

protocol UserCache {
    func addUser(_ username: String, _ password: String)
    func getUser(_ username: String, _ password: String) -> User?
}

class DefaultUserCache : UserCache {
    
    private let cachedUsers = CurrentValueSubject<[User], Never>([])
    
    init() {
        let decoder = JSONDecoder()
        let defaults = UserDefaults.standard
        guard let saved = defaults.object(forKey: "users") as? Data,
              let users = try? decoder.decode([User].self, from: saved) else {
            return
        }
        cachedUsers.send(users)
    }
    
    func addUser(_ username: String, _ password: String) {
        let user = User(username: username, password: password)
        var current = cachedUsers.value
        current.append(user)
        cachedUsers.send(current)
        save()
    }
    
    private func save() {
        let encoder = JSONEncoder()
        if let coded = try? encoder.encode(cachedUsers.value) {
            let defaults = UserDefaults.standard
            defaults.setValue(coded, forKey: "users")
        }
    }
    
    func getUser(_ username: String, _ password: String) -> User? {
        let user = cachedUsers.value.compactMap{
            $0.username == username && $0.password == password ? $0 : nil
        }
        return user.first
    }
    
    
}
