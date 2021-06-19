//
//  LoginView.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import SwiftUI

struct LoginView: View {
    
    @EnvironmentObject var store: Store
    
    var settings: AppState.Settings {
        store.appState.settings
    }
    
    var settingsBinding: Binding<AppState.Settings> {
        $store.appState.settings
    }
    
    var isLoginButtonInvalid: Bool {
        settings.name.count == 0 || settings.password.count == 0
    }

    var body: some View {
        VStack(alignment: .center) {
            // name, password
            TextField("Name", text: settingsBinding.name)
                .textFieldStyle(PlainTextFieldStyle())
            Divider()
                .modifier(DividerModifier())
            SecureField("Password", text: settingsBinding.password)
                .textFieldStyle(PlainTextFieldStyle())
                .padding(.top, 15)
            Divider()
                .modifier(DividerModifier())
            
            // register
            HStack(spacing: 12) {
                Spacer()
                Button {
                    withAnimation {
                        self.store.dispatch(.navRegister)
                                }
                } label: {
                    Text("Create Account")
                        .padding(10)
                        .foregroundColor(Color.gray)
                        .font(.subheadline)
                }
            }
            
            // login button
            if settings.loginRequesting {
                Text("登陆中……")
            } else {
                Button() {
                    store.dispatch(.login(name: settings.name, password: settings.password))
                } label: {
                    Text("Login")
                        .modifier(ButtonModifier(background: isLoginButtonInvalid ? Color("lightgray") : Color.green ))
                }
            }
        }
        .padding()
        .alert(item: settingsBinding.loginError){ error in Alert(title: Text(error.localizedDescription))
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        let store = Store()
        LoginView().environmentObject(store)
    }
}

extension LoginView {
    
    struct ButtonModifier: ViewModifier {
        let background: Color
        func body(content: Content) -> some View {
            content
                .padding(20)
                .foregroundColor(Color.white)
                .cornerRadius(3)
                .frame(width: 250, height: 44, alignment: .center)
                .background(background)
                .padding(.top, 50)
        }
    }
    
    struct DividerModifier: ViewModifier {
        func body(content: Content) -> some View {
            content
                .frame(height: 1)
                .background(Color.gray)
        }
    }
    
}
