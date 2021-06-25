//
//  LoginAndRegisterView.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI
import Combine

struct LoginAndRegisterView: View {
    @EnvironmentObject var userAuth: UserModel
    
    var body: some View {
        VStack(alignment: .leading, content:{
            TextField("Name", text: $userAuth.username)
                .textFieldStyle(CommonTextField())
                .autocapitalization(.none)
            Divider()
            SecureField("Password", text: $userAuth.password)
                .textFieldStyle(CommonTextField())
            if userAuth.isRegister {
                Divider()
                SecureField("Repeat Password", text: $userAuth.repeatPassword)
                    .textFieldStyle(CommonTextField())
            }
            
            Divider()
            if !$userAuth.isRegister.wrappedValue {
                HStack(content: {
                    Spacer()
                    Button(action: userAuth.onClickCreateAccount){
                        Text("Create Account").foregroundColor(.gray)
                    }
                }).padding(.top, 10)
            }
            
            Button(action: userAuth.onClickConfirm) {
                Text($userAuth.isRegister.wrappedValue ? "Create Account" : "Login")
            }.padding(.top, 60)
            .disabled($userAuth.isDisable.wrappedValue)
            .buttonStyle(GreenButton())
            Spacer()
        })
        .padding(30).padding(.top, 150)
        .alert(isPresented: $userAuth.showErrorDialog, content: {
            Alert(title: Text("Tips"),
                  message: Text($userAuth.errorMessage.wrappedValue),
                  dismissButton: .default(Text("ok")))
        })
    }
}

struct LoginAndRegisterView_Previews: PreviewProvider {
    static var previews: some View {
        LoginAndRegisterView()
    }
}
