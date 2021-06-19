//
//  ContentView.swift
//  Login2
//
//  Created by Chen, Aaron on 2021/6/16.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var store: Store
    var body: some View {
        switch store.appState.settings.accountBehavior {
        case .login:
            LoginView()
        case .register:
            RegisterView()
        case .postLogin:
            PostLoginView()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(Store())
    }
}
