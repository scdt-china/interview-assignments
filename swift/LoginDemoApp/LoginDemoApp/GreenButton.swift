//
//  GreenButton.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI

struct GreenButton: ButtonStyle {
    
    @Environment(\.isEnabled) private var isEnabled: Bool
    func makeBody(configuration: Configuration) -> some View {
        HStack {
              Spacer()
              configuration.label.foregroundColor(.white)
              Spacer()
        }
        .padding()
        .background(!isEnabled ? Color.gray.cornerRadius(16) : Color(red: 0, green: 0.5, blue: 0).cornerRadius(16))
        .scaleEffect(configuration.isPressed ? 0.95 : 1)
    }
}
