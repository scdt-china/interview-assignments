//
//  RegisterView.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import SwiftUI

struct RegisterView: View {
    
    @EnvironmentObject var store: Store
    
    var settings: AppState.Settings {
        store.appState.settings
    }
    
    var settingsBinding: Binding<AppState.Settings> {
        $store.appState.settings
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Name", text: settingsBinding.registerName)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            SecureField("Password", text: settingsBinding.registerPassword)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            SecureField("Repeat Password", text: settingsBinding.registerVerifyPassword)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            if self.store.appState.settings.registerRequesting {
                Text("注册中……")
            } else {
                
                Button {
                    self.store.dispatch(.register(name: self.settings.registerName, password: self.settings.registerPassword, verifyPassword: self.settings.registerVerifyPassword))
                } label: {
                    Text("Create Account")
                        .padding(20)
                }
                .contentShape(Rectangle())
                
            }
        }
        .alert(item: settingsBinding.registerError){ error in Alert(title: Text(error.localizedDescription))
        }
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        let store = Store()
        RegisterView().environmentObject(store)
    }
}
