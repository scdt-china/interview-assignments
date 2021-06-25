//
//  LoginDemoAppApp.swift
//  LoginDemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI

@main
struct LoginDemoAppApp: App {
    
    @StateObject var userAuth = UserModel()
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(userAuth)
        }
    }
}
