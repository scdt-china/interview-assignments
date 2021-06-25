//
//  UserAuth.swift
//  DemoApp
//
//  Created by kim on 2021/5/26.
//
import Combine
import Foundation

class UserModel: ObservableObject {
    @Published var user: User? = nil
    @Published var isRegister = false
    @Published var isDisable = true
    @Published var showErrorDialog = false
    @Published var username = ""
    @Published var password = ""
    @Published var repeatPassword = ""
    @Published var errorMessage = ""
    
    private var cancellableSet: Set<AnyCancellable> = []
    private var userCache = DefaultUserCache()
    
    private var isUsernameValidPublisher: AnyPublisher<Bool, Never> {
        $username.map { input in
          return input.count >= 6
        }.eraseToAnyPublisher()
    }
    
    private var isPasswordEnablePublisher: AnyPublisher<Bool, Never> {
        $password.map { input in
          return input.count > 0
        }.eraseToAnyPublisher()
    }
    
    private var isRepeatPasswordEnablePublisher: AnyPublisher<Bool, Never> {
        $repeatPassword.map { input in
          return input.count > 0
        }.eraseToAnyPublisher()
    }
    
    private var isPasswordLengthValidPublisher: AnyPublisher<Bool, Never> {
        $password.map { input in
            input.count > 8
        }.eraseToAnyPublisher()
    }
    
    private var isPasswordSamePublisher: AnyPublisher<Bool, Never> {
        $repeatPassword
        .map {repeatPassword in
            self.password == repeatPassword
        }
        .eraseToAnyPublisher()
    }
    
    private var isInputValidPublisher: AnyPublisher<Bool, Never> {
      Publishers.CombineLatest3(isUsernameValidPublisher, isPasswordEnablePublisher, isRepeatPasswordEnablePublisher)
        .map { userNameIsValid, passwordIsValid, repeatPassword in
            self.isRegister ? userNameIsValid && passwordIsValid && repeatPassword : userNameIsValid && passwordIsValid
        }
      .eraseToAnyPublisher()
    }
    
    init() {
        isUsernameValidPublisher
          .receive(on: RunLoop.main)
          .map { valid in
            valid ? "" : "the length of username should more than 3 characters"
          }
          .assign(to: \.errorMessage, on: self)
          .store(in: &cancellableSet)
        
        isPasswordLengthValidPublisher.receive(on: RunLoop.main)
            .map { valid in
                valid ? "" : "the length of password should more than 8 characters"
            }.assign(to: \.errorMessage, on: self)
            .store(in: &cancellableSet)
        
        
        isPasswordSamePublisher.receive(on: RunLoop.main)
            .map { valid in
                valid || !self.isRegister ? "" : "the repeat password doesn't match"
            }.assign(to: \.errorMessage, on: self)
            .store(in: &cancellableSet)
        
        // check length
        isInputValidPublisher
            .receive(on: RunLoop.main)
            .map {
                valid in
                !valid
            }
            .assign(to: \.isDisable, on: self)
            .store(in: &cancellableSet)
        
    }
    func logout() {
        self.user = nil
    }
    func onClickConfirm() {
        if errorMessage.isEmpty {
            if isRegister {
                register()
                resetToLogin()
            } else {
                login().sink { user in
                    self.user = user
                    self.errorMessage = user == nil ? "username or password is invalidate" : ""
                    self.showErrorDialog = !self.errorMessage.isEmpty
                    if user != nil {
                        self.clear()
                    }
                }.store(in: &cancellableSet)
            }
        } else {
            showErrorDialog = true
        }
    }
    
    func onClickCreateAccount() {
        isRegister = true
        password = ""
    }
    
    func resetToLogin() {
        isRegister = false
        password = ""
        repeatPassword = ""
    }
    
    private func clear() {
        self.password = ""
        self.repeatPassword = ""
    }
    
    private func login() -> AnyPublisher<User?, Never>{
        self.user = userCache.getUser(username, password)
        return Just(user).eraseToAnyPublisher()
    }
    
    private func register() {
        userCache.addUser(username, password)
    }
   
}
