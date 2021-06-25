//
//  HomeView.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var userAuth: UserModel
    
    var body: some View {
        VStack(alignment: .center){
            VStack{
                Text(userAuth.username.prefix(1).uppercased())
                    .frame(width:90, height: 90)
                    .clipShape(Circle())
                    .foregroundColor(.white)
                    .background(Color(red: 0.8, green: 0.8, blue: 0.8).cornerRadius(45))
                    .overlay(Circle().stroke(Color.white.opacity(0.7), lineWidth: 4))
                Text(userAuth.username).padding(.top, 4).foregroundColor(.white)
            }.padding(80).padding(.top, 40)
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                minHeight: 200,
                alignment: .center
            )
            .background(Color.green.cornerRadius(30, corners: [.bottomLeft, .bottomRight]))
            Spacer()
            Button("Logout") {
                onClickLogout()
            }.buttonStyle(GrayButton()).padding(40)
        }.edgesIgnoringSafeArea(.all)
    }
    func onClickLogout() {
        userAuth.logout()
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
