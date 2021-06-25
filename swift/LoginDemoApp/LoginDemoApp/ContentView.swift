//
//  ContentView.swift
//  LoginDemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var userAuth: UserModel
    
    var body: some View {
        if userAuth.user != nil {
            HomeView()
        } else {
            LoginAndRegisterView()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
