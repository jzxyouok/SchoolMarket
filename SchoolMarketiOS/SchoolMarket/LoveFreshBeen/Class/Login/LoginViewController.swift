//
//  LoginViewController.swift
//  LoveFreshBeen
//  登录界面
//  Created by Dora on 16/5/3.
//  Copyright © 2016年 tianzhongtao. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
class LoginViewController: BaseNavigationController {
    
    private var bacImage:UIImageView?
    private var logoView:UIView?
    private var textFieldView:UIView?
    private var shareView:UIView?
    private var mobileField:UITextField!
    private var passwordField:UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationBar.hidden = true
        createBacImage()
        createLogoView()
        createTextFieldView()
        createSignUpAndShareView()
    }
    
    func createLogoView()
    {
        let marTop = (50/667.0)*view.height
        let marWidth = (217/375.0)*0.75*view.width
        let marHeight = (238/667.0)*0.75*view.height
        logoView = UIView(frame: CGRectMake(0, marTop, view.width, marHeight))
        view.addSubview(logoView!)
        
        let logoImgView = UIImageView(frame: CGRectMake((view.width-marWidth)/2, 0, marWidth, marHeight))
        logoImgView.image = UIImage(named: "scnuLogo")
        logoView?.addSubview(logoImgView)
    }
    
    func createBacImage()
    {
        view.backgroundColor = UIColor.clearColor()
        bacImage = UIImageView(frame: CGRectMake(0, 0, view.width, view.height))
        bacImage?.image = UIImage(named: "BacImage")
        view.addSubview(bacImage!)
    }
    
    func createTextFieldView()
    {
        let marTop = (275/667.0)*view.height
        let marTextHeight = (50/667.0)*view.height
        textFieldView = UIView(frame: CGRectMake(0, marTop, view.width, marTextHeight*2+70))
        view.addSubview(textFieldView!)
        
        //背景View
        let marLeft = (50/667.0)*view.width
        let bacView = UIView(frame: CGRectMake(marLeft, 0, view.width-marLeft*2, marTextHeight*2))
        bacView.backgroundColor = UIColor.whiteColor()
        bacView.layer.cornerRadius = 5.0
        textFieldView?.addSubview(bacView)
        
        //分割线
        let mar = (15/667.0)*view.width
        let lineView = UIView(frame: CGRectMake(marLeft+mar, marTextHeight, view.width-marLeft*2-mar*2, 1))
        lineView.backgroundColor = UIColor(red:241/255.0, green:243/255.0 ,blue:247/255.0 ,alpha:1)
        textFieldView?.addSubview(lineView)
        
        //两个TextField
        mobileField = UITextField(frame: CGRectMake(mar, 0, view.width-mar*2, marTextHeight))
        mobileField.placeholder = "手机号码"
        bacView.addSubview(mobileField)
        passwordField = UITextField(frame:CGRectMake(mar,marTextHeight,view.width-mar*2,marTextHeight))
        passwordField.secureTextEntry = true
        passwordField.placeholder = "密码"
        mobileField.tag = 10001
        passwordField.tag = 10002
        bacView.addSubview(passwordField)
        
        //登陆Btn
        let marBtnHeight = marTextHeight
        let loginBtn = UIButton(frame: CGRectMake(marLeft, marTextHeight*2+20, view.width-marLeft*2, marBtnHeight))
        loginBtn.layer.cornerRadius = 5.0
        loginBtn.setTitle("登陆", forState: UIControlState.Normal)
        loginBtn.backgroundColor = UIColor(red:100/255.0 ,green:45/255.0 ,blue:60/255.0 ,alpha:0.75)
        textFieldView?.addSubview(loginBtn)
        loginBtn.addTarget(self, action: #selector(LoginViewController.loginBtnOnClick), forControlEvents: UIControlEvents.TouchDown)
        
    }
    
    func createSignUpAndShareView()
    {
        let marHeight = (70/667.0)*view.height
        let marBottom = ((667.0-120)/667.0)*view.height
        shareView = UIView(frame: CGRectMake(0, marBottom, view.width, marHeight))
        view.addSubview(shareView!)
        
        let btnWidth = (300/667.0)*view.width
        let signUpBtn = UIButton(frame: CGRectMake((view.width-btnWidth)/2, 0, btnWidth, 40))
        signUpBtn.setTitle("注册SchoolMarket账户", forState: UIControlState.Normal)
        signUpBtn.backgroundColor = UIColor.clearColor()
        signUpBtn.titleLabel?.textAlignment = NSTextAlignment.Center
        signUpBtn.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        signUpBtn.titleLabel?.font = UIFont(name: "Helvetica-Bold",size: 15)
        shareView?.addSubview(signUpBtn)
        signUpBtn.addTarget(self, action: #selector(LoginViewController.signUpBtnOnClick), forControlEvents: UIControlEvents.TouchDown)
        
        let shareBtn = UIButton(frame: CGRectMake((view.width-btnWidth)/2, 50, btnWidth, 20))
        shareBtn.setTitle("使用第三方登录", forState: UIControlState.Normal)
        shareBtn.backgroundColor = UIColor.clearColor()
        shareBtn.titleLabel?.textAlignment = NSTextAlignment.Center
        shareBtn.setTitleColor(UIColor.whiteColor(), forState: UIControlState.Normal)
        shareBtn.titleLabel?.font = UIFont(name: "Helvetica-Bold",size: 12)
        shareView?.addSubview(shareBtn)
        shareBtn.addTarget(self, action: #selector(LoginViewController.shareBtnOnClick), forControlEvents: UIControlEvents.TouchDown)
    }
    
    //Btn点击事件
    func loginBtnOnClick()
    {
        //改变本地登录状态
        let act:LCAccount = LCAccount.sharedInstance()
        act.logined = true
        
        SVProgressHUD.showSuccessWithStatus("登陆成功")
        let mainVC = MainTabBarController()
        self.presentViewController(mainVC, animated: true, completion: nil)
        //先判断下数据的合法性
//        requestLogin()
    }
    
    func signUpBtnOnClick()
    {
        let registerVC = RegisterViewController()
        self.presentViewController(registerVC, animated: true, completion: nil)
//        self.navigationController?.pushViewController(registerVC, animated: true)
    }
    
    func shareBtnOnClick()
    {
        
    }
    
    //收起键盘
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
//        let field1:UITextField = view.viewWithTag(10001) as! UITextField
//        field1.resignFirstResponder()
//        let field2:UITextField = view.viewWithTag(10002) as! UITextField
//        field2.resignFirstResponder()
        view.endEditing(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    

    func requestLogin() {
        Alamofire.request(.GET, "",parameters: ["name":mobileField.text!,"password":passwordField.text!])
            .responseJSON { response in
                if let value = response.result.value {
                    print("\(value)")
                }
                switch (response.result) {
                case .Success:
                    let json = JSON(response.result.value!)
                    let status = json["status"]
                    let message = json["message"]
                    if status.intValue != 200 {
                        SVProgressHUD.showErrorWithStatus("\(message)")
                    }else {
                        //改变本地登录状态
                        let act:LCAccount = LCAccount.sharedInstance()
                        act.logined = true
                        SVProgressHUD.showSuccessWithStatus("登陆成功")
                        let mainVC = MainTabBarController()
                        self.presentViewController(mainVC, animated: true, completion: nil)
                    }
                case .Failure(let error):
                    SVProgressHUD.showErrorWithStatus("登录失败")
                    print("\(error)")
                }
        }
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */


}
