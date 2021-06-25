//
//  CommonTextField.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//

import SwiftUI

struct CommonTextField: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
            configuration
                .padding(EdgeInsets(top: 20, leading: 10, bottom: 0, trailing: 10))
        }
}
