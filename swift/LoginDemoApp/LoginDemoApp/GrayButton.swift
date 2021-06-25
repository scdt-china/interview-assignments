//
//  GrayButton.swift
//  LoginDemoApp
//
//  Created by kim on 2021/5/27.
//

import SwiftUI

struct GrayButton: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        HStack {
            Spacer()
            configuration.label.foregroundColor(.red)
            Spacer()
        }
        .padding()
        .background(Color(red: 0.9, green: 0.9, blue: 0.9).cornerRadius(16))
        .scaleEffect(configuration.isPressed ? 0.95 : 1)
    }
}
