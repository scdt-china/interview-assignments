//
//  PostLoginView.swift
//  Login
//
//  Created by Chen, Aaron on 2021/6/16.
//

import SwiftUI

struct PostLoginView: View {
    
    @EnvironmentObject var store: Store
    
    var settings: AppState.Settings {
        store.appState.settings
    }
    
    var settingsBinding: Binding<AppState.Settings> {
        $store.appState.settings
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(settings.name)
                .textFieldStyle(RoundedBorderTextFieldStyle())
        Button {
            self.store.dispatch(.logout)
        } label: {
            Text("Logout")
        }
        .contentShape(Rectangle())
        }
    }
}

struct PostLoginView_Previews: PreviewProvider {
    static var previews: some View {
        let store = Store()
        store.appState.settings.name = "abc"
        return PostLoginView().environmentObject(store)
    }
}
