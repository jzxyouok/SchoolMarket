//
//  RegisterViewController.swift
//  LoveFreshBeen
//  注册界面
//  Created by Dora on 16/5/3.
//  Copyright © 2016年 tianzhongtao. All rights reserved.
//

import UIKit

class RegisterViewController: BaseNavigationController {
    
    private var bacImage:UIImageView?
    private var textFieldView:UIView?

    override func viewDidLoad() {
        super.viewDidLoad()

        createNavigation()
        createBacImage()
        createMidView()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func createNavigation()
    {
        self.navigationItem.title = "注册"
    }
    
    func createBacImage()
    {
        view.backgroundColor = UIColor.clearColor()
        bacImage = UIImageView(frame: CGRectMake(0, 0, view.width, view.height))
        bacImage?.image = UIImage(named: "BacImage")
        view.addSubview(bacImage!)
    }

    func createMidView()
    {
        let marTop = (50/667.0)*view.height
        let marHeight = (50/667.0)*view.height
        
        textFieldView = UIView(frame: CGRectMake(0, marTop, view.width, marHeight*5))
        view.addSubview(textFieldView!)
        
        let field1 = RegisterTextField(frame: CGRectMake(0, 0, view.width, marHeight), placeholder: "手机号码", tag: 10001)
        textFieldView!.addSubview(field1)
        let field2 = RegisterTextField(frame: CGRectMake(0, marHeight+15, view.width, marHeight), placeholder: "输入密码", tag: 10002)
        textFieldView!.addSubview(field2)
        let field3 = RegisterTextField(frame: CGRectMake(0, marHeight*2+30, view.width, marHeight), placeholder: "再次输入密码", tag: 10003)
        textFieldView!.addSubview(field3)
        
        let signInBtn = UIButton(frame: CGRectMake(30, marHeight*3+50, view.width-60, marHeight))
        signInBtn.layer.cornerRadius = 5.0
        signInBtn.setTitle("注册", forState: UIControlState.Normal)
        signInBtn.backgroundColor = UIColor(red:100/255.0 ,green:45/255.0 ,blue:60/255.0 ,alpha:0.75)
        textFieldView!.addSubview(signInBtn)
        signInBtn.addTarget(self, action: "signInBtnOnClick", forControlEvents: UIControlEvents.TouchDown)
    }
    
    func signInBtnOnClick()
    {
        let mainVC = MainTabBarController()
        self.presentViewController(mainVC, animated: true, completion: nil)
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        let field1 = view.viewWithTag(10001) as! UITextField
        field1.resignFirstResponder()
        let field2 = view.viewWithTag(10002) as! UITextField
        field2.resignFirstResponder()
        let field3 = view.viewWithTag(10003) as! UITextField
        field3.resignFirstResponder()
    }
}
